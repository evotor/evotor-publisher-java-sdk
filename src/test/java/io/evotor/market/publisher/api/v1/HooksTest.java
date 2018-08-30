package io.evotor.market.publisher.api.v1;

import io.evotor.market.publisher.api.v1.builder.Versions;
import io.evotor.market.publisher.api.v1.model.NavigablePage;
import io.evotor.market.publisher.api.v1.model.event.EventTrigger;
import io.evotor.market.publisher.api.v1.model.hook.DeliveryConfig;
import io.evotor.market.publisher.api.v1.model.hook.Hook;
import io.evotor.market.publisher.api.v1.model.hook.HookPrototype;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.evotor.market.publisher.api.v1.ApiHolder.DEFAULT;
import static io.evotor.market.publisher.api.v1.ApiHolder.api;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class HooksTest {

    private static final Versions.Hooks HOOKS = api.apps()
            .select(DEFAULT)
            .versions()
            .select(DEFAULT)
            .hooks();

    @Test
    public void should_create_hook_settings() {
        Hook hook = HOOKS
                .create(HookPrototype.builder()
                        .withConfig(DeliveryConfig.builder()
                                .build())
                        .withEvent(EventTrigger.document)
                        .withEvent(EventTrigger.store)
                        .withEvent(EventTrigger.device)
                        .build());

        assertNotNull(hook);
    }

    @Test
    public void should_fetch_hooks_list() {
        NavigablePage<Hook> page = HOOKS
                .fetch();

        assertNotNull(page);
        assertThat(page.getItems(), Matchers.hasSize(1));
    }
}
