package io.evotor.market.publisher.api.v1.builder;

import io.evotor.market.publisher.api.v1.model.NavigablePage;

public interface NavigableResource<T> extends Iterable<T> {

    NavigablePage<T> fetch();
}
