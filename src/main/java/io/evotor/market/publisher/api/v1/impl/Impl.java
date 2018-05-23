package io.evotor.market.publisher.api.v1.impl;

import io.evotor.market.publisher.api.v1.ApiProvider;

class Impl {

    private final ApiProvider apiProvider;

    Impl(ApiProvider apiProvider) {
        this.apiProvider = apiProvider;
    }

    @SuppressWarnings("unchecked")
    protected  <T> T get(Class<T> target) {
        return (T) apiProvider.apply(target);
    }
}
