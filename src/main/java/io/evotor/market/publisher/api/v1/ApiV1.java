package io.evotor.market.publisher.api.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import feign.Feign;
import feign.Logger;
import feign.Response;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import io.evotor.market.publisher.api.exception.ExceptionResolver;
import io.evotor.market.publisher.api.token.TokenProvider;
import io.evotor.market.publisher.api.v1.builder.Api;
import io.evotor.market.publisher.api.v1.impl.ApiImpl;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import static com.fasterxml.jackson.databind.DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;

@RequiredArgsConstructor
public class ApiV1 {

    private final String apiUrl;
    private final Feign.Builder builder;

    public static ApiV1 of() {
        return of(null, "https://dev.evotor.ru/");
    }

    public static ApiV1 of(TokenProvider provider, String apiUrl) {
        Feign.Builder builder = prepareBuilder(provider);

        return new ApiV1(apiUrl, builder);
    }

    public static Feign.Builder prepareBuilder(TokenProvider provider) {
        return prepareBuilder(provider, buildObjectMapper());
    }

    public static Feign.Builder prepareBuilder(TokenProvider provider, ObjectMapper mapper) {
        return Feign.builder()
                    .decoder(new JacksonDecoder(mapper))
                    .encoder(new JacksonEncoder(mapper))
                    .logger(new Slf4jNoResponseLogger())
                    .logLevel(Logger.Level.FULL)
                    .client(new OkHttpClient())
                    .errorDecoder(new ExceptionResolver())
                    .requestInterceptor(template -> {
                        template.header("Authorization", "Bearer " + provider.get());
                        template.header("User-Agent", "X-Evotor-Publisher-Api-Demo");
                        if (!template.headers().containsKey("Accept")) {
                            template.header("Accept", "application/vnd.evotor.v2+json");
                        }

                        if (template.body() != null && template.body().length > 0) {
                            template.header("Content-Type", "application/json");
                        }
                    });
    }

    // disable logging for streaming support
    public static class Slf4jNoResponseLogger extends Slf4jLogger {
        @Override
        protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
            return response;
        }
    }

    public static ObjectMapper buildObjectMapper() {
        return new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY)
                .enable(READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
    }

    public Api build() {
        return new ApiImpl((ApiProvider<ApiInterface>) this::buildApi);
    }

    private <T extends ApiInterface> T buildApi(Class<T> apiType) {
        return builder.target(apiType, apiUrl);
    }
}
