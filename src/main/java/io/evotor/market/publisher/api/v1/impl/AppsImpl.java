package io.evotor.market.publisher.api.v1.impl;

import io.evotor.market.publisher.api.v1.ApiProvider;
import io.evotor.market.publisher.api.v1.AppsApi;
import io.evotor.market.publisher.api.v1.builder.*;
import io.evotor.market.publisher.api.v1.model.Page;
import io.evotor.market.publisher.api.v1.model.application.App;

import java.util.UUID;
import java.util.function.Function;

class AppsImpl extends Impl<App> implements Apps, AppFilters.Builder, AppFilters.FinalStage, AppFilters.AppEnvironmentBuilder {

    private String status;
    private App.Environment environment;

    AppsImpl(ApiProvider apiProvider) {
        super(apiProvider);
    }

    @Override
    public ApplicationInstance select(UUID appId) {
        return new ApplicationInstance() {
            @Override
            public App fetchPublishedVersion() {
                return get(AppsApi.class).fetchPublishedAppVersion(appId);
            }

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

            @Override
            public Installations installations() {
                return new InstallationsImpl(appId, apiProvider);
            }
        };
    }

    @Override
    public AppFilters.Builder filter() {
        return this;
    }

    @Override
    protected Function<String, Page<App>> nextPageProvider() {
        return cursor -> {
            AppsApi api = get(AppsApi.class);
            return cursor != null ? api.fetchAppVersions(cursor) : api.fetchAppVersions(status, environment);
        };
    }

    @Override
    public AppFilters.AppEnvironmentBuilder status(String status) {
        this.status = status;
        return this;
    }

    @Override
    public AppFilters.FinalStage environment(App.Environment environment) {
        this.environment = environment;
        return this;
    }
}
