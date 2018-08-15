package io.evotor.market.publisher.api.v1.model.event;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum EventType {

    StoreEvent,
    DeviceEvent,
    EmployeeEvent,
    DocumentEvent,
    ProductEvent,
    ProductGroupEvent,
    ProductImageEvent,
    HookEvent,
    TokenEvent,
    SystemEvent,

    @JsonEnumDefaultValue
    UnknownEvent

}
