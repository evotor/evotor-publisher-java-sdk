package io.evotor.market.publisher.api.v1.model;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {

    private List<T> items;
    private Paging paging;

}
