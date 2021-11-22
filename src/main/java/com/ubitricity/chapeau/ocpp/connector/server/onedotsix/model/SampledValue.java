/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model;

import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.*;
import eu.chargetime.ocpp.model.Validatable;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@RegisterForReflection
public class SampledValue implements Validatable {

    @NotNull
    private String value;
    private ContextType context;
    private ValueFormat format;
    private MeasurandValue measurand;
    private PhaseType phase;
    private Location location;
    private UnitOfMeasure unit;

    public SampledValue() {
        setContext(ContextType.SAMPLE_PERIODIC);
        setFormat(ValueFormat.Raw);
        setMeasurand(MeasurandValue.ENERGY_ACTIVE_IMPORT_REGISTER);
        setLocation(Location.Outlet);
        setUnit(UnitOfMeasure.WH);
    }

    @Override
    public boolean validate() {
        return value != null;
    }

}
