package io.evotor.market.publisher.api.v1.model.event;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import io.evotor.market.api.v2.model.Resource;
import io.evotor.market.api.v2.model.device.Device;
import io.evotor.market.api.v2.model.document.Document;
import io.evotor.market.api.v2.model.employee.Employee;
import io.evotor.market.api.v2.model.group.ProductGroup;
import io.evotor.market.api.v2.model.product.AnyProduct;
import io.evotor.market.api.v2.model.product.image.ProductImage;
import io.evotor.market.api.v2.model.store.Store;

public enum EventType {

    StoreEvent          (Store.class),
    DeviceEvent         (Device.class),
    EmployeeEvent       (Employee.class),
    DocumentEvent       (Document.class),
    ProductEvent        (AnyProduct.class),
    ProductGroupEvent   (ProductGroup.class),
    ProductImageEvent   (ProductImage.class),
    SystemEvent         (null),

    @JsonEnumDefaultValue
    UnknownEvent        (null)
    ;

    private Class<? extends Resource> resourceClass;

    EventType(Class<? extends Resource> resourceClass) {
        this.resourceClass = resourceClass;
    }

    public Class<? extends Resource> getResourceClass() {
        return resourceClass;
    }
}
