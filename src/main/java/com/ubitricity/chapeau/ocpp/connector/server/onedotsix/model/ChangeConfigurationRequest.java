/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model;

import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.utilities.ModelUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.ModelConstants.STRING_500_CHAR_MAX_LENGTH;
import static com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.ModelConstants.STRING_50_CHAR_MAX_LENGTH;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeConfigurationRequest implements Request {

    @NotEmpty
    @Size(message = "key exceeds limit of {value} chars", max = STRING_50_CHAR_MAX_LENGTH)
    private String key;
    @NotEmpty
    @Size(message = "value exceeds limit of {value} chars", max = STRING_500_CHAR_MAX_LENGTH)
    private String value;


    @Override
    public boolean transactionRelated() {
        return false;
    }

    @Override
    public boolean validate() {
        return ModelUtil.validate(key, STRING_50_CHAR_MAX_LENGTH)
            && ModelUtil.validate(value, STRING_500_CHAR_MAX_LENGTH);
    }
}
