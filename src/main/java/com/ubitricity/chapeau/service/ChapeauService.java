package com.ubitricity.chapeau.service;

import com.ubitricity.chapeau.domain.Devices;
import com.ubitricity.chapeau.domain.NonExistingDeviceIdException;
import com.ubitricity.chapeau.domain.RejectedRequestException;
import com.ubitricity.chapeau.domain.Transaction;
import com.ubitricity.chapeau.ocpp.client.OcppJsonClient;
import com.ubitricity.chapeau.ocpp.connector.requesthandler.onedotsix.RemoteStartTransactionRequestOneDotSixHandler;
import com.ubitricity.chapeau.ocpp.connector.server.JSONConfiguration;
import com.ubitricity.chapeau.ocpp.connector.server.helper.OcppIncomingRequestHandler;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.OcppOneDotSixProfile;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.ChargePointErrorCode;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.RegistrationStatus;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.*;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.model.Request;
import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
public class ChapeauService {

    private static final String VENDOR = "HUB";
    private static final String MODEL = "UBI";
    private static final String CHARGE_BOX_SERIAL_NUMBER = "12345";
    private static final String FIRMWARE_VERSION = "1.0";
    private static final String ICCID = "89234261758761379219";
    private static final String IMSI = "087986861758093321";
    private static final String METER_SERIAL_NUMBER = "67890";
    private static final String METER_TYPE = "meter-type";
    private static final String VENDOR_ID = "ubi123";
    private final Map<String, OcppJsonClient> clientMap = new HashMap<>();
    private final Map<String, Transaction> deviceTransactionMap = new HashMap<>();
    @Inject
    Devices devices;
    @ConfigProperty(name = "ocpp.url")
    String URL;
    @Inject
    MeterRegistry registry;

    void startup(@Observes StartupEvent event) {
        printAll();
    }

    private void printAll() {
        for (Devices.Device device : devices.all()) {
            log.info("device.deviceId() = {}", device.deviceId());
            log.info("device.authorizationCode() = {}", device.authorizationCode());
            log.info("device.checkAvailability() = {}", device.checkAvailability());
            deviceTransactionMap.put(device.deviceId(), new Transaction());
        }
    }

    private OcppJsonClient getClient(Devices.Device device) {
        JSONConfiguration jsonConfiguration = JSONConfiguration.get();
        String deviceId = device.deviceId();
        jsonConfiguration.setParameter(JSONConfiguration.USERNAME_PARAMETER, deviceId);
        jsonConfiguration.setParameter(JSONConfiguration.PASSWORD_PARAMETER, device.authorizationCode());
        List<OcppIncomingRequestHandler<?>> ocppIncomingRequestHandlers =
                List.of(new RemoteStartTransactionRequestOneDotSixHandler(deviceTransactionMap));
        OcppJsonClient client = new OcppJsonClient(deviceId, new OcppOneDotSixProfile(
                deviceId, ocppIncomingRequestHandlers.stream()
                .collect(Collectors.toMap(OcppIncomingRequestHandler::supports, Function.identity()))),
                jsonConfiguration);
        client.connect(URL, null);
        return client;
    }

    private BootNotificationRequest buildBootNotificationRequest(String deviceId) {
        return new BootNotificationRequest(
                VENDOR, MODEL, CHARGE_BOX_SERIAL_NUMBER, deviceId,
                FIRMWARE_VERSION, ICCID, IMSI, METER_SERIAL_NUMBER, METER_TYPE
        );
    }

    private StatusNotificationRequest buildStatusNotificationRequest(ChangeChargingStatusRequest changeChargingStatusRequest) {
        return new StatusNotificationRequest(changeChargingStatusRequest.getConnectorId(), ChargePointErrorCode.NoError,
                null, changeChargingStatusRequest.getStatus(),
                ZonedDateTime.now(), VENDOR_ID, null
        );
    }

    public void checkDevicesAvailability() {
        log.info("Check devices availability - start");
        devices.all().stream().filter(Devices.Device::checkAvailability).forEach(this::registerDeviceAvailability);
        log.info("Check devices availability - stop");
    }

    private void registerDeviceAvailability(Devices.Device device) {
        String deviceId = device.deviceId();
        String gaugeName = "ocpp.availability." + deviceId;
        try {
            subscribeOnBoot(deviceId);
            registry.gauge(gaugeName, 1);
            log.info("DeviceId = " + deviceId + " is available");
        } catch (Exception e) {
            log.error("Checking availability failed for deviceId = " + deviceId, e);
            registry.gauge(gaugeName, 0);
        }
    }

    public void subscribeOnBoot(String deviceId) throws NonExistingDeviceIdException, RejectedRequestException {
        Request request = buildBootNotificationRequest(deviceId);
        try {
            OcppJsonClient client = getClientFor(deviceId);
            BootNotificationConfirmation response = (BootNotificationConfirmation) client.send(request).toCompletableFuture().get();
            RegistrationStatus status = response.getStatus();
            log.info("From SubscribeOnBoot, ChargePoint: {}; response.getStatus() = {}", deviceId, status);
            if (status == RegistrationStatus.Rejected) {
                throw new RejectedRequestException("Boot notification rejected");
            }
        } catch (InterruptedException | ExecutionException | OccurenceConstraintException | UnsupportedFeatureException e) {
            log.error("Error while getting BootNotificationConfirmation", e);
            throw new IllegalStateException(e);
        }
    }

    public void subscribeOnStatus(String deviceId, ChangeChargingStatusRequest changeChargingStatusRequest) throws NonExistingDeviceIdException, RejectedRequestException {
        Request request = buildStatusNotificationRequest(changeChargingStatusRequest);
        try {
            OcppJsonClient client = getClientFor(deviceId);
            StatusNotificationConfirmation response = (StatusNotificationConfirmation) client.send(request).toCompletableFuture().get();
            boolean validate = response.validate();
            log.info("From SubscribeOnStatus, ChargePoint: {}; response.validate() = {}", deviceId, validate);
            if (!validate) {
                throw new RejectedRequestException("Status notification rejected");
            }
        } catch (InterruptedException | ExecutionException | OccurenceConstraintException | UnsupportedFeatureException e) {
            log.error("Error while getting StatusNotificationConfirmation", e);
            throw new IllegalStateException(e);
        }
    }

    public boolean subscribeOnStartTransaction(String deviceId, StartTransactionRequest request)
            throws NonExistingDeviceIdException, RejectedRequestException {
        try {
            OcppJsonClient client = getClientFor(deviceId);
            String currentIdTag = deviceTransactionMap.get(deviceId).idTag;
            if (currentIdTag != null && currentIdTag.equals(request.getIdTag())) {
                request.setIdTag(currentIdTag);
            }
            StartTransactionConfirmation startTransactionConfirmationResponse =
                    (StartTransactionConfirmation) client.send(request).toCompletableFuture().get();
            Boolean startTransactionValidate = startTransactionConfirmationResponse.validate();
            deviceTransactionMap.get(deviceId).transactionId = startTransactionConfirmationResponse.getTransactionId();
            log.info("From SubscribeOnStartTransaction, ChargePoint: {}; response.validate() = {}", deviceId, startTransactionValidate);
            if (!startTransactionValidate) {
                throw new RejectedRequestException("Start Transaction request rejected");
            }

        } catch (InterruptedException | OccurenceConstraintException |
                UnsupportedFeatureException | ExecutionException e) {
            log.error("Error while getting subscribeOnStartTransaction", e);
            throw new IllegalStateException(e);
        }

        return false;
    }

    public boolean subscribeOnStopTransaction(String deviceId, StopTransactionRequest request)
            throws NonExistingDeviceIdException, RejectedRequestException {
        try {
            OcppJsonClient client = getClientFor(deviceId);
            Integer currentTransactionId = deviceTransactionMap.get(deviceId).transactionId;
            if (currentTransactionId != null && currentTransactionId.equals(request.getTransactionId())) {
                request.setTransactionId(request.getTransactionId());
            }
            StopTransactionConfirmation response = (StopTransactionConfirmation) client.send(request)
                    .toCompletableFuture().get();
            boolean validate = response.validate();
            log.info("From subscribeOnStopTransaction, ChargePoint: {}; response.validate() = {}", deviceId, validate);
            if (!validate) {
                throw new RejectedRequestException("Stop Transaction request rejected");
            }

        } catch (InterruptedException | OccurenceConstraintException |
                UnsupportedFeatureException | ExecutionException e) {
            log.error("Error while getting subscribeOnStopTransaction", e);
            throw new IllegalStateException(e);
        }

        return false;
    }

    private OcppJsonClient getClientFor(String deviceId) throws NonExistingDeviceIdException {
        Devices.Device device = devices.findWithId(deviceId).orElseThrow(() -> new NonExistingDeviceIdException(deviceId));
        return clientMap.computeIfAbsent(deviceId, s -> getClient(device));
    }
}
