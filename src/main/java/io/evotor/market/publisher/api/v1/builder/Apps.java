package io.evotor.market.publisher.api.v1.builder;

import java.util.UUID;

public interface Apps {

    ApplicationInstance select(UUID appId);

    interface ApplicationInstance {

        Events events();

        PushNotifications pushNotifications();

        Versions versions();

    }
}
