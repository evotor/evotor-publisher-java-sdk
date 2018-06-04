package io.evotor.market.publisher.api.v1.builder;

import java.util.UUID;

public interface Api {

    ApplicationInstance select(UUID appId);

    interface ApplicationInstance {

        Events events();

        PushNotifications pushNotifications();

        Installations installations();

    }
}
