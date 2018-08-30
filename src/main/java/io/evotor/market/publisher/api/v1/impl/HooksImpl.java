package io.evotor.market.publisher.api.v1.impl;

import io.evotor.market.publisher.api.v1.ApiProvider;
import io.evotor.market.publisher.api.v1.HooksApi;
import io.evotor.market.publisher.api.v1.builder.Versions;
import io.evotor.market.publisher.api.v1.model.Page;
import io.evotor.market.publisher.api.v1.model.hook.Hook;
import io.evotor.market.publisher.api.v1.model.hook.HookPrototype;

import java.util.UUID;
import java.util.function.Function;

class HooksImpl extends Impl<Hook> implements Versions.Hooks {

    private final UUID appId;
    private final UUID versionId;

    HooksImpl(UUID appId, UUID versionId, ApiProvider apiProvider) {
        super(apiProvider);
        this.appId = appId;
        this.versionId = versionId;
    }

    @Override
    public Hook create(HookPrototype prototype) {
        return get(HooksApi.class).create(appId, versionId, prototype);
    }

    @Override
    protected Function<String, Page<Hook>> nextPageProvider() {
        return cursor -> get(HooksApi.class).fetch(appId, versionId, cursor);
    }

    @Override
    public HookInstance select(UUID hookId) {
        return new HookInstance() {
            @Override
            public Hook fetch() {
                return get(HooksApi.class).fetchOne(appId, versionId, hookId);
            }

            @Override
            public void delete() {
                get(HooksApi.class).delete(appId, versionId, hookId);
            }

            @Override
            public Hook updated(HookPrototype prototype) {
                return get(HooksApi.class).update(appId, versionId, hookId, prototype);
            }
        };
    }
}
