package io.evotor.market.publisher.api.v1.impl;

import io.evotor.market.publisher.api.v1.ApiProvider;
import io.evotor.market.publisher.api.v1.builder.Apps;
import io.evotor.market.publisher.api.v1.builder.Events;
import io.evotor.market.publisher.api.v1.builder.PushNotifications;
import io.evotor.market.publisher.api.v1.builder.Versions;

import java.util.UUID;

class AppsImpl extends Impl<Object> implements Apps {

    AppsImpl(ApiProvider apiProvider) {
        super(apiProvider);
    }

    @Override
    public ApplicationInstance select(UUID appId) {
        return new ApplicationInstance() {
            @Override
            public Events events() {
                return new EventsImpl(appId, apiProvider);
            }

            @Override
            public PushNotifications pushNotifications() {
                return new PushNotificationsImpl(appId, apiProvider);
            }

            @Override
            public Versions versions() {
                return new VersionsImpl(appId, apiProvider);
            }
        };
    }
}
