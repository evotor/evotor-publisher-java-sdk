package io.evotor.market.publisher.api.v1.model.hook;

import io.evotor.market.publisher.api.v1.model.event.EventTrigger;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class HookPrototype {

    public interface Create {}

    @NotEmpty(groups = Create.class)
    @Size(min = 1, groups = Default.class, message = "{javax.validation.constraints.NotEmpty.message}")
    private Set<@NotNull(groups = {Default.class,  Create.class }) EventTrigger> events;

    @Valid
    @NotNull(groups = Create.class)
    private DeliveryConfig config;

    private boolean active = true;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private HookPrototype prototype;

        public Builder() {
            this.prototype = new HookPrototype();
        }

        public Builder withConfig(DeliveryConfig config) {
            this.prototype.setConfig(config);
            return this;
        }

        public Builder withEvent(EventTrigger trigger) {
            Set<EventTrigger> events = prototype.getEvents();
            if (events == null) {
                events = new LinkedHashSet<>();
                prototype.setEvents(events);
            }

            events.add(trigger);
            return this;
        }

        public Builder active() {
            this.prototype.setActive(true);
            return this;
        }

        public Builder inactive() {
            this.prototype.setActive(false);
            return this;
        }

        public HookPrototype build() {
            return prototype;
        }
    }
}
