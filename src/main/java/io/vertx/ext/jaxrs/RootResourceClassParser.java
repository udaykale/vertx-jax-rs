package io.vertx.ext.jaxrs;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parse a resource class.
 */
class RootResourceClassParser {

    static <T> List<Router> parse(Class<T> clazz, Vertx vertx, InjectManager injectManager)
            throws ClassPathAnnotationNotFoundException {

        // TODO: Check if the class is a resource class

        List<Annotation> pathAnnotations = Arrays.stream(clazz.getAnnotations())
                .filter(annotation -> annotation.annotationType() == Path.class)
                .collect(Collectors.toList());

        if (pathAnnotations.size() != 1) {
            String message = String.format("Class %s has more than 2 @Path annotations", clazz.getName());
            throw new ClassPathAnnotationNotFoundException(message);
        } else {
            // Fetch the base path from @Path
            String basePath = clazz.getAnnotation(Path.class).value();
            String finalBasePath = basePath.trim().isEmpty() ? "/" : basePath;
            Router baseRouter = Router.router(vertx);

            // TODO: @Consumes
            // TODO: @Produces

            return Arrays.stream(clazz.getMethods())
                    .map(method -> ResourceMethodParser.parse(method, new ResourceClassInfo(), vertx, injectManager))
                    .flatMap(Collection::stream)
                    .map(router -> baseRouter.mountSubRouter(finalBasePath, router))
                    .collect(Collectors.toList());
        }
    }
}
