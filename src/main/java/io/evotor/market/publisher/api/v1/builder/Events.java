package io.evotor.market.publisher.api.v1.builder;

import io.evotor.market.publisher.api.v1.model.Page;
import io.evotor.market.publisher.api.v1.model.event.ApplicationEvent;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

public interface Events {

    EventUntilBuilder since(long since);

    default EventUntilBuilder all() {
        return since(0);
    }

    default EventTypesBuilder yesterday() {
        return last(1, ChronoUnit.DAYS);
    }

    default EventTypesBuilder lastWeek() {
        return last(1, ChronoUnit.WEEKS);
    }

    default EventTypesBuilder last(int amount, ChronoUnit unit) {
        long now = LocalDateTime.now()
                .toEpochSecond(ZoneOffset.UTC) * 1000;

        long lastDay = LocalDateTime.now()
                .minus(unit.getDuration().multipliedBy(amount))
                .toEpochSecond(ZoneOffset.UTC) * 1000;

        return since(lastDay).until(now);
    }

    interface EventUntilBuilder extends EventTypesBuilder {
        EventTypesBuilder until(long until);
    }

    interface EventTypesBuilder extends EventBuilderFinalStage {
        EventTypesBuilder type(String eventType);
    }

    interface EventBuilderFinalStage extends Iterable<ApplicationEvent> {
        Page<ApplicationEvent> fetch();
        Stream<ApplicationEvent> stream();
    }
}
