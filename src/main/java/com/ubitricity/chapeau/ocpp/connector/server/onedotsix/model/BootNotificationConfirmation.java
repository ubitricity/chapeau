/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model;

import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.RegistrationStatus;
import eu.chargetime.ocpp.model.Confirmation;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RegisterForReflection
public class BootNotificationConfirmation implements Confirmation {

    private ZonedDateTime currentTime;
    @Positive(message = "interval should have a positive value")
    private int interval;
    private RegistrationStatus status;

    @Override
    public boolean validate() {
        return status != null && currentTime != null && interval > ModelConstants.INTERVAL_MIN_VALUE;
    }
}
