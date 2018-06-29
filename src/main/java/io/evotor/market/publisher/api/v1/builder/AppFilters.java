package io.evotor.market.publisher.api.v1.builder;

import io.evotor.market.publisher.api.v1.model.application.App;

public interface AppFilters extends NavigableResource<App> {

    interface Builder extends AppEnvironmentBuilder {

        AppEnvironmentBuilder status(String status);

    }

    interface AppEnvironmentBuilder extends FinalStage {

        FinalStage environment(App.Environment environment);

    }

    interface FinalStage extends NavigableResource<App> {

    }
}
