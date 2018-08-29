package io.evotor.market.publisher.api.v1.model.event;

import io.evotor.market.publisher.api.v1.model.GUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductGroup extends AbstractPayload {

    private UUID id;
    private UUID parentId;
    private String name;
    private List<Attribute> attributes;
    private List<String> barcodes;
    private String userId;
    private GUID storeId;

    @Data
    public static class Attribute {

        private UUID id;
        private String name;
        private Collection<AttributeChoice> choices;

    }

    @Data
    public static class AttributeChoice {

        private UUID id;
        private String name;

    }
}
