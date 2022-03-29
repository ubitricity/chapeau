/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.feature;

import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.ActionType;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.HeartbeatConfirmation;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.HeartbeatRequest;
import eu.chargetime.ocpp.feature.ProfileFeature;
import eu.chargetime.ocpp.feature.profile.Profile;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;


public class HeartbeatFeature extends ProfileFeature {

    public HeartbeatFeature(Profile ownerProfile) {
        super(ownerProfile);
    }

    @Override
    public Class<? extends Request> getRequestType() {
        return HeartbeatRequest.class;
    }

    @Override
    public Class<? extends Confirmation> getConfirmationType() {
        return HeartbeatConfirmation.class;
    }

    @Override
    public String getAction() {
        return ActionType.HEARTBEAT.getValue();
    }
}
