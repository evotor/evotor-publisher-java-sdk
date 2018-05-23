package io.evotor.market.publisher.api.v1.model.event;

import io.evotor.market.publisher.api.v1.model.GUID;
import lombok.Data;

import java.util.Optional;

@Data
public class Store implements EventPayload {

    private GUID id;
    private String userId;
    private String name;
    private String address;

    @Override
    public Optional<String> userId() {
        return Optional.ofNullable(userId);
    }

    @Override
    public Optional<GUID> storeId() {
        return Optional.ofNullable(id);
    }
}
