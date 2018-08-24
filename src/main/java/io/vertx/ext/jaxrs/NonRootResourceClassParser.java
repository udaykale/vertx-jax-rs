package io.vertx.ext.jaxrs;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

import java.util.List;

class NonRootResourceClassParser {

    static <T> List<Router> parse(Class<T> clazz, Vertx vertx, InjectManager injectManager)
            throws MethodPathAnnotationNotFoundException {

        return null;
    }
}
