package io.evotor.market.publisher.api.v1.model.event;

import io.evotor.market.publisher.api.v1.model.GUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document implements EventPayload {

    private String type;
    private UUID id;
    private Map<UUID, Map<String, Object>> extras;
    private Long number;
    private Date closeDate;
    private int timeZoneOffset;
    private UUID sessionId;
    private int sessionNumber;
    private String closeUserId;
    private GUID deviceId;
    private GUID storeId;
    private String userId;
    private String version;

    @Override
    public Optional<String> userId() {
        return Optional.ofNullable(userId);
    }

    @Override
    public Optional<GUID> storeId() {
        return Optional.ofNullable(storeId);
    }

    @Override
    public Optional<GUID> deviceId() {
        return Optional.ofNullable(deviceId);
    }
}