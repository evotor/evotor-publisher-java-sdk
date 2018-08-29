package io.evotor.market.publisher.api.v1.model.application;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public abstract class AppItem {

    private UUID appId;
    private Date createdAt;
    private Date updatedAt;

}
