package io.evotor.market.publisher.api.v1.model.event;

import io.evotor.market.publisher.api.v1.model.GUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)
public class Store extends AbstractPayload {

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
