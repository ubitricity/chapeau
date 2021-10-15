/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubitricity.chapeau.ocpp.connector.server.OcppSessionHandler;
import com.ubitricity.chapeau.ocpp.connector.server.helper.OcppIncomingRequestHandler;
import com.ubitricity.chapeau.ocpp.connector.server.helper.OcppProfile;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.feature.BootNotificationFeature;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.feature.StatusNotificationFeature;
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
//    private final CallAuditService callAuditService;
    private final ObjectMapper objectMapper;
//    private final Validator validator;

    public OcppOneDotSixProfile(OcppSessionHandler ocppSessionHandler,
                                Map<Class<? extends Request>, OcppIncomingRequestHandler<? extends Request>> handlerMap,
                                /*CallAuditService callAuditService,*/ ObjectMapper objectMapper/*, Validator validator*/) {
        this.ocppSessionHandler = ocppSessionHandler;
        this.handlerMap = handlerMap;
//        this.callAuditService = callAuditService;
        this.objectMapper = objectMapper;
//        this.validator = validator;
    }

    @Override
    public ProfileFeature[] getFeatureList() {
        return new ProfileFeature[]{
//            new AuthorizeFeature(this),
            new BootNotificationFeature(this),
            new StatusNotificationFeature(this)
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

        /*var violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }*/

        /*StopWatch watch = new StopWatch();
        watch.start();*/

        var response = handlerMap.get(request.getClass())
            .handleRequest(deviceIdSessionEntry.getValue(), deviceIdSessionEntry.getKey(), request, getProtocol());

        /*watch.stop();

        callAuditService.save(
            buildCallAudit(
                request, response, deviceIdSessionEntry.getKey(),
                getProtocol(), findOperation(request), watch.getTotalTimeMillis()
            )
        );*/

        return response;
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

    /*private CallAuditDto buildCallAudit(Request request, Confirmation response,
                                        String deviceId, String protocol,
                                        String operation, Long callTimeMillis) {
        try {
            return CallAuditDto.builder()
                .protocol(protocol)
                .deviceId(deviceId)
                .ocppRequest(objectMapper.writeValueAsString(request))
                .ocppRequestClass(request.getClass().getName())
                .ocppResponse(objectMapper.writeValueAsString(response))
                .ocppResponseClass(response.getClass().getName())
                .ocppOperation(operation)
                .ocppCallTimeMillis(callTimeMillis)
                .createdTimestamp(ZonedDateTime.now())
                .build();
        } catch (JsonProcessingException e) {
            log.error("Error while trying to write request/response as JSON : ", e);
        }

        return null;
    }*/
}
