package io.evotor.market.publisher.api.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.*;
import feign.codec.Encoder;
import feign.jackson.JacksonEncoder;
import io.evotor.market.publisher.api.v1.builder.Api;
import net.javacrumbs.jsonunit.JsonMatchers;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Type;
import java.util.*;

import static io.evotor.market.publisher.api.v1.ApiV1.buildObjectMapper;

public class ApiHolder {

    public static final UUID DEFAULT = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final Api api;

    static {
        Feign.Builder builder = ApiV1.prepareBuilder(() -> "any")
                .encoder(new ValidationEncoderDecorator(new JacksonEncoder(buildObjectMapper())))
                .client(getClient())
                .requestInterceptor(template -> template.header("User-Agent", "Publisher-Api-Tests"));

        api = new ApiV1("dummy", builder).build();
    }

    private static Client getClient() {
        ObjectMapper mapper = buildObjectMapper();
        return (request, options) -> {
            String path = StringUtils.substringAfter(request.url(), "dummy");

            int index = path.lastIndexOf("/");
            String prefix = (path.substring(0, index) + "/" + request.method() + " " + path.substring(index + 1))
                    .replace(DEFAULT.toString(),"$default$");

            InputStream inputStream = ApiHolder.class.getResourceAsStream(prefix + ".in.json");
            if (inputStream != null) {
                JsonNode expected = mapper.readTree(inputStream);
                JsonNode actual = mapper.readTree(request.body());

                Assert.assertThat(expected, JsonMatchers.jsonEquals(actual));
            }

            InputStream outputStream = ApiHolder.class.getResourceAsStream(prefix + ".json");
            if (outputStream == null) {
                return Response.builder()
                        .status(404)
                        .headers(Collections.emptyMap())
                        .build();
            }

            int status = 200;
            byte[] bytes = Util.toByteArray(outputStream);
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
            String firstLine = reader.readLine();
            if (firstLine.startsWith("// HTTP/1.1")) {
                String code = firstLine.substring(12, 15);
                status = Integer.parseInt(code);
                bytes = Arrays.copyOfRange(bytes, firstLine.length(), bytes.length);
            }

            return Response.builder()
                    .status(status)
                    .body(bytes)
                    .headers(new HashMap<String, Collection<String>>() {{
                        put("Content-Type", Collections.singleton("application/vnd.evotor.v2+json"));
                    }})
                    .build();
        };
    }

    private static class ValidationEncoderDecorator implements Encoder {

        private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        private final Encoder delegate;

        ValidationEncoderDecorator(Encoder delegate) {
            this.delegate = delegate;
        }

        @Override
        public void encode(Object object, Type bodyType, RequestTemplate template) {
            Set<ConstraintViolation<Object>> violationSet = validator.validate(object);
            if (!violationSet.isEmpty()) {
                throw new ConstraintViolationException(violationSet);
            }

            delegate.encode(object, bodyType, template);
        }
    }
}