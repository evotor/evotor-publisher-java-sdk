package io.evotor.market.publisher.api.v1;

import io.evotor.market.publisher.api.v1.model.token.TemporaryToken;
import org.junit.Assert;
import org.junit.Test;

import static io.evotor.market.publisher.api.v1.ApiHolder.DEFAULT;
import static io.evotor.market.publisher.api.v1.ApiHolder.api;

public class InstallationsTest {

    @Test
    public void should_create_installation_token() {
        TemporaryToken token = api.select(DEFAULT)
                .installations()
                .select("01-007")
                .createToken();

        Assert.assertEquals(DEFAULT.toString(), token.getToken());
        Assert.assertNull(token.getExpiredAt());
    }
}
