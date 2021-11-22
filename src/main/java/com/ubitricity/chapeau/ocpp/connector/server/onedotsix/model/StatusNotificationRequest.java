/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model;

import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.ChargePointErrorCode;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.ChargePointStatus;
import eu.chargetime.ocpp.model.Request;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class StatusNotificationRequest implements Request {

    @NotNull
    @PositiveOrZero(message = "connectorId must be >= 0")
    private Integer connectorId;
    @NotNull
    private ChargePointErrorCode errorCode;
    @Size(max = ModelConstants.STRING_50_CHAR_MAX_LENGTH, message = "info exceeded limit of {value} chars")
    private String info;
    @NotNull
    private ChargePointStatus status;
    private ZonedDateTime timestamp;
    @Size(max = ModelConstants.STRING_255_CHAR_MAX_LENGTH, message = "vendorId exceeded limit of {value} chars")
    private String vendorId;
    @Size(max = ModelConstants.STRING_50_CHAR_MAX_LENGTH, message = "vendorErrorCode exceeded limit of {value} chars")
    private String vendorErrorCode;

    @Override
    public boolean transactionRelated() {
        return false;
    }

    @Override
    public boolean validate() {
        return connectorId != null && connectorId >= ModelConstants.CONNECTOR_ID_MIN_VALUE && errorCode != null && status != null;
    }
}
