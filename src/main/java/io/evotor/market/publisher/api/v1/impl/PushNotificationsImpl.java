package io.evotor.market.publisher.api.v1.impl;

import io.evotor.market.publisher.api.v1.ApiProvider;
import io.evotor.market.publisher.api.v1.PushNotificationsApi;
import io.evotor.market.publisher.api.v1.builder.PushNotifications;
import io.evotor.market.publisher.api.v1.model.push.PushRequest;
import io.evotor.market.publisher.api.v1.model.push.PushScheduledTask;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

class PushNotificationsImpl extends Impl<PushScheduledTask> implements PushNotifications {

    private final UUID appId;

    PushNotificationsImpl(UUID appId, ApiProvider apiProvider) {
        super(apiProvider);
        this.appId = appId;
    }

    @Override
    public PushScheduledTask create(PushRequest request) {
        return get(PushNotificationsApi.class).create(appId, request);
    }

    @Override
    public PushScheduledTask status(UUID taskId) {
        return get(PushNotificationsApi.class).status(appId, taskId);
    }

    @Override
    public CompletableFuture<PushScheduledTask> awaitComplete(UUID taskId) {
        return CompletableFuture.supplyAsync(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                PushScheduledTask status = status(taskId);
                if (PushScheduledTask.Status.COMPLETED == status.getStatus()) {
                    return status;
                }

                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(500));
            }

            return null;
        });
    }
}
