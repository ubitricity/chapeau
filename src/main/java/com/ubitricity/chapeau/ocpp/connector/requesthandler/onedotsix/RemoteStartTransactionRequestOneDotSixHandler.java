/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.requesthandler.onedotsix;

import com.ubitricity.chapeau.domain.Transaction;
import com.ubitricity.chapeau.ocpp.connector.server.helper.OcppIncomingRequestHandler;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.RemoteStartStopStatus;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.RemoteStartTransactionConfirmation;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.RemoteStartTransactionRequest;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class RemoteStartTransactionRequestOneDotSixHandler
        implements OcppIncomingRequestHandler<RemoteStartTransactionRequest> {
    private final Map<String, Transaction> deviceTransactionMap;

    @Override
    public Confirmation handleRequest(UUID sessionId, String deviceId, Request request, String protocol) {
        RemoteStartTransactionRequest remoteStartTransactionRequest = (RemoteStartTransactionRequest) request;
        deviceTransactionMap.get(deviceId).idTag = remoteStartTransactionRequest.getIdTag();
        return new RemoteStartTransactionConfirmation(RemoteStartStopStatus.Accepted);
    }

    @Override
    public Class<RemoteStartTransactionRequest> supports() {
        return RemoteStartTransactionRequest.class;
    }
}
