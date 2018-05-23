package io.evotor.market.publisher.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonIteratorDecoder;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static feign.Util.ensureClosed;

public class StreamDecoder extends JacksonDecoder {

    private final Decoder iteratorDecoder;

    public StreamDecoder(ObjectMapper mapper) {
        super(mapper);
        this.iteratorDecoder = JacksonIteratorDecoder.create(mapper);
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        if (!(type instanceof ParameterizedType)) {
            return super.decode(response, type);
        }

        ParameterizedType streamType = (ParameterizedType) type;
        if (!Stream.class.equals(streamType.getRawType())) {
            return super.decode(response, type);
        }

        Iterator<?> iterator =
                (Iterator) iteratorDecoder.decode(response, new IteratorParameterizedType(streamType));

        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterator, 0), false)
                .onClose(() -> {
                    if (iterator instanceof Closeable) {
                        ensureClosed((Closeable) iterator);
                    } else {
                        ensureClosed(response);
                    }
                });
    }

    final class IteratorParameterizedType implements ParameterizedType {

        private final ParameterizedType streamType;

        IteratorParameterizedType(ParameterizedType streamType) {
            this.streamType = streamType;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return streamType.getActualTypeArguments();
        }

        @Override
        public Type getRawType() {
            return Iterator.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
