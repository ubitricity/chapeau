/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.client;

import com.ubitricity.chapeau.ocpp.connector.server.JSONCommunicator;
import com.ubitricity.chapeau.ocpp.connector.server.JSONConfiguration;
import com.ubitricity.chapeau.ocpp.connector.server.helper.OcppProfile;
import eu.chargetime.ocpp.*;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.protocols.Protocol;

import java.util.Collections;
import java.util.concurrent.CompletionStage;

@Slf4j
public class OcppJsonClient {
    public final Draft draftOcppOnly;
    private final WebSocketTransmitter transmitter;
    private final FeatureRepository featureRepository;
    private final Client client;
    private final String identity;

    /**
     * Application composite root for a json client.
     *
     * @param identity      if set, will append identity to url.
     * @param configuration network configuration for a json client.
     */
    public OcppJsonClient(String identity, OcppProfile profile, JSONConfiguration configuration) {
        this.identity = identity;
        draftOcppOnly =
                new Draft_6455(Collections.emptyList(), Collections.singletonList(new Protocol(profile.getProtocol())));
        transmitter = new WebSocketTransmitter(configuration, draftOcppOnly);
        JSONCommunicator communicator = new JSONCommunicator(transmitter);
        featureRepository = new FeatureRepository();
        ISession session = new SessionFactory(featureRepository).createSession(communicator);
        client = new Client(session, featureRepository, new PromiseRepository()){

        };
        featureRepository.addFeatureProfile(profile);
    }

    public void connect(String url, ClientEvents clientEvents) {
        log.debug("Feature repository: {}", featureRepository);

        String identityUrl = (identity != null) ? String.format("%s/%s", url, identity) : url;
        client.connect(identityUrl, clientEvents);
    }

    public CompletionStage<Confirmation> send(Request request)
            throws OccurenceConstraintException, UnsupportedFeatureException {
        return client.send(request);
    }

    public void disconnect() {
        client.disconnect();
    }

    public boolean isClosed() {
        return transmitter.isClosed();
    }

}
