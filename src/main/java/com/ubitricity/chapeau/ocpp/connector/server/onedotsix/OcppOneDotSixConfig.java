/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubitricity.chapeau.ocpp.connector.server.OcppSessionHandler;
import com.ubitricity.chapeau.ocpp.connector.server.helper.OcppIncomingRequestHandler;
import com.ubitricity.chapeau.ocpp.connector.requesthandler.onedotsix.BootNotificationRequestOneDotSixHandler;
import com.ubitricity.chapeau.ocpp.connector.requesthandler.onedotsix.StatusNotificationOneDotSixHandler;
import lombok.RequiredArgsConstructor;

import javax.enterprise.inject.Produces;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
/*@Configuration
@ConditionalOnProperty(prefix = "ocpp", name = "version", havingValue = "1.6")*/
public class OcppOneDotSixConfig {
    @Produces
    public OcppSessionHandler ocppSessionHandler() {
        return new OcppSessionHandler();
    }

    /*@Bean
    public OcppServerProperties ocppServerProperties() {
        return new OcppServerProperties();
    }*/

    @Produces
    public OcppOneDotSixProfile ocppOneDotSixProfile(OcppSessionHandler ocppSessionHandler,
                                                     /*List<OcppIncomingRequestHandler<?>> ocppIncomingRequestHandlers,*/
                                                     /*CallAuditService callAuditService, */ObjectMapper objectMapper/*,
                                                     Validator validator*/) {
        List<OcppIncomingRequestHandler<?>> ocppIncomingRequestHandlers =
                Arrays.asList(new BootNotificationRequestOneDotSixHandler(), new StatusNotificationOneDotSixHandler()
                );
        return new OcppOneDotSixProfile(
            ocppSessionHandler, ocppIncomingRequestHandlers.stream()
            .collect(Collectors.toMap(OcppIncomingRequestHandler::supports, Function.identity())),
            /*callAuditService, */objectMapper/*, validator*/
        );
    }

    /*@Bean
    public OcppJsonServer ocppJsonServer(OcppSessionHandler ocppSessionHandler,
                                         OcppServerProperties ocppServerProperties,
                                         OcppOneDotSixProfile ocppOneDotSixProfile,
                                         OcppAuthenticationHandler ocppAuthenticationHandler) {
        return new OcppJsonServer(
            ocppSessionHandler, ocppServerProperties,
            ocppOneDotSixProfile, ocppAuthenticationHandler
        );
    }*/
}
