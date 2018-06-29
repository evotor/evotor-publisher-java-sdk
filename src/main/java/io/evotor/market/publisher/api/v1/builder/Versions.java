package io.evotor.market.publisher.api.v1.builder;

import io.evotor.market.publisher.api.v1.model.application.App;

import java.util.UUID;

public interface Versions extends NavigableResource<App> {

    VersionInstance select(UUID versionId);

    AppFilters.Builder filter();

    interface VersionInstance {

        App fetch();

    }
}
