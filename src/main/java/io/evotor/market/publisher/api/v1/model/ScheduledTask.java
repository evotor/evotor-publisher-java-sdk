package io.evotor.market.publisher.api.v1.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ScheduledTask {

    private UUID id;
    private Date modifiedAt;
    private Status status;

    public enum Status {
        ACCEPTED,
        RUNNING,
        FAILED,
        COMPLETED
    }
}
