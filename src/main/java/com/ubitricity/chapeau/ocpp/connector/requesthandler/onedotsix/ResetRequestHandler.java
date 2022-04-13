package com.ubitricity.chapeau.ocpp.connector.requesthandler.onedotsix;

import com.ubitricity.chapeau.ocpp.connector.server.helper.OcppIncomingRequestHandler;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.*;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ResetRequestHandler implements OcppIncomingRequestHandler<ResetRequest> {

    @Override
    public Confirmation handleRequest(UUID sessionId, String deviceId, Request request, String protocol) {
        var resetRequest = (ResetRequest) request;
        log.info("ResetRequest: {}", resetRequest);

        return new ResetConfirmation(ResetStatus.Accepted);
    }

    @Override
    public Class<ResetRequest> supports() {
        return ResetRequest.class;
    }
}
