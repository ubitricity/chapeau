/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnitOfMeasure {

    WH("Wh"),
    K_WH("kWh"),
    VARH("varh"),
    KVARH("kvarh"),
    W("W"),
    K_W("kW"),
    VA("VA"),
    K_VA("kVA"),
    VAR("var"),
    KVAR("kvar"),
    A("A"),
    V("V"),
    CELSIUS("Celsius"),
    FAHRENHEIT("Fahrenheit"),
    K("K"),
    PERCENT("Percent");

    private final String value;

}
