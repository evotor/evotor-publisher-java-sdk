package io.evotor.market.publisher.api.v1.model.token;

import lombok.Data;

import java.util.Date;

@Data
public class TemporaryToken {

    private String token;
    private Date expiredAt;

}
