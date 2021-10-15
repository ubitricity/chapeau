/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.client;

import com.ubitricity.chapeau.ocpp.connector.server.JSONConfiguration;
import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.RadioEvents;
import eu.chargetime.ocpp.Transmitter;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;

import java.net.ConnectException;
import java.net.Proxy;
import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WebSocketTransmitter implements Transmitter {
    private final Draft draft;

    private final JSONConfiguration configuration;
    private volatile boolean closed = true;
    private volatile WebSocketClient client;

    public WebSocketTransmitter(JSONConfiguration configuration, Draft draft) {
        this.configuration = configuration;
        this.draft = draft;
    }

    @Override
    public void connect(String uri, RadioEvents events) {
        final URI resource = URI.create(uri);

        Map<String, String> httpHeaders = new HashMap<>();
        String username = configuration.getParameter(JSONConfiguration.USERNAME_PARAMETER);
        String password = configuration.getParameter(JSONConfiguration.PASSWORD_PARAMETER);
        if (username != null && password != null) {
            String credentials = username + ":" + password;
            byte[] base64Credentials = Base64.getEncoder().encode(credentials.getBytes());
            httpHeaders.put("Authorization", "Basic " + new String(base64Credentials));
        }

        client =
                new WebSocketClient(resource, draft, httpHeaders) {
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        log.debug("On connection open (HTTP status: {})", serverHandshake.getHttpStatus());
                        events.connected();
                    }

                    @Override
                    public void onMessage(String message) {
                        events.receivedMessage(message);
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        log.debug(
                                "On connection close (code: {}, reason: {}, remote: {})", code, reason, remote);

                        events.disconnected();
                    }

                    @Override
                    public void onError(Exception ex) {
                        if (ex instanceof ConnectException) {
                            log.error("On error triggered caused by:", ex);
                        } else {
                            log.error("On error triggered:", ex);
                        }
                    }
                };

        configure();

        log.debug("Trying to connect to: {}", resource);

        try {
            client.connectBlocking();
            closed = false;
        } catch (Exception ex) {
            log.warn("client.connectBlocking() failed", ex);
        }
    }

    void configure() {
        client.setReuseAddr(configuration.getParameter(JSONConfiguration.REUSE_ADDR_PARAMETER, false));
        client.setTcpNoDelay(
                configuration.getParameter(JSONConfiguration.TCP_NO_DELAY_PARAMETER, false));
        client.setConnectionLostTimeout(
                configuration.getParameter(JSONConfiguration.PING_INTERVAL_PARAMETER, 60));
        client.setProxy(configuration.getParameter(JSONConfiguration.PROXY_PARAMETER, Proxy.NO_PROXY));
    }

    @Override
    public void disconnect() {
        if (client == null) {
            return;
        }
        try {
            client.closeBlocking();
        } catch (Exception ex) {
            log.info("client.closeBlocking() failed", ex);
        } finally {
            client = null;
            closed = true;
        }
    }

    @Override
    public void send(Object request) throws NotConnectedException {
        if (client == null) {
            throw new NotConnectedException();
        }

        try {
            client.send(request.toString());
        } catch (WebsocketNotConnectedException ex) {
            throw new NotConnectedException();
        }
    }

    public boolean isClosed() {
        return closed;
    }
}
