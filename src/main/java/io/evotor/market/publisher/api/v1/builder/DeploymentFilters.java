package io.evotor.market.publisher.api.v1.builder;

import io.evotor.market.publisher.api.v1.model.application.AppDeployment;

public interface DeploymentFilters {

    interface Builder extends FinalStage {

        FinalStage status(String status);

    }

    interface FinalStage extends NavigableResource<AppDeployment> {

    }
}
