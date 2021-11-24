/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model;

import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.utilities.ModelUtil;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class RemoteStartTransactionRequest implements Request {

    @Positive(message = "connectorId must be > 0")
    private Integer connectorId;
    @NotEmpty
    @Size(max = ModelConstants.STRING_20_CHAR_MAX_LENGTH, message = "idTag exceeded limit of {value} chars")
    private String idTag;
    @Valid
    private ChargingProfile chargingProfile;

    @Override
    public boolean transactionRelated() {
        return false;
    }

    @Override
    public boolean validate() {
        boolean valid = ModelUtil.validate(idTag, ModelConstants.STRING_20_CHAR_MAX_LENGTH);

        if (chargingProfile != null) {
            valid &= chargingProfile.validate();
        }

        if (connectorId != null) {
            valid &= connectorId > ModelConstants.CONNECTOR_ID_MIN_VALUE;
        }

        return valid;
    }
}
