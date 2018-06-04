package io.evotor.market.publisher.api.v1.impl;

import io.evotor.market.publisher.api.v1.ApiProvider;
import io.evotor.market.publisher.api.v1.InstallationsApi;
import io.evotor.market.publisher.api.v1.builder.Installations;
import io.evotor.market.publisher.api.v1.model.token.TemporaryToken;

import java.util.UUID;

class InstallationsImpl extends Impl implements Installations {

    private final UUID appID;

    InstallationsImpl(UUID appID, ApiProvider apiProvider) {
        super(apiProvider);
        this.appID = appID;
    }

    @Override
    public InstallationInstance select(String userId) {
        return new InstallationInstance() {
            @Override
            public TemporaryToken createToken() {
                return get(InstallationsApi.class).createToken(appID, userId);
            }
        };
    }
}
