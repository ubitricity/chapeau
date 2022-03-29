/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model;

import eu.chargetime.ocpp.model.Confirmation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeartbeatConfirmation implements Confirmation {

    @NotNull
    private ZonedDateTime currentTime;

    @Override
    public boolean validate() {
        return currentTime != null;
    }
}
