package io.evotor.market.publisher.api.v1;

import io.evotor.market.publisher.api.v1.model.GUID;
import io.evotor.market.publisher.api.v1.model.NavigablePage;
import io.evotor.market.publisher.api.v1.model.application.AppDeployment;
import io.evotor.market.publisher.api.v1.model.application.AppInstallation;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Optional;

import static io.evotor.market.publisher.api.v1.ApiHolder.DEFAULT;
import static io.evotor.market.publisher.api.v1.ApiHolder.api;
import static org.junit.Assert.*;

public class AppInstallationsTest {

    @Test
    public void should_list_application_installations() {
        NavigablePage<AppInstallation> page = api.apps().select(DEFAULT).installations().fetch();

        assertNotNull(page);
        assertThat(page.getItems(), Matchers.hasSize(4));
        assertNotNull(page.getPaging().getNextCursor());

        Optional<NavigablePage<AppInstallation>> nextPageOpt = page.next();
        assertTrue(nextPageOpt.isPresent());

        NavigablePage<AppInstallation> nextPage = nextPageOpt.get();
        assertNotNull(nextPage);

        assertThat(nextPage.getItems(), Matchers.hasSize(1));
        assertNull(nextPage.getPaging().getNextCursor());
    }

    @Test
    public void should_fetch_single_installation() {
        AppInstallation installation = api.apps().select(DEFAULT).installations().select("userId").fetch();

        assertNotNull(installation);
        assertEquals(DEFAULT, installation.getAppId());
        assertNotNull(installation.getCreatedAt());
        assertNotNull(installation.getUpdatedAt());
        assertEquals("userId", installation.getUserId());
    }

    @Test
    public void should_fetch_installation_deployments() {
        NavigablePage<AppDeployment> page = api.apps().select(DEFAULT)
                .installations()
                .select("userId")
                .deployments()
                .fetch();

        assertNotNull(page);
        assertThat(page.getItems(), Matchers.hasSize(3));
        assertNull(page.getPaging().getNextCursor());
    }

    @Test
    public void should_fetch_installation_deployments_by_filter() {
        NavigablePage<AppDeployment> page = api.apps().select(DEFAULT)
                .installations()
                .select("userId")
                .deployments()
                .filter()
                .status("INSTALL_SUCCEEDED")
                .fetch();

        assertNotNull(page);
        assertThat(page.getItems(), Matchers.hasSize(3));
        assertNull(page.getPaging().getNextCursor());
    }

    @Test
    public void should_fetch_installation_deployment() {
        AppDeployment deployment = api.apps().select(DEFAULT)
                .installations()
                .select("userId")
                .deployments()
                .select(new GUID(DEFAULT))
                .fetch();

        assertNotNull(deployment);
        assertEquals("userId", deployment.getUserId());
        assertEquals(DEFAULT, deployment.getAppId());
        assertEquals(DEFAULT, deployment.getVersionId());
        assertEquals(DEFAULT, deployment.getDeviceId().asUUID());
        assertEquals("INSTALL_SUCCEEDED", deployment.getStatus());
        assertNotNull(deployment.getCreatedAt());
        assertNotNull(deployment.getUpdatedAt());
    }
}
