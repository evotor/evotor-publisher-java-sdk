package io.evotor.market.publisher.api.v1.model.event;

import io.evotor.market.publisher.api.v1.model.GUID;

import java.util.Optional;

public interface EventPayload {

    default Optional<String> userId() {
        return Optional.empty();
    }

    default Optional<GUID> storeId() {
        return Optional.empty();
    }

    default Optional<GUID> deviceId() {
        return Optional.empty();
    }
}
