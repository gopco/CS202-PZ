package rs.ac.metropolitan.project.di;

import java.util.Map;

/**
 * Dependency injection module
 */
public interface InjectionModule {

    /**
     * Returns a map of class to instance mappings
     *
     * @return map of class to instance mappings
     */
    Map<Class<?>, Object> getMappings();
}
