package io.vertx.ext.jaxrs;

import java.util.Set;

/**
 * Concrete implementation of this interfaces must encapsulate some Dependency provider system.
 * The bare minimum requirements are:
 * <p>
 * 1. To provide Application sub-class.
 * 2. Root and non-root resource instances returned by getSingletons method of the application sub-class.
 * 3. Root and non-root resource classes returned by getClasses method of the application sub-class.
 * 2. Provider instances returned by getSingletons method of the application sub-class.
 * 3. Provider returned by getClasses method of the application sub-class.
 * <p>
 * The implementation may cache the instance of implementation of this class.
 */
public interface InjectManager {
    /**
     * Get the instance of a class.
     * InjectManagerException exception is thrown when:
     * 1. The given class does not exist in the inject manager
     * 2. If there were issues in instance creation
     *
     * @param clazz The java .class of expected class
     * @param <T>   The type of expected class
     * @return Instance of a class
     * @throws InjectManagerException   InjectManagerException
     * @throws IllegalArgumentException IllegalArgumentException if the argument was null
     */
    <T> T getInstance(Class<T> clazz) throws InjectManagerException;

    /**
     * Get a set of all classes
     *
     * @return Set of classes
     */
    Set<Class<?>> getAllClasses();
}
