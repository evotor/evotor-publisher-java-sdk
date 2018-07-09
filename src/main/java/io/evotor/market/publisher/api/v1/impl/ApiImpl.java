package io.evotor.market.publisher.api.v1.impl;

import io.evotor.market.publisher.api.v1.ApiProvider;
import io.evotor.market.publisher.api.v1.builder.Api;
import io.evotor.market.publisher.api.v1.builder.Apps;

public class ApiImpl extends Impl implements Api {

    public ApiImpl(ApiProvider apiProvider) {
        super(apiProvider);
    }

    @Override
    public Apps apps() {
        return new AppsImpl(apiProvider);
    }
}
