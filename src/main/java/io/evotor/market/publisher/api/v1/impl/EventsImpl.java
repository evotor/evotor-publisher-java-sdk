package io.evotor.market.publisher.api.v1.impl;

import io.evotor.market.publisher.api.v1.ApiProvider;
import io.evotor.market.publisher.api.v1.EventsApi;
import io.evotor.market.publisher.api.v1.builder.Events;
import io.evotor.market.publisher.api.v1.model.Page;
import io.evotor.market.publisher.api.v1.model.event.ApplicationEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

class EventsImpl extends Impl<ApplicationEvent> implements Events, Events.EventSinceBuilder, Events.EventUntilBuilder, Events.EventTypesBuilder, Events.EventBuilderFinalStage {

    private final UUID appId;
    private final EventsApi eventsApi;

    private Long since = 0L;
    private Long until;
    private Set<String> types;

    EventsImpl(UUID appId, ApiProvider apiProvider) {
        super(apiProvider);
        this.eventsApi = get(EventsApi.class);
        this.appId = appId;
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
    public Stream<ApplicationEvent> stream() {
        return eventsApi.fetchEventStream(appId, since, until, types);
    }

    @Override
    public EventSinceBuilder filter() {
        return this;
    }
}
