package io.vertx.ext.jaxrs;

import io.vertx.core.Vertx;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Inject manager to provide a minimalistic dependency provider engine.
 */
final class DefaultInjectManager implements InjectManager {

    private final Map<Class<?>, ?> singletons;
    private final Set<Class<?>> allClasses;

    private DefaultInjectManager(Map<Class<?>, ?> singletons, Set<Class<?>> allClasses) {
        this.singletons = singletons;
        this.allClasses = allClasses;
    }

    static DefaultInjectManager of(Application application, Vertx vertx) {
        Objects.requireNonNull(application, "Application cannot be null");

        Set<Object> singletons = application.getSingletons() == null || application.getSingletons().isEmpty()
                ? new HashSet<>() : application.getSingletons();

        // create a map of class type and instance of singleton objects
        Map<Class<?>, Object> typeAndSingletons = singletons.stream()
                .collect(Collectors.toMap(Object::getClass, singleton -> singleton));

        // Add vertx and application to singleton class map
        typeAndSingletons.put(Application.class, application);
        typeAndSingletons.put(Vertx.class, vertx);

        Set<Class<?>> allClasses = new HashSet<>(application.getClasses());
        allClasses.addAll(typeAndSingletons.keySet());

        return new DefaultInjectManager(typeAndSingletons, allClasses);
    }

    @Override public <T> T getInstance(Class<T> clazz) throws InjectManagerException {

        if (clazz == null) throw new IllegalArgumentException("Argument clazz cannot be null");

        if (allClasses.contains(clazz)) {
            Object singleton = singletons.get(clazz);

            if (singleton != null)
                return clazz.cast(singleton);
            else {
                try {
                    return clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new InjectManagerException(e);
                }
            }
        } else {
            throw new InjectManagerException(String.format("Instance of %s was not found by the dependency engine",
                    clazz.getName()));
        }
    }

    @Override
    public Set<Class<?>> getAllClasses() {
        return allClasses;
    }
}
