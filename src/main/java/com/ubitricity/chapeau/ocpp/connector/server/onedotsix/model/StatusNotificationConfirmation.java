/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model;

import eu.chargetime.ocpp.model.Confirmation;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@RegisterForReflection
public class StatusNotificationConfirmation implements Confirmation {

    @Override
    public boolean validate() {
        return true;
    }

}
