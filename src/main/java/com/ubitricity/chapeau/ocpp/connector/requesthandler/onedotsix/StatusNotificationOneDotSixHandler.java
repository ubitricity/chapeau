/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.requesthandler.onedotsix;

import com.ubitricity.chapeau.ocpp.connector.server.helper.OcppIncomingRequestHandler;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.StatusNotificationConfirmation;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.StatusNotificationRequest;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class StatusNotificationOneDotSixHandler implements OcppIncomingRequestHandler<StatusNotificationRequest> {

    @Override
    public Confirmation handleRequest(UUID sessionId, String deviceId, Request request, String protocol) {
        return new StatusNotificationConfirmation();
    }

    @Override
    public Class<StatusNotificationRequest> supports() {
        return StatusNotificationRequest.class;
    }
}
