package io.evotor.market.publisher.api.v1.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import io.evotor.market.publisher.api.v1.ApiProvider;
import io.evotor.market.publisher.api.v1.ApiV1;
import io.evotor.market.publisher.api.v1.EventsApi;
import io.evotor.market.publisher.api.v1.StreamDecoder;
import io.evotor.market.publisher.api.v1.builder.Events;
import io.evotor.market.publisher.api.v1.model.Page;
import io.evotor.market.publisher.api.v1.model.event.ApplicationEvent;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

class EventsImpl extends Impl<ApplicationEvent> implements Events, Events.EventSinceBuilder, Events.EventUntilBuilder, Events.EventTypesBuilder, Events.EventBuilderFinalStage {

    private final UUID appId;
    private final EventsApi eventsApi;
    private final StreamDecoder streamDecoder;

    private Long since = 0L;
    private Long until;
    private Set<String> types;

    EventsImpl(UUID appId, ApiProvider apiProvider) {
        super(apiProvider);
        this.eventsApi = get(EventsApi.class);
        this.appId = appId;
        this.streamDecoder = new StreamDecoder(ApiV1.buildObjectMapper());
    }

    @Override
    public EventUntilBuilder since(long since) {
        this.since = since;
        return this;
    }

    @Override
    public EventTypesBuilder until(long until) {
        this.until = until;
        return this;
    }

    @Override
    public EventTypesBuilder type(String eventType) {
        if (this.types == null) {
            this.types = new HashSet<>();
        }

        this.types.add(eventType);
        return this;
    }

    @Override
    protected Function<String, Page<ApplicationEvent>> nextPageProvider() {
        return cursor -> cursor != null ?
                eventsApi.fetchEvents(appId, cursor) :
                eventsApi.fetchEvents(appId, since, until, types);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Stream<ApplicationEvent> stream() {
        Response response = eventsApi.fetchEventStream(appId, since, until, types);

        try {
            Stream<ApplicationEvent> decode = (Stream<ApplicationEvent>) streamDecoder.decode(response,
                    new TypeReference<Stream<ApplicationEvent>>() {}.getType());

            return decode.onClose(response::close);
        } catch (IOException e) {
            Util.ensureClosed(response);
            throw new DecodeException(e.getMessage());
        }
    }

    @Override
    public EventSinceBuilder filter() {
        return this;
    }
}
