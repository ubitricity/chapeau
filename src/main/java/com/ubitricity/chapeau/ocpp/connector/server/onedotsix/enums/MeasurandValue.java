/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@RegisterForReflection
public enum MeasurandValue {
    CURRENT_EXPORT("Current.Export"),
    CURRENT_IMPORT("Current.Import"),
    CURRENT_OFFERED("Current.Offered"),
    ENERGY_ACTIVE_EXPORT_REGISTER("Energy.Active.Export.Register"),
    ENERGY_ACTIVE_IMPORT_REGISTER("Energy.Active.Import.Register"),
    ENERGY_REACTIVE_EXPORT_REGISTER("Energy.Reactive.Export.Register"),
    ENERGY_REACTIVE_IMPORT_REGISTER("Energy.Reactive.Import.Register"),
    ENERGY_ACTIVE_EXPORT_INTERVAL("Energy.Active.Export.Interval"),
    ENERGY_ACTIVE_IMPORT_INTERVAL("Energy.Active.Import.Interval"),
    ENERGY_REACTIVE_EXPORT_INTERVAL("Energy.Reactive.Export.Interval"),
    ENERGY_REACTIVE_IMPORT_INTERVAL("Energy.Reactive.Import.Interval"),
    FREQUENCY("Frequency"),
    POWER_ACTIVE_EXPORT("Power.Active.Export"),
    POWER_ACTIVE_IMPORT("Power.Active.Import"),
    POWER_FACTOR("Power.Factor"),
    POWER_OFFERED("Power.Offered"),
    POWER_REACTIVE_EXPORT("Power.Reactive.Export"),
    POWER_REACTIVE_IMPORT("Power.Reactive.Import"),
    RPM("RPM"),
    SOC("SoC"),
    TEMPERATURE("Temperature"),
    VOLTAGE("Voltage");

    private final String value;
}
