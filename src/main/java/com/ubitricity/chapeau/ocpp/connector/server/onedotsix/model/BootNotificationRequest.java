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
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.ModelConstants.*;

@Data
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class BootNotificationRequest implements Request {

    @NonNull
    @NotEmpty
    @Size(max = STRING_20_CHAR_MAX_LENGTH, message = "chargePointVendor exceeded limit of {value} chars")
    private String chargePointVendor;
    @NonNull
    @NotEmpty
    @Size(max = STRING_20_CHAR_MAX_LENGTH, message = "chargePointModel exceeded limit of {value} chars")
    private String chargePointModel;
    @Size(max = STRING_25_CHAR_MAX_LENGTH, message = "chargeBoxSerialNumber exceeded limit of {value} chars")
    private String chargeBoxSerialNumber;
    @Size(max = STRING_25_CHAR_MAX_LENGTH, message = "chargePointSerialNumber exceeded limit of {value} chars")
    private String chargePointSerialNumber;
    @Size(max = STRING_50_CHAR_MAX_LENGTH, message = "firmwareVersion exceeded limit of {value} chars")
    private String firmwareVersion;
    @Size(max = STRING_20_CHAR_MAX_LENGTH, message = "iccid exceeded limit of {value} chars")
    private String iccid;
    @Size(max = STRING_20_CHAR_MAX_LENGTH, message = "imsi exceeded limit of {value} chars")
    private String imsi;
    @Size(max = STRING_25_CHAR_MAX_LENGTH, message = "meterSerialNumber exceeded limit of {value} chars")
    private String meterSerialNumber;
    @Size(max = STRING_25_CHAR_MAX_LENGTH, message = "meterType exceeded limit of {value} chars")
    private String meterType;

    @Override
    public boolean transactionRelated() {
        return false;
    }

    @Override
    public boolean validate() {
        return ModelUtil.validate(chargePointVendor, STRING_20_CHAR_MAX_LENGTH)
            && ModelUtil.validate(chargePointModel, STRING_20_CHAR_MAX_LENGTH);
    }

}
