package io.evotor.market.publisher.api.v1.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.UUID;

public class GUID {

    private final UUID value;

    @JsonCreator
    public GUID(UUID value) {
        this.value = value;
    }

    public UUID asUUID() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString().toUpperCase();
    }
}
