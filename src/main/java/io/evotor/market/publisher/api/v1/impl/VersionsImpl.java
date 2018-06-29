package io.evotor.market.publisher.api.v1.impl;

import io.evotor.market.publisher.api.v1.ApiProvider;
import io.evotor.market.publisher.api.v1.AppsApi;
import io.evotor.market.publisher.api.v1.builder.AppFilters;
import io.evotor.market.publisher.api.v1.builder.Versions;
import io.evotor.market.publisher.api.v1.model.Page;
import io.evotor.market.publisher.api.v1.model.application.App;

import java.util.UUID;
import java.util.function.Function;

class VersionsImpl extends Impl<App> implements Versions, AppFilters.Builder, AppFilters.AppEnvironmentBuilder, AppFilters.FinalStage {

    private final UUID appId;

    private String status;
    private App.Environment environment;

    VersionsImpl(UUID appId, ApiProvider apiProvider) {
        super(apiProvider);
        this.appId = appId;
    }

    @Override
    protected Function<String, Page<App>> nextPageProvider() {
        return cursor -> {
            AppsApi api = get(AppsApi.class);
            return cursor != null ?
                    api.fetchAppVersions(appId, cursor) :
                    api.fetchAppVersions(appId, status, environment);
        };
    }

    @Override
    public VersionInstance select(UUID versionId) {
        return () -> get(AppsApi.class).fetchAppVersion(appId, versionId);
    }

    @Override
    public AppFilters.Builder filter() {
        return this;
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
