package io.evotor.market.publisher.api.v1.model.push;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class PushRequest {

    @Size(min = 1, max = 100)
    @Singular("device")
    private Set<String> devices;

    @Future
    private Date activeUntil;

    @NotNull
    private Object payload;

}
