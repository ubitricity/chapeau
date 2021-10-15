package com.ubitricity.chapeau.domain;

public class RejectedRequestException extends Exception {
    public RejectedRequestException(String message) {
        super(message);
    }
}
