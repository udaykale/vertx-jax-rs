package io.vertx.ext.jaxrs;

/**
 * Holds the necessary config to run a JAX-RS application on vertx
 */
public final class VertxJaxRSApplicationConf {

    private final int port;

    private VertxJaxRSApplicationConf(int port) {
        this.port = port;
    }

    public static VertxJaxRSApplicationConf of(int port) {

        assert port > 0;
        return new VertxJaxRSApplicationConf(port);
    }

    public int getPort() {
        return port;
    }
}
