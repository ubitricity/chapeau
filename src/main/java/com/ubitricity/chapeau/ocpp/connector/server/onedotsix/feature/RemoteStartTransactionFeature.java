/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.feature;

import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.ActionType;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.RemoteStartTransactionConfirmation;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.RemoteStartTransactionRequest;
import eu.chargetime.ocpp.feature.ProfileFeature;
import eu.chargetime.ocpp.feature.profile.Profile;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;


public class RemoteStartTransactionFeature extends ProfileFeature {

    public RemoteStartTransactionFeature(Profile ownerProfile) {
        super(ownerProfile);
    }

    @Override
    public Class<? extends Request> getRequestType() {
        return RemoteStartTransactionRequest.class;
    }

    @Override
    public Class<? extends Confirmation> getConfirmationType() {
        return RemoteStartTransactionConfirmation.class;
    }

    @Override
    public String getAction() {
        return ActionType.REMOTE_START_TRANSACTION.getValue();
    }
}
