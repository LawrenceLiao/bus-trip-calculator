package com.littlepay.model;

public enum TripStatus {
    COMPLETED("COMPLETED"),
    INCOMPLETE("INCOMPLETE"),
    CANCELLED("CANCELLED");

    public final String status;

    TripStatus(String status) {
        this.status = status;
    }
}
