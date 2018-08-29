package io.evotor.market.publisher.api.v1.model.application;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class App extends AppItem {

    public enum Environment {
        SANDBOX, TEST, PRODUCTION
    }

    @Data
    private static class Manifest {
        private String packageId;
        private String packageUrl;
        private Integer versionCode;
    }

    private UUID versionId;
    private UUID publisherId;

    private String name;
    private String status;
    private String shortName;
    private String description;
    private Manifest manifest;
    private Environment environment;

}
