package io.evotor.market.publisher.api.v1.model.event;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import io.evotor.market.publisher.api.v1.model.GUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractPayload {

    public enum ProductType {
        @JsonEnumDefaultValue
        NORMAL,
        ALCOHOL_MARKED,
        ALCOHOL_NOT_MARKED,
        SERVICE
    }

    public enum TaxType {
        @JsonEnumDefaultValue
        NO_VAT,
        VAT_0,
        VAT_10,
        VAT_18,
        VAT_10_110,
        VAT_18_118
    }

    private UUID id;
    private String parentId;
    private ProductType type;
    private String name;
    private String code;
    private Collection<String> barcodes;
    private BigDecimal price;
    private BigDecimal costPrice;
    private BigDecimal quantity;
    private String measureName;
    private TaxType tax;
    private Boolean allowToSell;
    private Map<UUID, UUID> attributesChoices;
    private String description;
    private String articleNumber;
    private Collection<String> alcocodes;
    private BigDecimal alcoholByVolume;
    private Integer alcoholProductKindCode;
    private BigDecimal tareVolume;

    private String userId;
    private GUID storeId;

    @Override
    public Optional<String> userId() {
        return Optional.ofNullable(userId);
    }

    @Override
    public Optional<GUID> storeId() {
        return Optional.ofNullable(storeId);
    }
}
