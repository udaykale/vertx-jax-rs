package io.vertx.ext.jaxrs;

public class InjectManagerException extends Exception {

    public InjectManagerException() {
        super();
    }

    public InjectManagerException(String message) {
        super(message);
    }

    public InjectManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InjectManagerException(Throwable cause) {
        super(cause);
    }
}
