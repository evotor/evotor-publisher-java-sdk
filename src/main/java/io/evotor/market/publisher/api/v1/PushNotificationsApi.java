package io.evotor.market.publisher.api.v1;

import feign.Param;
import feign.RequestLine;
import io.evotor.market.publisher.api.v1.model.push.PushRequest;
import io.evotor.market.publisher.api.v1.model.push.PushScheduledTask;

import java.util.UUID;

@Scope("push-notification:write")
public interface PushNotificationsApi extends ApiInterface {

    @RequestLine("POST /apps/{app_id}/push-notifications")
    PushScheduledTask create(
            @Param("app_id") UUID appId,
            PushRequest request);

    @RequestLine("GET /apps/{app_id}/push-notifications/{task_id}")
    PushScheduledTask status(
            @Param("app_id") UUID appId,
            @Param("task_id") UUID taskId);

}
