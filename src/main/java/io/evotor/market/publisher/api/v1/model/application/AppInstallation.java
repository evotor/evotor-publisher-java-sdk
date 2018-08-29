package io.evotor.market.publisher.api.v1.model.application;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppInstallation extends AppItem {

    private String userId;

}
