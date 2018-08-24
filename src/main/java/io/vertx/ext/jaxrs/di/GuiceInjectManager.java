package io.vertx.ext.jaxrs.di;

import com.google.inject.Injector;
import com.google.inject.Key;
import io.vertx.ext.jaxrs.InjectManager;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Inject manager using the Guice dependency provider engine.
 */
public class GuiceInjectManager implements InjectManager {

    private final Injector injector;

    private GuiceInjectManager(Injector injector) {
        this.injector = injector;
    }

    public static GuiceInjectManager of(Injector injector) {
        Objects.requireNonNull(injector, "Injector cannot be null");
        return new GuiceInjectManager(injector);
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        return injector.getInstance(clazz);
    }

    @Override
    public Set<Class<?>> getAllClasses() {
        return injector.getAllBindings().keySet().stream().map(Key::getClass).collect(Collectors.toSet());
    }
}
