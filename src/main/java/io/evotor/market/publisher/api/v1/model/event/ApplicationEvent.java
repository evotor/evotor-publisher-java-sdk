package io.evotor.market.publisher.api.v1.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.evotor.market.api.v2.model.Resource;
import io.evotor.market.api.v2.model.device.Device;
import io.evotor.market.api.v2.model.document.Document;
import io.evotor.market.api.v2.model.employee.Employee;
import io.evotor.market.api.v2.model.group.ProductGroup;
import io.evotor.market.api.v2.model.product.Product;
import io.evotor.market.api.v2.model.product.image.ProductImage;
import io.evotor.market.api.v2.model.store.Store;
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
            defaultImpl = System.class,
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Document.class, name = "DocumentEvent"),
            @JsonSubTypes.Type(value = System.class, name = "SystemEvent"),
            @JsonSubTypes.Type(value = Store.class, name = "StoreEvent"),
            @JsonSubTypes.Type(value = Device.class, name = "DeviceEvent"),
            @JsonSubTypes.Type(value = Employee.class, name = "EmployeeEvent"),
            @JsonSubTypes.Type(value = Product.class, name = "ProductEvent"),
            @JsonSubTypes.Type(value = ProductGroup.class, name = "ProductGroupEvent"),
            @JsonSubTypes.Type(value = ProductImage.class, name = "ProductImageEvent")
    })
    private Resource payload;

}
