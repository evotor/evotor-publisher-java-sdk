package io.evotor.market.publisher.api.v1.impl;

import io.evotor.market.publisher.api.v1.ApiProvider;
import io.evotor.market.publisher.api.v1.InstallationsApi;
import io.evotor.market.publisher.api.v1.builder.Installations;
import io.evotor.market.publisher.api.v1.model.Page;
import io.evotor.market.publisher.api.v1.model.application.AppInstallation;

import java.util.UUID;
import java.util.function.Function;

class InstallationsImpl extends Impl<AppInstallation> implements Installations {

    private final UUID appId;

    InstallationsImpl(UUID appId, ApiProvider apiProvider) {
        super(apiProvider);
        this.appId = appId;
    }

    @Override
    public InstallationInstance select(String userId) {
        return new InstallationInstance() {
            @Override
            public AppInstallation fetch() {
                return get(InstallationsApi.class).fetchOne(appId, userId);
            }

            @Override
            public Deployments deployments() {
                return new DeploymentsImpl(appId, userId, apiProvider);
            }
        };
    }

    @Override
    protected Function<String, Page<AppInstallation>> nextPageProvider() {
        return cursor -> get(InstallationsApi.class).fetch(appId, cursor);
    }
}
