/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.feature;

import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.ActionType;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.ChangeConfigurationConfirmation;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.ChangeConfigurationRequest;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.ResetConfirmation;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.ResetRequest;
import eu.chargetime.ocpp.feature.ProfileFeature;
import eu.chargetime.ocpp.feature.profile.Profile;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;

public class ResetFeature extends ProfileFeature {

    public ResetFeature(Profile ownerProfile) {
        super(ownerProfile);
    }

    @Override
    public Class<? extends Request> getRequestType() {
        return ResetRequest.class;
    }

    @Override
    public Class<? extends Confirmation> getConfirmationType() {
        return ResetConfirmation.class;
    }

    @Override
    public String getAction() {
        return ActionType.RESET.getValue();
    }
}
