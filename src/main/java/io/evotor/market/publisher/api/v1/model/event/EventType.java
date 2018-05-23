package io.evotor.market.publisher.api.v1.model.event;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum EventType {

    // customer
    store, employee, device, document,

    // publisher
    hook,

    // internal
    system, @JsonEnumDefaultValue unknown

}
