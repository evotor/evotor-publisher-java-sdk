package io.evotor.market.publisher.api.v1.model.event;


import io.evotor.market.api.v2.model.Resource;

import java.util.Date;

public class System implements Resource {

    @Override
    public Date getCreatedAt() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Date getUpdatedAt() {
        throw new UnsupportedOperationException();
    }
}
