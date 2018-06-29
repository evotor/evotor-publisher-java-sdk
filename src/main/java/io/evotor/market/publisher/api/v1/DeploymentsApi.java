package io.evotor.market.publisher.api.v1;

import feign.Param;
import feign.RequestLine;
import io.evotor.market.publisher.api.v1.model.GUID;
import io.evotor.market.publisher.api.v1.model.Page;
import io.evotor.market.publisher.api.v1.model.application.AppDeployment;

import java.util.UUID;

@Scope("deployment:read")
public interface DeploymentsApi extends ApiInterface {

    @RequestLine("GET /apps/{app_id}/installations/{user_id}/deployments?status={status}")
    Page<AppDeployment> fetchDeployments(
            @Param("app_id") UUID appId,
            @Param("user_id") String userId,
            @Param("status") String status);

    @RequestLine("GET /apps/{app_id}/installations/{user_id}/deployments?cursor={cursor}")
    Page<AppDeployment> fetchDeploymentsByCursor(
            @Param("app_id") UUID appId,
            @Param("user_id") String userId,
            @Param("cursor") String cursor);

    @RequestLine("GET /apps/{app_id}/installations/{user_id}/deployments/{device_id}")
    AppDeployment fetchOneDeployment(
            @Param("app_id") UUID appId,
            @Param("user_id") String userId,
            @Param("device_id") GUID deviceId);

}
