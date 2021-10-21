package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model;

import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.ChargePointStatus;
import eu.chargetime.ocpp.model.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeChargingStatusRequest implements Request {

    @NotNull
    @PositiveOrZero(message = "connectorId must be >= 0")
    private Integer connectorId;

    @NotNull
    private ChargePointStatus status;

    @Override
    public boolean validate() {
        return connectorId != null && connectorId >= ModelConstants.CONNECTOR_ID_MIN_VALUE && status != null;
    }

    @Override
    public boolean transactionRelated() {
        return false;
    }
}
