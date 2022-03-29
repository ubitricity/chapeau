package com.ubitricity.chapeau;

import com.ubitricity.chapeau.domain.NonExistingDeviceIdException;
import com.ubitricity.chapeau.domain.RejectedRequestException;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.ChangeChargingStatusRequest;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.StartTransactionRequest;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.StopTransactionRequest;
import com.ubitricity.chapeau.service.ChapeauService;
import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.ws.rs.*;

@Slf4j
@Path("/api/devices/{deviceId}")
public class ChapeauResource {

    private final ChapeauService chapeauService;

    public ChapeauResource(ChapeauService chapeauService) {
        this.chapeauService = chapeauService;
    }

    @POST
    @Path("boot")
    public String boot(@PathParam("deviceId") String deviceId) {
        try {
            chapeauService.subscribeOnBoot(deviceId);
            return "OK";
        } catch (NonExistingDeviceIdException e) {
            log.error("Failed to subscribe for boot notifications", e);
            throw new NotFoundException(e.getMessage());
        } catch (RejectedRequestException e) {
            log.error("Charge point rejected the connection", e);
            throw new ForbiddenException(e.getMessage());
        }
    }

    @POST
    @Path("sendHeartbeat")
    public String sendHeartbeat(@PathParam("deviceId") String deviceId) {
        try {
            chapeauService.sendHeartbeat(deviceId);
            return "OK";
        } catch (NonExistingDeviceIdException e) {
            log.error("Failed to subscribe for boot notifications", e);
            throw new NotFoundException(e.getMessage());
        } catch (RejectedRequestException e) {
            log.error("Charge point rejected the connection", e);
            throw new ForbiddenException(e.getMessage());
        }
    }

    @POST
    @Path("changeStatus")
    public String changeChargingStatus(@PathParam("deviceId") String deviceId,
                                       @RequestBody ChangeChargingStatusRequest changeChargingStatusRequest) {
        try {
            chapeauService.subscribeOnStatus(deviceId, changeChargingStatusRequest);
            return "OK";
        } catch (NonExistingDeviceIdException e) {
            log.error("Failed to subscribe for status notifications", e);
            throw new NotFoundException(e.getMessage());
        } catch (RejectedRequestException e) {
            log.error("Charge point rejected the connection", e);
            throw new ForbiddenException(e.getMessage());
        }
    }

    @POST
    @Path("startTransaction")
    public String startTransaction(@PathParam("deviceId") String deviceId, @RequestBody StartTransactionRequest request) {
        try {
            chapeauService.subscribeOnStartTransaction(deviceId, request);
            return "OK";
        } catch (NonExistingDeviceIdException e) {
            log.error("Failed to start the transaction", e);
            throw new NotFoundException(e.getMessage());
        } catch (RejectedRequestException e) {
            log.error("Charge point rejected the connection", e);
            throw new ForbiddenException(e.getMessage());
        }
    }

    @POST
    @Path("stopTransaction")
    public String stopTransaction(@PathParam("deviceId") String deviceId, @RequestBody StopTransactionRequest request) {
        try {
            chapeauService.subscribeOnStopTransaction(deviceId, request);
            return "OK";
        } catch (NonExistingDeviceIdException e) {
            log.error("Failed to stop the transaction", e);
            throw new NotFoundException(e.getMessage());
        } catch (RejectedRequestException e) {
            log.error("Charge point rejected the connection", e);
            throw new ForbiddenException(e.getMessage());
        }
    }

    @POST
    @Path("rfid-swipe")
    public String rfidSwipe(@PathParam("deviceId") String deviceId) {
        throw new UnsupportedOperationException();
    }


    @Scheduled(every = "20s", delayed = "10s")
//    @Scheduled(every = "5m", delayed = "10s")
    public void checkDevicesAvailability() {
        chapeauService.checkDevicesAvailability();
    }
}
