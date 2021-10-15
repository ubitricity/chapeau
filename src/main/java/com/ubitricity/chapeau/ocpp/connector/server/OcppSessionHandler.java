/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server;

import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.model.SessionInformation;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class OcppSessionHandler implements ServerEvents {
    private final Map<String, UUID> deviceSessionMap;

    public OcppSessionHandler() {
        deviceSessionMap = new ConcurrentHashMap<>();
    }

    @Override
    public void newSession(UUID sessionId, SessionInformation information) {
        String deviceId = information.getIdentifier().replace("/", "");
        deviceSessionMap.put(deviceId, sessionId);
        log.info("newSession: sessionId: {} deviceId: {}", sessionId, deviceId);
    }

    @Override
    public void lostSession(UUID sessionId) {
        deviceSessionMap.entrySet().removeIf(entry -> sessionId.equals(entry.getValue()));
        log.info("lostSession: sessionId: {} lost connection", sessionId);
    }

    public Optional<Map.Entry<String, UUID>> getActiveSessionEntry(UUID sessionId) {
        return deviceSessionMap.entrySet().stream()
                .filter(entry -> sessionId.equals(entry.getValue()))
                .findFirst();
    }

    public Optional<UUID> getActiveSession(String deviceId) {
        return Optional.ofNullable(deviceSessionMap.get(deviceId));
    }
}
