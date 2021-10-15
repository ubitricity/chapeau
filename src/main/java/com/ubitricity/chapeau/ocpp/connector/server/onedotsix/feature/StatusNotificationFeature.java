/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.feature;

import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.StatusNotificationConfirmation;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.StatusNotificationRequest;
import eu.chargetime.ocpp.feature.ProfileFeature;
import eu.chargetime.ocpp.feature.profile.Profile;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;


public class StatusNotificationFeature extends ProfileFeature {

    public StatusNotificationFeature(Profile ownerProfile) {
        super(ownerProfile);
    }

    @Override
    public Class<? extends Request> getRequestType() {
        return StatusNotificationRequest.class;
    }

    @Override
    public Class<? extends Confirmation> getConfirmationType() {
        return StatusNotificationConfirmation.class;
    }

    @Override
    public String getAction() {
        return ActionType.STATUS_NOTIFICATION.getValue();
    }
}
