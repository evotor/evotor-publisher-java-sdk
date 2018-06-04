package io.evotor.market.publisher.api.v1.builder;

import io.evotor.market.publisher.api.v1.model.token.TemporaryToken;

public interface Installations {

    InstallationInstance select(String userId);

    interface InstallationInstance {

        TemporaryToken createToken();

    }
}
