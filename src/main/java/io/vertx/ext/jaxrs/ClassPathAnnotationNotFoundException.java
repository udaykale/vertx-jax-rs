package io.vertx.ext.jaxrs;

public class ClassPathAnnotationNotFoundException extends Exception {

    public ClassPathAnnotationNotFoundException() {
        super();
    }

    public ClassPathAnnotationNotFoundException(String message) {
        super(message);
    }

    public ClassPathAnnotationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassPathAnnotationNotFoundException(Throwable cause) {
        super(cause);
    }
}
