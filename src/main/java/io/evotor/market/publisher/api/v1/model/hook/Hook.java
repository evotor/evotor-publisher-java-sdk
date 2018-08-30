package io.evotor.market.publisher.api.v1.model.hook;

import io.evotor.market.api.v2.model.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class Hook extends HookPrototype implements Resource {

    private UUID id;
    private UUID appId;
    private UUID versionId;
    private Date createdAt;
    private Date updatedAt;

}
