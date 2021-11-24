/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model;

import eu.chargetime.ocpp.model.Validatable;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class ChargingSchedulePeriod implements Validatable {

    @NotNull
    private Integer startPeriod;
    @NotNull
    private Double limit;
    private Integer numberPhases = 3;

    @Override
    public boolean validate() {
        return startPeriod != null && limit != null;
    }
}
