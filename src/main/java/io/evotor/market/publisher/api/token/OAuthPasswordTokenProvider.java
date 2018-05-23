package io.evotor.market.publisher.api.token;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class OAuthPasswordTokenProvider implements TokenProvider {

    private final OAuth20Service service;

    private OAuth2AccessToken token;
    private long activeTill;

    public OAuthPasswordTokenProvider(String clientId, String clientSecret) {
        super();
        this.service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .build(EvotorOAuth20Service::new);
    }

    public void initPasswordGrant(String username, String password) {
        try {
            this.token = this.service.getAccessTokenPasswordGrant(username, password);
            this.activeTill = System.currentTimeMillis() + (token.getExpiresIn() * 1000);
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String get() {
        if (activeTill < System.currentTimeMillis() - TimeUnit.MILLISECONDS.toMillis(1)) {
            try {
                this.token = service.refreshAccessToken(token.getRefreshToken());
            } catch (IOException | InterruptedException | ExecutionException e) {
                throw new IllegalStateException(e);
            }
        }

        return token.getAccessToken();
    }
}
