package io.evotor.market.publisher.api.v1.builder;

import io.evotor.market.publisher.api.v1.model.application.App;

import java.util.UUID;

public interface Apps extends NavigableResource<App> {

    ApplicationInstance select(UUID appId);

    AppFilters.Builder filter();

    interface ApplicationInstance {

        App fetchPublishedVersion();

        Events events();

        PushNotifications pushNotifications();

        Versions versions();

        Installations installations();

    }
}
