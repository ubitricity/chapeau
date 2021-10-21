/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model;

import eu.chargetime.ocpp.model.Validatable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterValue implements Validatable {

    @NotNull
    private ZonedDateTime timestamp;
    @NotNull
    @Valid
    private List<SampledValue> sampledValue;

    @Override
    public boolean validate() {
        return timestamp != null && sampledValue != null && !sampledValue.isEmpty()
            && sampledValue.stream().allMatch(SampledValue::validate);
    }
}
