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

//    private final DeviceService deviceService;

    @Override
    public Confirmation handleRequest(UUID sessionId, String deviceId, Request request, String protocol) {
        var statusRequest = (StatusNotificationRequest) request;
        /*var device = deviceService.findDeviceById(deviceId);

        deviceService.save(updateStatus(device, statusRequest));*/

        return new StatusNotificationConfirmation();
    }

    /*private DeviceDto updateStatus(DeviceDto device, StatusNotificationRequest request) {
        var status = ConnectorStatusType.valueOf(request.getStatus().name());

        // according to 1.6 there is only one EVSE hence getting first
        if (request.getConnectorId() == ServiceConstants.EVSE_CONNECTOR_ID) {
            device.getEvseList().get(0).setStatus(status);
        } else {
            device.getEvseList().get(0).getConnectorDtos().stream()
                .filter(c -> Objects.equals(c.getIndex(), request.getConnectorId()))
                .forEach(c -> c.setStatus(status));
        }

        return device;
    }*/

    @Override
    public Class<StatusNotificationRequest> supports() {
        return StatusNotificationRequest.class;
    }
}
