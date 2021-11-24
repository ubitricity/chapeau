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
import javax.validation.constraints.PositiveOrZero;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class ChargingProfile implements Validatable {

    @NotNull(message = "chargingProfileId must be present")
    private Integer chargingProfileId;
    private Integer transactionId;
    @NotNull
    @PositiveOrZero(message = "stackLevel must be >= 0")
    private Integer stackLevel;
    @NotNull
    private ChargingProfilePurposeType chargingProfilePurpose;
    @NotNull
    private ChargingProfileKindType chargingProfileKind;
    private RecurrencyKindType recurrencyKind;
    private ZonedDateTime validFrom;
    private ZonedDateTime validTo;
    @NotNull
    @Valid
    private ChargingSchedule chargingSchedule;

    @Override
    public boolean validate() {
        boolean valid = chargingProfileId != null;
        valid &= (stackLevel != null && stackLevel >= ModelConstants.STACK_LEVEL_MIN_VALUE);
        valid &= chargingProfilePurpose != null;
        valid &= (transactionId == null || chargingProfilePurpose == ChargingProfilePurposeType.TxProfile);
        valid &= chargingProfileKind != null;
        valid &= (chargingSchedule != null && chargingSchedule.validate());
        return valid;
    }
}
