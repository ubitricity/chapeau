/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.helper;

import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;

import java.util.UUID;

public interface OcppIncomingRequestHandler<T extends Request> {
    Confirmation handleRequest(UUID sessionId, String deviceId, Request request, String protocol);

    Class<T> supports();
}
