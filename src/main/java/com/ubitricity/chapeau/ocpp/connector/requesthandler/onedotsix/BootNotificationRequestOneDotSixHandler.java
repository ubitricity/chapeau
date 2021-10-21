/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.requesthandler.onedotsix;

import com.ubitricity.chapeau.ocpp.connector.server.helper.OcppIncomingRequestHandler;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.BootNotificationConfirmation;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.BootNotificationRequest;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.RegistrationStatus;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.ZonedDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class BootNotificationRequestOneDotSixHandler implements OcppIncomingRequestHandler<BootNotificationRequest> {

    @ConfigProperty(name = "ocpp.default.interval", defaultValue = "86400")
    int interval;

    @Override
    public Confirmation handleRequest(UUID sessionId, String deviceId, Request request, String protocol) {

        var bootNotificationRequest = (BootNotificationRequest) request;

        log.info("handleRequest: sessionId: {} deviceId: {} bootNotificationRequest: {}", sessionId, deviceId,
            bootNotificationRequest);
        if(!deviceId.equals(bootNotificationRequest.getChargePointSerialNumber())) {
            log.error("bootNotificationRequest rejected as deviceId != chargePointSerialNumber. sessionId: {} deviceId: {}, bootNotificationRequest: {}", sessionId, deviceId, bootNotificationRequest);
            return new BootNotificationConfirmation(
                ZonedDateTime.now(), interval, RegistrationStatus.Rejected
                );
        }

        return new BootNotificationConfirmation(
            ZonedDateTime.now(), interval, RegistrationStatus.Accepted
            );
    }

    @Override
    public Class<BootNotificationRequest> supports() {
        return BootNotificationRequest.class;
    }

}
