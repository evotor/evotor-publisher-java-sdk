package io.evotor.market.publisher.api.v1.model;

import java.util.Optional;

public abstract class NavigablePage<T> extends Page<T> {

    public abstract Optional<NavigablePage<T>> next();

}
