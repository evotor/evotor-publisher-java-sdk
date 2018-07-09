package io.evotor.market.publisher.api.v1;

import io.evotor.market.publisher.api.exception.BadRequestException;
import io.evotor.market.publisher.api.exception.Violation;
import io.evotor.market.publisher.api.v1.builder.PushNotifications;
import io.evotor.market.publisher.api.v1.model.ScheduledTask;
import io.evotor.market.publisher.api.v1.model.push.PushInfo;
import io.evotor.market.publisher.api.v1.model.push.PushRequest;
import io.evotor.market.publisher.api.v1.model.push.PushScheduledTask;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static io.evotor.market.publisher.api.v1.ApiHolder.DEFAULT;
import static io.evotor.market.publisher.api.v1.ApiHolder.api;
import static org.junit.Assert.*;

public class PushNotificationTests {

    private final PushNotifications notifications = api.apps().select(DEFAULT)
            .pushNotifications();

    @Test
    public void request_should_be_created() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("id", 12345);
        payload.put("data", "foobar");

        PushScheduledTask task = notifications
                .create(PushRequest.builder()
                        .device("device-id-1")
                        .device("device-id-2")
                        .payload(payload)
                        .build());

        assertNotNull(task);
        assertEquals(DEFAULT, task.getId());
        assertEquals(ScheduledTask.Status.ACCEPTED, task.getStatus());
        assertNotNull(task.getModifiedAt());
        assertNotNull(task.getDetails());
        assertThat(task.getDetails(), Matchers.hasSize(3));
    }

    @Test
    public void scheduled_task_status_should_be_fetched() {
        PushScheduledTask task = notifications.status(DEFAULT);

        assertNotNull(task);
        assertEquals(DEFAULT, task.getId());
        assertEquals(ScheduledTask.Status.COMPLETED, task.getStatus());
        assertNotNull(task.getModifiedAt());
        assertNotNull(task.getDetails());
        assertThat(task.getDetails(), Matchers.hasSize(3));

        PushInfo info = task.getDetails().iterator().next();
        assertEquals(PushInfo.Status.DELIVERED, info.getStatus());
        assertNotNull(info.getActiveTill());
        assertEquals(DEFAULT.toString(), info.getDeviceId());
        assertNotNull(info.getReply());
        assertThat(info.getReply(), Matchers.instanceOf(Map.class));
    }

    @Test
    public void bad_request_should_be_processed() {
        try {
            api.apps().select(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                    .pushNotifications()
                    .create(PushRequest.builder()
                            .device("unknown-device-id")
                            .payload(Arrays.asList(1, 2, 3))
                            .build());
            fail("unreachable");
        } catch (BadRequestException e) {
            assertEquals("unknown_device", e.getCode());
            assertEquals("device with specified id not found", e.getMessage());
            assertThat(e.getViolations(), Matchers.hasSize(1));

            Violation violation = e.getViolations().iterator().next();
            assertEquals("device_id", violation.getSubject());
            assertEquals("device not found: unknown-device-id", violation.getReason());
        }
    }
}
