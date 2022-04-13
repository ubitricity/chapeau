/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix;

import com.ubitricity.chapeau.ocpp.connector.server.helper.OcppIncomingRequestHandler;
import com.ubitricity.chapeau.ocpp.connector.server.helper.OcppProfile;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.feature.*;
import eu.chargetime.ocpp.feature.ProfileFeature;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;

@Slf4j
public class OcppOneDotSixProfile implements OcppProfile {

    private final String deviceId;
    private final Map<Class<? extends Request>, OcppIncomingRequestHandler<? extends Request>> handlerMap;

    public OcppOneDotSixProfile(
            String deviceId,
            Map<Class<? extends Request>, OcppIncomingRequestHandler<? extends Request>> handlerMap) {
        this.deviceId = deviceId;
        this.handlerMap = handlerMap;
    }

    @Override
    public ProfileFeature[] getFeatureList() {
        return new ProfileFeature[]{
                new BootNotificationFeature(this),
                new StatusNotificationFeature(this),
                new StartTransactionFeature(this),
                new StopTransactionFeature(this),
                new RemoteStartTransactionFeature(this),
                new HeartbeatFeature(this),
                new ChangeConfigurationFeature(this),
                new ResetFeature(this)
        };
    }

    @Override
    public Confirmation handleRequest(UUID sessionId, Request request) {
        log.info("handleRequest: sessionId: {} request: {}", sessionId, request);
        return handlerMap.get(request.getClass())
                .handleRequest(null, deviceId, request, getProtocol());
    }

    @Override
    public String getProtocol() {
        return "ocpp1.6";
    }
}
