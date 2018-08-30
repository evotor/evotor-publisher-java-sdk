package io.evotor.market.publisher.api.v1.builder;

import io.evotor.market.publisher.api.v1.model.hook.Hook;
import io.evotor.market.publisher.api.v1.model.hook.HookPrototype;

import java.util.UUID;

public interface Versions {

    VersionInstance select(UUID versionId);

    interface VersionInstance {

        Hooks hooks();

    }

    interface Hooks extends NavigableResource<Hook> {

        Hook create(HookPrototype prototype);

        HookInstance select(UUID hookId);

        interface HookInstance {
            Hook fetch();
            void delete();
            Hook updated(HookPrototype prototype);
        }
    }
}
