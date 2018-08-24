package io.vertx.ext.jaxrs;

public class MethodPathAnnotationNotFoundException extends Exception {

    public MethodPathAnnotationNotFoundException() {
        super();
    }

    public MethodPathAnnotationNotFoundException(String message) {
        super(message);
    }

    public MethodPathAnnotationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodPathAnnotationNotFoundException(Throwable cause) {
        super(cause);
    }
}
