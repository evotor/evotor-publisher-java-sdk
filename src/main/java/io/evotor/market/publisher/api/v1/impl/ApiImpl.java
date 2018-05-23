package io.evotor.market.publisher.api.v1.impl;

import io.evotor.market.publisher.api.v1.ApiInterface;
import io.evotor.market.publisher.api.v1.ApiProvider;
import io.evotor.market.publisher.api.v1.EventsApi;
import io.evotor.market.publisher.api.v1.builder.Api;
import io.evotor.market.publisher.api.v1.builder.Events;
import io.evotor.market.publisher.api.v1.builder.PushNotifications;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ApiImpl implements Api {

    private final Map<Class<? extends ApiInterface>, ApiInterface> apis;
    private final ApiProvider apiProvider;

    public ApiImpl(ApiProvider apiProvider) {
        this.apiProvider = apiProvider;
        this.apis = new ConcurrentHashMap<>();
    }

    @SuppressWarnings("unchecked")
    private <T extends ApiInterface> T get(Class<T> target) {
        return (T) apis.computeIfAbsent(target, apiProvider);
    }

    @Override
    public ApplicationInstance select(UUID appId) {
        return new ApplicationInstance() {
            @Override
            public Events events() {
                return new EventsImpl(appId, get(EventsApi.class));
            }

            @Override
            public PushNotifications pushNotifications() {
                return new PushNotificationsImpl(appId, apiProvider);
            }
        };
    }
}
