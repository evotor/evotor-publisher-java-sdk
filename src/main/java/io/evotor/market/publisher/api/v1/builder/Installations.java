package io.evotor.market.publisher.api.v1.builder;

import io.evotor.market.publisher.api.v1.model.GUID;
import io.evotor.market.publisher.api.v1.model.application.AppDeployment;
import io.evotor.market.publisher.api.v1.model.application.AppInstallation;

public interface Installations extends NavigableResource<AppInstallation> {

    InstallationInstance select(String userId);

    interface InstallationInstance {

        AppInstallation fetch();

        Deployments deployments();

    }

    interface Deployments extends NavigableResource<AppDeployment> {

        DeploymentInstance select(GUID deviceId);

        DeploymentFilters.Builder filter();

    }

    interface DeploymentInstance {

        AppDeployment fetch();

    }
}
