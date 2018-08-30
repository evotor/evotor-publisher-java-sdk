package io.evotor.market.publisher.api.v1;

import feign.Param;
import feign.RequestLine;
import io.evotor.market.publisher.api.v1.model.Page;
import io.evotor.market.publisher.api.v1.model.hook.Hook;
import io.evotor.market.publisher.api.v1.model.hook.HookPrototype;

import java.util.UUID;

public interface HooksApi extends ApiInterface {

    @Scope("hook:read")
    @RequestLine("GET /apps/{app_id}/versions/{version_id}/hooks?cursor={cursor}")
    Page<Hook> fetch(
            @Param("app_id") UUID appId,
            @Param("version_id") UUID versionId,
            @Param("cursor") String cursor);

    @Scope("hook:read")
    @RequestLine("GET /apps/{app_id}/versions/{version_id}/hooks/{hook_id}")
    Hook fetchOne(
            @Param("app_id") UUID appId,
            @Param("version_id") UUID versionId,
            @Param("hook_id") UUID hookId);

    @Scope("hook:write")
    @RequestLine("POST /apps/{app_id}/versions/{version_id}/hooks")
    Hook create(
            @Param("app_id") UUID appId,
            @Param("version_id") UUID versionId,
            HookPrototype prototype);

    @Scope("hook:write")
    @RequestLine("POST /apps/{app_id}/versions/{version_id}/hooks/{hook_id}")
    Hook replace(
            @Param("app_id") UUID appId,
            @Param("version_id") UUID versionId,
            @Param("hook_id") UUID hookId,
            HookPrototype prototype);

    @Scope("hook:write")
    @RequestLine("PATCH /apps/{app_id}/versions/{version_id}/hooks/{hook_id}")
    Hook update(
            @Param("app_id") UUID appId,
            @Param("version_id") UUID versionId,
            @Param("hook_id") UUID hookId,
            HookPrototype prototype);

    @Scope("hook:write")
    @RequestLine("DELETE /apps/{app_id}/versions/{version_id}/hooks/{hook_id}")
    void delete(
            @Param("app_id") UUID appId,
            @Param("version_id") UUID versionId,
            @Param("hook_id") UUID hookId);

}
