package io.evotor.market.publisher.api.v1.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EventTrigger {

    // customer
    store,
    device,
    employee,
    document,
    product,

    @JsonProperty("product-group")
    product_group,

    @JsonProperty("product-image")
    product_image,

    // publisher
    hook,
    token

}
