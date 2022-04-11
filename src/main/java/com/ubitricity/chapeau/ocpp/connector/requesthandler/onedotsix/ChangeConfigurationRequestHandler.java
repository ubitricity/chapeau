package com.ubitricity.chapeau.ocpp.connector.requesthandler.onedotsix;

import com.ubitricity.chapeau.ocpp.connector.server.helper.OcppIncomingRequestHandler;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.ChangeConfigurationConfirmation;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.ChangeConfigurationRequest;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.ConfigurationStatus;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ChangeConfigurationRequestHandler implements OcppIncomingRequestHandler<ChangeConfigurationRequest> {

    private static final List<String> REBOOT_REQUIRED_CONFIGS = Arrays.asList(
            "AuthorizationKey"
    );

    private static final List<String> ACCEPTED_CONFIGS = Arrays.asList(
            "OCPPJEndpointURL"
    );

    private static final List<String> NOT_SUPPORTED_CONFIGS = Arrays.asList(
            "stop"
    );

    @Override
    public Confirmation handleRequest(UUID sessionId, String deviceId, Request request, String protocol) {
        var changeConfigReq = (ChangeConfigurationRequest) request;
        log.info("ChangeConfigurationRequest: {}", changeConfigReq);

        if (REBOOT_REQUIRED_CONFIGS.contains(changeConfigReq.getKey())) {
            return new ChangeConfigurationConfirmation(ConfigurationStatus.RebootRequired);
        } else if (ACCEPTED_CONFIGS.contains(changeConfigReq.getKey())) {
            return new ChangeConfigurationConfirmation(ConfigurationStatus.Accepted);
        } else if (NOT_SUPPORTED_CONFIGS.contains(changeConfigReq.getKey())) {
            return new ChangeConfigurationConfirmation(ConfigurationStatus.NotSupported);
        }

        return new ChangeConfigurationConfirmation(ConfigurationStatus.Rejected);
    }

    @Override
    public Class<ChangeConfigurationRequest> supports() {
        return ChangeConfigurationRequest.class;
    }
}
