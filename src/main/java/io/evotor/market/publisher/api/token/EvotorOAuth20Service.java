package io.evotor.market.publisher.api.token;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.oauth.OAuth20Service;

public class EvotorOAuth20Service extends OAuth20Service {

    EvotorOAuth20Service(OAuthConfig config) {
        super(new DefaultApi20() {
            @Override
            public String getAccessTokenEndpoint() {
                return "https://oauth.evotor.ru/oauth/token";
            }

            @Override
            protected String getAuthorizationBaseUrl() {
                throw new UnsupportedOperationException();
            }
        }, config);
    }
}
