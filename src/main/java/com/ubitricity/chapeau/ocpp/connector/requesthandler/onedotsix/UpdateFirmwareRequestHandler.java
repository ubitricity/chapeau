package com.ubitricity.chapeau.ocpp.connector.requesthandler.onedotsix;

import com.ubitricity.chapeau.ocpp.connector.server.helper.OcppIncomingRequestHandler;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.*;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class UpdateFirmwareRequestHandler implements OcppIncomingRequestHandler<UpdateFirmwareRequest> {

    @Override
    public Confirmation handleRequest(UUID sessionId, String deviceId, Request request, String protocol) {
        var UpdateFirmwareRequest = (UpdateFirmwareRequest) request;
        log.info("UpdateFirmwareRequest: {}", UpdateFirmwareRequest);

        return new UpdateFirmwareConfirmation();
    }

    @Override
    public Class<UpdateFirmwareRequest> supports() {
        return UpdateFirmwareRequest.class;
    }
}
