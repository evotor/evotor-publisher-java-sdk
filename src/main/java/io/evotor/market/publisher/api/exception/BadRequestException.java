package io.evotor.market.publisher.api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class BadRequestException extends RuntimeException {

    private String code;
    private String message;
    private List<Violation> violations;

}
