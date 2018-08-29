package io.evotor.market.publisher.api.v1;

import io.evotor.market.publisher.api.v1.model.Page;
import io.evotor.market.publisher.api.v1.model.application.App;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.evotor.market.publisher.api.v1.ApiHolder.DEFAULT;
import static io.evotor.market.publisher.api.v1.ApiHolder.api;
import static org.junit.Assert.*;

public class AppsTest {

    @Test
    public void should_list_all_apps() {
        Page<App> page = api.apps().fetch();

        assertNotNull(page);
        assertThat(page.getItems(), Matchers.hasSize(3));
        assertNotNull(page.getPaging());
        assertNotNull(page.getPaging().getNextCursor());
    }

    @Test
    public void should_list_all_app_versions() {
        Page<App> page = api.apps().select(DEFAULT).versions().fetch();

        assertNotNull(page);
        assertThat(page.getItems(), Matchers.hasSize(1));
        assertNotNull(page.getPaging());
        assertNull(page.getPaging().getNextCursor());
    }

    @Test
    public void should_fetch_specific_app_version() {
        App app = api.apps().select(DEFAULT).versions().select(DEFAULT).fetch();

        assertNotNull(app);
        assertEquals(DEFAULT, app.getVersionId());
        assertEquals(DEFAULT, app.getAppId());
        assertEquals("name", app.getName());
        assertEquals("short name", app.getShortName());
    }

    @Test
    public void should_list_all_app_s_by_filter() {
        Page<App> page = api.apps()
                .filter()
                .status("REVIEW")
                .environment(App.Environment.SANDBOX)
                .fetch();

        assertNotNull(page);
        assertThat(page.getItems(), Matchers.hasSize(1));
        assertNotNull(page.getPaging());
        assertNull(page.getPaging().getNextCursor());
    }

    @Test
    public void should_list_all_app_versions_by_filter() {
        Page<App> page = api.apps().select(DEFAULT).versions()
                .filter()
                .status("PUBLISHED")
                .environment(App.Environment.PRODUCTION)
                .fetch();

        assertNotNull(page);
        assertThat(page.getItems(), Matchers.hasSize(1));
        assertNotNull(page.getPaging());
        assertNull(page.getPaging().getNextCursor());
    }

    @Test
    public void should_fetch_published_version() {
        App app = api.apps().select(DEFAULT).fetchPublishedVersion();

        assertNotNull(app);
        assertEquals(DEFAULT, app.getVersionId());
        assertEquals(DEFAULT, app.getAppId());
        assertEquals("name", app.getName());
        assertEquals("short name", app.getShortName());
    }
}
