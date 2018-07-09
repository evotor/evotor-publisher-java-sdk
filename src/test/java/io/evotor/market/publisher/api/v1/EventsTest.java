package io.evotor.market.publisher.api.v1;

import io.evotor.market.publisher.api.v1.model.Page;
import io.evotor.market.publisher.api.v1.model.event.ApplicationEvent;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.evotor.market.publisher.api.v1.ApiHolder.DEFAULT;
import static io.evotor.market.publisher.api.v1.ApiHolder.api;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class EventsTest {

    @Test
    public void events_should_be_fetched() {
        Page<ApplicationEvent> page = api.apps().select(DEFAULT).events()
                .filter()
                .since(1524497180000L)
                .until(1524583580000L)
                .fetch();

        assertNotNull(page);
        assertThat(page.getItems(), Matchers.hasSize(2));
        assertThat(page.getPaging().getNextCursor(), Matchers.notNullValue());
    }

    @Test
    public void events_should_be_streamed() {
        Stream<ApplicationEvent> stream = api.apps().select(DEFAULT).events()
                .filter()
                .since(1524497180000L)
                .stream();

        assertNotNull(stream);
        List<ApplicationEvent> list = stream.collect(Collectors.toList());
        assertThat(list, Matchers.hasSize(3));
    }
}