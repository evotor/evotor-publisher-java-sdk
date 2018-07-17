package io.evotor.market.publisher.api.v1.model.event;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum EventType {

    // customer
    store,
    employee,
    device,
    document,
    product,

    @JsonProperty("product.image")
    product_image,

    // publisher
    hook,

    // internal
    system,

    @JsonEnumDefaultValue
    unknown

}
