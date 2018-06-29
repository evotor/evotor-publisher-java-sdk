package io.evotor.market.publisher.api.v1.model.application;

import io.evotor.market.publisher.api.v1.model.GUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppDeployment extends AppInstallation {

    private GUID deviceId;
    private UUID versionId;
    private String status;

}
