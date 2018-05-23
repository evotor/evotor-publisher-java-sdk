package io.evotor.market.publisher.api.v1;

import java.util.function.Function;

public interface ApiProvider<T extends ApiInterface> extends Function<Class<T>, T> {

}
