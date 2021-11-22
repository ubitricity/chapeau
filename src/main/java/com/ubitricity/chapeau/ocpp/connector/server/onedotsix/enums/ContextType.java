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
public enum ContextType {

    INTERRUPTION_BEGIN("Interruption.Begin"),
    INTERRUPTION_END("Interruption.End"),
    OTHER("Other"),
    SAMPLE_CLOCK("Sample.Clock"),
    SAMPLE_PERIODIC("Sample.Periodic"),
    TRANSACTION_BEGIN("Transaction.Begin"),
    TRANSACTION_END("Transaction.End"),
    TRIGGER("Trigger");

    private final String value;
}
