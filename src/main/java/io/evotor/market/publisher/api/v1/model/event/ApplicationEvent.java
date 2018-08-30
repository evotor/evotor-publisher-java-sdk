package io.evotor.market.publisher.api.v1.model.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.evotor.market.api.v2.model.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = EventDeserializer.class)
public class ApplicationEvent {

    private UUID id;
    private Date timestamp;
    private EventType type;
    private String action;
    private Resource payload;

}
