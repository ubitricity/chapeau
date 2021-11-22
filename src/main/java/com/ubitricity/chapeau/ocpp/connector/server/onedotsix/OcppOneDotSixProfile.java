/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix;

import com.ubitricity.chapeau.ocpp.connector.server.OcppSessionHandler;
import com.ubitricity.chapeau.ocpp.connector.server.helper.OcppIncomingRequestHandler;
import com.ubitricity.chapeau.ocpp.connector.server.helper.OcppProfile;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.feature.BootNotificationFeature;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.feature.StartTransactionFeature;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.feature.StatusNotificationFeature;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.feature.StopTransactionFeature;
import eu.chargetime.ocpp.feature.Feature;
import eu.chargetime.ocpp.feature.ProfileFeature;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class OcppOneDotSixProfile implements OcppProfile {

    private final OcppSessionHandler ocppSessionHandler;
    private final Map<Class<? extends Request>, OcppIncomingRequestHandler<? extends Request>> handlerMap;

    public OcppOneDotSixProfile(
            OcppSessionHandler ocppSessionHandler,
            Map<Class<? extends Request>, OcppIncomingRequestHandler<? extends Request>> handlerMap) {
        this.ocppSessionHandler = ocppSessionHandler;
        this.handlerMap = handlerMap;
    }

    @Override
    public ProfileFeature[] getFeatureList() {
        return new ProfileFeature[]{
                new BootNotificationFeature(this),
                new StatusNotificationFeature(this),
                new StartTransactionFeature(this),
                new StopTransactionFeature(this)
        };
    }

    @Override
    public Confirmation handleRequest(UUID sessionId, Request request) {
        log.debug("handleRequest: sessionId: {} request: {}", sessionId, request);
        return ocppSessionHandler.getActiveSessionEntry(sessionId)
                .map(deviceIdSessionEntry -> handle(deviceIdSessionEntry, request)).orElse(null);
    }

    private Confirmation handle(Map.Entry<String, UUID> deviceIdSessionEntry, Request request) {
        if (!handlerMap.containsKey(request.getClass())) {
            return null;
        }

        return handlerMap.get(request.getClass())
                .handleRequest(deviceIdSessionEntry.getValue(), deviceIdSessionEntry.getKey(), request, getProtocol());
    }

    @Override
    public String getProtocol() {
        return "ocpp1.6";
    }

    private String findOperation(Request request) {
        return Arrays.stream(getFeatureList())
                .filter(f -> (f.getRequestType().equals(request.getClass())))
                .findFirst()
                .map(Feature::getAction)
                .orElseThrow();
    }
}
