package io.evotor.market.publisher.api.v1.impl;

import io.evotor.market.publisher.api.v1.ApiProvider;
import io.evotor.market.publisher.api.v1.builder.NavigableResource;
import io.evotor.market.publisher.api.v1.model.NavigablePage;
import io.evotor.market.publisher.api.v1.model.Page;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;

class Impl<T> implements NavigableResource<T> {

    final ApiProvider apiProvider;

    Impl(ApiProvider apiProvider) {
        this.apiProvider = apiProvider;
    }

    @SuppressWarnings("unchecked")
    protected  <T> T get(Class<T> target) {
        return (T) apiProvider.apply(target);
    }

    private <V> NavigablePage<V> buildNavigable(Page<V> current, Function<String, Page<V>> nextPage) {
        NavigablePage<V> page = new NavigablePage<V>() {
            @Override
            public Optional<NavigablePage<V>> next() {
                String nextCursor = current.getPaging().getNextCursor();
                return Optional.ofNullable(nextCursor)
                        .map(nextPage)
                        .map(page -> buildNavigable(page, nextPage));
            }
        };

        page.setItems(current.getItems());
        page.setPaging(current.getPaging());

        return page;
    }

    protected Function<String, Page<T>> nextPageProvider() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final NavigablePage<T> fetch() {
        Function<String, Page<T>> nextPage = nextPageProvider();
        Page<T> current = nextPage.apply(null);
        return buildNavigable(current, nextPage);
    }

    @Override
    public final Iterator<T> iterator() {
        return new PageableIterator<>(nextPageProvider());
    }
}
