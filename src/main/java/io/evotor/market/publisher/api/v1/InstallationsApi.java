package io.evotor.market.publisher.api.v1;

import feign.Param;
import feign.RequestLine;
import io.evotor.market.publisher.api.v1.model.Page;
import io.evotor.market.publisher.api.v1.model.application.AppInstallation;

import java.util.UUID;

@Scope("installation:read")
public interface InstallationsApi extends ApiInterface {

    @RequestLine("GET /apps/{app_id}/installations?cursor={cursor}")
    Page<AppInstallation> fetch(
            @Param("app_id") UUID appId,
            @Param("cursor") String cursor);

    @RequestLine("GET /apps/{app_id}/installations/{user_id}")
    AppInstallation fetchOne(
            @Param("app_id") UUID appId,
            @Param("user_id") String userId);

}
