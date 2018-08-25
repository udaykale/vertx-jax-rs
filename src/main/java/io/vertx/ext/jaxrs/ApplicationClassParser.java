package io.vertx.ext.jaxrs;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parses the Application class
 */
class ApplicationClassParser {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationClassParser.class);

    /**
     * Validates the Application class. Checks if there is a single @ApplicationPath annotation. If proper annotations
     * are provided it starts to parse all resource and non resource classes.
     *
     * @param clazz         Class to parse. Must be a sub-class of {@link Application}
     * @param vertx         Vertx instance
     * @param injectManager InjectManager to provide objects necessary for application
     * @param <T>           The type of class to parse. Must be a sub-class of {@link Application}
     * @return Base router of the application
     * @throws VertxJaxRSParseException The given class was not sub-type of Application Class or Some error in parsing.
     */
    static <T extends Application> Router parse(Class<T> clazz, Vertx vertx, InjectManager injectManager)
            throws VertxJaxRSParseException {

        validate(clazz);
        // Fetch the base path from @ApplicationPath
        String basePath = clazz.getAnnotation(ApplicationPath.class).value();
        String finalBasePath = basePath.trim().isEmpty() ? "/" : basePath;
        Router baseRouter = Router.router(vertx);

        // TODO: Mount a router that generates the base request id.

        for (Class<?> cl : injectManager.getAllClasses()) {
            try {
                RootResourceClassParser.parse(cl, vertx, injectManager)
                        .forEach(router -> baseRouter.mountSubRouter(finalBasePath, router));
                NonRootResourceClassParser.parse(cl, vertx, injectManager)
                        .forEach(router -> baseRouter.mountSubRouter(finalBasePath, router));
                // TODO: @Feature and @Provider classes
            } catch (ClassPathAnnotationNotFoundException | MethodPathAnnotationNotFoundException e) {
                // Ignore
            }
        }

        return baseRouter;
    }

    /**
     * Validates the class annotated with @ApplicationPath and implements Application class.
     *
     * @param clazz Class to parse. Must be a sub-class of {@link Application}
     * @param <T>   The type of class to parse. Must be a sub-class of {@link Application}
     * @throws VertxJaxRSParseException The given class was not sub-type of Application Class or Some error in parsing.
     */
    private static <T extends Application> void validate(Class<T> clazz) throws VertxJaxRSParseException {

        Annotation[] annotations = clazz.getAnnotations();

        List<Annotation> applicationPathAnnotation = Arrays.stream(annotations)
                .filter(annotation -> annotation.annotationType() == ApplicationPath.class)
                .collect(Collectors.toList());

        if (applicationPathAnnotation.size() != 1) {
            String msg = String.format("Class %s cannot have more than one @ApplicationPath annotations",
                    clazz.getCanonicalName());
            throw new VertxJaxRSParseException(msg);
        }
    }
}
