package io.evotor.market.publisher.api.v1.model.push;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushInfo {

    public enum Status {
        ACCEPTED,
        DELIVERED,
        EXPIRED,
        REJECTED,

        @JsonEnumDefaultValue
        UNKNOWN
    }

    private String deviceId;
    private Status status;
    private Date activeTill;
    private Date createdAt;
    private Date sentAt;
    private Date rejectedAt;
    private Object reply;

}
