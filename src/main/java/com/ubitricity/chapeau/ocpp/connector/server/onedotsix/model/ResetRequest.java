/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model;

import eu.chargetime.ocpp.model.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ResetRequest implements Request {

    @NotNull
    private ResetType type;

    @Override
    public boolean transactionRelated() {
        return false;
    }

    @Override
    public boolean validate() {
        return type != null;
    }
}
