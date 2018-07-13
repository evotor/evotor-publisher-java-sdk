package io.evotor.market.publisher.api.v1;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import io.evotor.market.publisher.api.v1.model.Page;
import io.evotor.market.publisher.api.v1.model.event.ApplicationEvent;

import java.util.Collection;
import java.util.UUID;

@Scope("event:read")
public interface EventsApi extends ApiInterface {

    @RequestLine("GET /apps/{app_id}/events?since={since}&until={until}&types={types}")
    Page<ApplicationEvent> fetchEvents(
            @Param("app_id") UUID appId,
            @Param("since") Long since,
            @Param("until") Long until,
            @Param("types") Collection<String> types);

    @RequestLine("GET /apps/{app_id}/events?cursor={cursor}")
    Page<ApplicationEvent> fetchEvents(
            @Param("app_id") UUID appId,
            @Param("cursor") String cursor);

    // raw response required for manual body closure
    @Headers("Accept: application/stream+json")
    @RequestLine("GET /apps/{app_id}/events?since={since}&until={until}&types={types}")
    Response fetchEventStream(
            @Param("app_id") UUID appId,
            @Param("since") Long since,
            @Param("until") Long until,
            @Param("types") Collection<String> types);

}
