package io.evotor.market.publisher.api.v1;

import feign.Param;
import feign.RequestLine;
import io.evotor.market.publisher.api.v1.model.Page;
import io.evotor.market.publisher.api.v1.model.application.App;

import java.util.UUID;

@Scope("app:read")
public interface AppsApi extends ApiInterface {

    @RequestLine("GET /apps?status={status}&environment={environment}")
    Page<App> fetchAppVersions(
            @Param("status") String status,
            @Param("environment") App.Environment environment);

    @RequestLine("GET /apps?cursor={cursor}")
    Page<App> fetchAppVersions(
            @Param("cursor") String cursor);

    @RequestLine("GET /apps/{app_id}/versions?status={status}&environment={environment}")
    Page<App> fetchAppVersions(
            @Param("app_id") UUID appId,
            @Param("status") String status,
            @Param("environment") App.Environment environment);

    @RequestLine("GET /apps/{app_id}/versions?cursor={cursor}")
    Page<App> fetchAppVersions(
            @Param("app_id") UUID appId,
            @Param("cursor") String cursor);

    @RequestLine("GET /apps/{app_id}")
    App fetchPublishedAppVersion(
            @Param("app_id") UUID appId);

    @RequestLine("GET /apps/{app_id}/versions/{version_id}")
    App fetchAppVersion(
            @Param("app_id") UUID appId,
            @Param("version_id") UUID versionId);

}
