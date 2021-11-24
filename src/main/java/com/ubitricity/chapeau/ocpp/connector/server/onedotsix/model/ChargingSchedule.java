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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class ChargingSchedule implements Validatable {

    private Integer duration;
    private ZonedDateTime startSchedule;
    @NotNull
    private ChargingRateUnitType chargingRateUnit;
    @Valid
    private List<ChargingSchedulePeriod> chargingSchedulePeriod;
    private Double minChargingRate;

    @Override
    public boolean validate() {
        return chargingRateUnit != null && chargingSchedulePeriod != null
                && !chargingSchedulePeriod.isEmpty()
                && chargingSchedulePeriod.stream().allMatch(ChargingSchedulePeriod::validate);
    }
}
