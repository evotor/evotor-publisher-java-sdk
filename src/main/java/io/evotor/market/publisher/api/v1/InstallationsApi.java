package io.evotor.market.publisher.api.v1;

import feign.Param;
import feign.RequestLine;
import io.evotor.market.publisher.api.v1.model.token.TemporaryToken;

import java.util.UUID;

public interface InstallationsApi extends ApiInterface {

    @Scope(isPrivate = true)
    @RequestLine("POST /apps/{app_id}/installations/{user_id}/tokens")
    TemporaryToken createToken(
            @Param("app_id") UUID appId,
            @Param("user_id") String userId);

}
