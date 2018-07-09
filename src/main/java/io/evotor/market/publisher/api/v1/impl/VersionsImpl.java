package io.evotor.market.publisher.api.v1.impl;

import io.evotor.market.publisher.api.v1.ApiProvider;
import io.evotor.market.publisher.api.v1.builder.Versions;

import java.util.UUID;

class VersionsImpl extends Impl<Object> implements Versions {

    private final UUID appId;

    VersionsImpl(UUID appId, ApiProvider apiProvider) {
        super(apiProvider);
        this.appId = appId;
    }

    @Override
    public VersionInstance select(UUID versionId) {
        return new VersionInstance() {};
    }
}
