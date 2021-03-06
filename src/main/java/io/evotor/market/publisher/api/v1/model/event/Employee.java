package io.evotor.market.publisher.api.v1.model.event;

import io.evotor.market.publisher.api.v1.model.GUID;
import lombok.Data;

import java.util.Collection;
import java.util.Optional;

@Data
public class Employee implements EventPayload {

    private GUID id;
    private Long phone;
    private String name;
    private String lastName;
    private String role;
    private String userId;
    private Collection<GUID> stores;

    @Override
    public Optional<String> userId() {
        return Optional.ofNullable(userId);
    }
}
