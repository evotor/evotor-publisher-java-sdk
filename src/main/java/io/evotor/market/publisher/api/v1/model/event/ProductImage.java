package io.evotor.market.publisher.api.v1.model.event;

import io.evotor.market.publisher.api.v1.model.GUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Optional;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductImage extends AbstractPayload {

    @Data
    public static class Meta {
        private Long fileSize;
        private String extension;
        private Long height;
        private Long width;
    }

    private UUID id;
    private GUID storeId;
    private UUID productId;
    private String userId;

    private String url;
    private Meta meta;

    @Override
    public Optional<String> userId() {
        return Optional.of(userId);
    }

    @Override
    public Optional<GUID> storeId() {
        return Optional.of(storeId);
    }
}
