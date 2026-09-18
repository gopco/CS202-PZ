package rs.ac.metropolitan.project.di;

import org.hibernate.AssertionFailure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Dependency injection container
 */
public class ApplicationContext {

    private final Map<Class<?>, Object> mappings = new HashMap<>();

    /**
     * Creates a new instance of {@link ApplicationContext} with given modules
     *
     * @param modules modules to be registered
     */
    public ApplicationContext(InjectionModule... modules) {
        for (InjectionModule module : modules) {
            mappings.putAll(module.getMappings());
        }
    }

    /**
     * Returns an instance of given class
     *
     * @param clazz class to be instantiated
     * @param <T>   type of the class
     * @return instance of given class
     */
    public <T> T getInstance(Class<T> clazz) {
        var constructor = clazz.getConstructors()[0];
        var parameters = Arrays.stream(constructor.getParameterTypes())
                .map(mappings::get)
                .toArray();
        try {
            return (T) constructor.newInstance(parameters);
        } catch (Exception e) {
            throw new AssertionFailure("Can't create an instance", e);
        }
    }
}
