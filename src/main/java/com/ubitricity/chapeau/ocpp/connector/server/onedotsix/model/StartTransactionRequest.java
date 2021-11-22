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
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class StartTransactionRequest implements Request {

    @NotNull
    @Positive(message = "connectorId must be > 0")
    private Integer connectorId;
    @NotEmpty
    @Size(max = ModelConstants.STRING_20_CHAR_MAX_LENGTH, message = "idTag exceeded limit of {value} chars")
    private String idTag;
    @NonNull
    private Integer meterStart;
    private Integer reservationId;
    @NonNull
    private ZonedDateTime timestamp;

    @Override
    public boolean transactionRelated() {
        return true;
    }

    @Override
    public boolean validate() {
        boolean connectorIdIsValid = connectorId != null && connectorId > ModelConstants.CONNECTOR_ID_MIN_VALUE;
        boolean idTagIsValid = ModelUtil.validate(idTag, ModelConstants.STRING_20_CHAR_MAX_LENGTH);

        return connectorIdIsValid && idTagIsValid && meterStart != null && timestamp != null;
    }
}
