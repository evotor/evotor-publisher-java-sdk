package io.evotor.market.publisher.api.v1.builder;

import io.evotor.market.publisher.api.v1.model.push.PushRequest;
import io.evotor.market.publisher.api.v1.model.push.PushScheduledTask;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PushNotifications {

    PushScheduledTask create(PushRequest request);

    default PushScheduledTask status(PushScheduledTask task) {
        return status(task.getId());
    }

    PushScheduledTask status(UUID taskId);

    CompletableFuture<PushScheduledTask> awaitComplete(UUID taskId);

}
