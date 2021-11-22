/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model;

import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.Reason;
import eu.chargetime.ocpp.model.Request;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RegisterForReflection
public class StopTransactionRequest implements Request {

    @Size(max = ModelConstants.STRING_20_CHAR_MAX_LENGTH, message = "'idTag' exceeded limit of {value} chars")
    private String idTag;
    @NotNull
    private Integer meterStop;
    @NonNull
    private ZonedDateTime timestamp;
    @NonNull
    private Integer transactionId;
    private Reason reason;
    private List<MeterValue> transactionData;

    @Override
    public boolean transactionRelated() {
        return false;
    }

    @Override
    public boolean validate() {
        boolean valid = meterStop != null && timestamp != null && transactionId != null;
        if (transactionData != null) {
            valid &= transactionData.stream().allMatch(MeterValue::validate);
        }
        return valid;
    }
}
