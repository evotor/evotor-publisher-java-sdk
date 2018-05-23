package io.evotor.market.publisher.api.v1.model.event;

import io.evotor.market.publisher.api.v1.model.GUID;
import lombok.Data;

import java.util.Optional;

@Data
public class Device implements EventPayload {

    private GUID id;
    private String name;
    private GUID storeId;
    private String userId;

    @Override
    public Optional<GUID> storeId() {
        return Optional.ofNullable(storeId);
    }

    @Override
    public Optional<GUID> deviceId() {
        return Optional.ofNullable(id);
    }

    @Override
    public Optional<String> userId() {
        return Optional.ofNullable(userId);
    }
}
