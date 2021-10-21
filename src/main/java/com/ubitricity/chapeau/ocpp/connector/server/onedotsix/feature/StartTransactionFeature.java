/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.feature;

import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.ActionType;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.StartTransactionConfirmation;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.StartTransactionRequest;
import eu.chargetime.ocpp.feature.ProfileFeature;
import eu.chargetime.ocpp.feature.profile.Profile;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;


public class StartTransactionFeature extends ProfileFeature {

    public StartTransactionFeature(Profile ownerProfile) {
        super(ownerProfile);
    }

    @Override
    public Class<? extends Request> getRequestType() {
        return StartTransactionRequest.class;
    }

    @Override
    public Class<? extends Confirmation> getConfirmationType() {
        return StartTransactionConfirmation.class;
    }

    @Override
    public String getAction() {
        return ActionType.START_TRANSACTION.getValue();
    }
}
