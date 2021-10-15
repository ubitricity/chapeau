/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.requesthandler.onedotsix;

import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

//@Configuration
@RequiredArgsConstructor
//@ConditionalOnProperty(prefix = "ocpp", name = "version", havingValue = "1.6")
@ApplicationScoped
public class OcppRequestHandlerOneDotSixConfig {

//    private final DeviceService deviceService;

    @Produces
    public BootNotificationRequestOneDotSixHandler bootNotificationRequestOneDotSixHandler() {
        return new BootNotificationRequestOneDotSixHandler(/*deviceService*/);
    }

    @Produces
    public StatusNotificationOneDotSixHandler statusNotificationOneDotSixHandler() {
        return new StatusNotificationOneDotSixHandler(/*deviceService*/);
    }


}
