package io.evotor.market.publisher.api.v1.impl;

import io.evotor.market.publisher.api.v1.ApiProvider;
import io.evotor.market.publisher.api.v1.DeploymentsApi;
import io.evotor.market.publisher.api.v1.builder.DeploymentFilters;
import io.evotor.market.publisher.api.v1.builder.Installations;
import io.evotor.market.publisher.api.v1.model.GUID;
import io.evotor.market.publisher.api.v1.model.Page;
import io.evotor.market.publisher.api.v1.model.application.AppDeployment;

import java.util.UUID;
import java.util.function.Function;

class DeploymentsImpl extends Impl<AppDeployment> implements Installations.Deployments, DeploymentFilters, DeploymentFilters.Builder, DeploymentFilters.FinalStage {

    private final UUID appId;
    private final String userId;
    private String status;

    DeploymentsImpl(UUID appId, String userId, ApiProvider apiProvider) {
        super(apiProvider);
        this.appId = appId;
        this.userId = userId;
    }

    @Override
    public Installations.DeploymentInstance select(GUID deviceId) {
        return new Installations.DeploymentInstance() {
            @Override
            public AppDeployment fetch() {
                return get(DeploymentsApi.class).fetchOneDeployment(appId, userId, deviceId);
            }
        };
    }

    @Override
    public Builder filter() {
        return this;
    }

    @Override
    protected Function<String, Page<AppDeployment>> nextPageProvider() {
        return cursor -> {
            DeploymentsApi api = get(DeploymentsApi.class);
            return cursor != null ?
                    api.fetchDeploymentsByCursor(appId, userId, cursor) :
                    api.fetchDeployments(appId, userId, status);
        };
    }

    @Override
    public FinalStage status(String status) {
        this.status = status;
        return this;
    }
}
