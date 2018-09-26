package io.vertx.ext.jaxrs;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parse a resource class.
 */
class ResourceClassParser {
    static <T> Router parse(Class<T> clazz, Vertx vertx, InjectManager injectManager)
            throws ClassPathAnnotationNotFoundException, InjectManagerException {
        validate(clazz);

        // Fetch the base path from @Path
        String basePath = clazz.getAnnotation(Path.class).value();
        String finalBasePath = basePath.trim().isEmpty() ? "/" : basePath;
        Router baseRouter = Router.router(vertx);

        // TODO: @Consumes
        // TODO: @Produces

        for (Method method : clazz.getDeclaredMethods()) {
            Router router = ResourceMethodParser.parse(method, injectManager.getInstance(clazz), vertx);
            baseRouter.mountSubRouter(finalBasePath, router);
        }

        return baseRouter;
    }

    private static <T> void validate(Class<T> clazz) throws ClassPathAnnotationNotFoundException {
        // TODO: Check if the class is a resource class

        List<Annotation> pathAnnotations = Arrays.stream(clazz.getAnnotations())
                .filter(annotation -> annotation.annotationType() == Path.class)
                .collect(Collectors.toList());

        if (pathAnnotations.size() > 1) {
            String message = String.format("Class %s has more than 2 @Path annotations", clazz.getName());
            throw new ClassPathAnnotationNotFoundException(message);
        } else if (pathAnnotations.isEmpty()) {
            boolean hasPathInMethod = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(AccessibleObject::isAccessible)
                    .anyMatch(method -> method.isAnnotationPresent(Path.class));

            if (!hasPathInMethod) throw new ClassPathAnnotationNotFoundException("");
        } else if (pathAnnotations.size() == 1) {
            // TODO: Add debug log
        } else {
            // TODO: Error message
            throw new ClassPathAnnotationNotFoundException("");
        }
    }
}
