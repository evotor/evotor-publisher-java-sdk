package io.evotor.market.publisher.api.v1.builder;

import io.evotor.market.publisher.api.v1.model.event.ApplicationEvent;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

public interface Events {

    EventSinceBuilder filter();

    interface EventSinceBuilder extends EventUntilBuilder {
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

            long since = LocalDateTime.now()
                    .minus(unit.getDuration().multipliedBy(amount))
                    .toEpochSecond(ZoneOffset.UTC) * 1000;

            return since(since).until(now);
        }
    }

    interface EventUntilBuilder extends EventTypesBuilder {
        EventTypesBuilder until(long until);
    }

    interface EventTypesBuilder extends EventBuilderFinalStage {
        EventTypesBuilder type(String eventType);
    }

    interface EventBuilderFinalStage extends NavigableResource<ApplicationEvent> {
        Stream<ApplicationEvent> stream();
    }
}
