package io.evotor.market.publisher.api.v1.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ApplicationEvent {

    private UUID id;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date timestamp;

    private EventType type;
    private String action;

    @JsonTypeInfo(
            defaultImpl = UnknownType.class,
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Document.class, name = "document"),
            @JsonSubTypes.Type(value = EventPayload.class, name = "system"),
            @JsonSubTypes.Type(value = Store.class, name = "store"),
            @JsonSubTypes.Type(value = Device.class, name = "device"),
            @JsonSubTypes.Type(value = Employee.class, name = "employee"),
            @JsonSubTypes.Type(value = Product.class, name = "product"),
            @JsonSubTypes.Type(value = ProductImage.class, name = "product-image")
    })
    private EventPayload payload;

}
