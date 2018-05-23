package io.evotor.market.publisher.api.token;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PersonalTokenProvider implements TokenProvider {

    private final String value;

    @Override
    public String get() {
        return value;
    }
}
