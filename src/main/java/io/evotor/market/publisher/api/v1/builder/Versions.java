package io.evotor.market.publisher.api.v1.builder;

import java.util.UUID;

public interface Versions {

    VersionInstance select(UUID versionId);

    interface VersionInstance {

    }
}
