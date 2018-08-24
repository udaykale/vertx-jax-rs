package io.vertx.ext.jaxrs;

public class VertxJaxRSParseException extends Exception {

    public VertxJaxRSParseException() {
        super();
    }

    public VertxJaxRSParseException(String message) {
        super(message);
    }

    public VertxJaxRSParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public VertxJaxRSParseException(Throwable cause) {
        super(cause);
    }
}
