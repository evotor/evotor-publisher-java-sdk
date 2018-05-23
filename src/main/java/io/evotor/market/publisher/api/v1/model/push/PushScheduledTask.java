package io.evotor.market.publisher.api.v1.model.push;

import io.evotor.market.publisher.api.v1.model.ScheduledTask;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PushScheduledTask extends ScheduledTask {

    private List<PushInfo> details;

}
