package io.evotor.market.publisher.api.v1.model.event;

import lombok.Data;

import java.util.Date;

@Data
public abstract class AbstractPayload implements EventPayload {

    private Date createdAt;
    private Date updatedAt;

}
