package io.evotor.market.publisher.api.v1;

/**
 * Marker for a required oauth scope
 */
public @interface Scope {

    String value() default "";

    /**
     * Resource can be visited only by application owner token
     * and can't be shared as regular oauth resource
     */
    boolean isPrivate() default false;

}
