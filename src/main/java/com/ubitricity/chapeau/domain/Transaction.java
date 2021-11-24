package com.ubitricity.chapeau.domain;

public class Transaction {
    public String idTag;
    public Integer transactionId;

    @Override
    public String toString() {
        return "Transaction{" +
                "idTag='" + idTag + '\'' +
                ", transactionId=" + transactionId +
                '}';
    }
}
