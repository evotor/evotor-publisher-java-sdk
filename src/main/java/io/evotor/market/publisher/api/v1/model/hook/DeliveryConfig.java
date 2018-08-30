package io.evotor.market.publisher.api.v1.model.hook;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.net.MalformedURLException;
import java.net.URL;

@Data
public class DeliveryConfig {

    public enum ContentType {
        V2
    }

    private URL url;

    @Size(max = 100, groups = {HookPrototype.Create.class, Default.class})
    private String secret;

    @NotNull(groups = {HookPrototype.Create.class, Default.class})
    private ContentType contentType = ContentType.V2;

    private boolean insecureSSL;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private DeliveryConfig config;

        public Builder() {
            this.config = new DeliveryConfig();
        }

        public Builder withUrl(URL url) {
            this.config.setUrl(url);
            return this;
        }

        public Builder withUrl(String url) {
            try {
                return withUrl(new URL(url));
            } catch (MalformedURLException e) {
                throw new IllegalStateException(e);
            }
        }

        public Builder withSecret(String secret) {
            this.config.setSecret(secret);
            return this;
        }

        public Builder insecureSSL() {
            this.config.setInsecureSSL(true);
            return this;
        }

        public Builder secureSSL() {
            this.config.setInsecureSSL(false);
            return this;
        }

        public DeliveryConfig build() {
            return this.config;
        }
    }
}
