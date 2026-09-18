package rs.ac.metropolitan.project.di;

import org.hibernate.cfg.Configuration;
import rs.ac.metropolitan.project.repository.AgencyRepository;
import rs.ac.metropolitan.project.repository.HibernateRepository;

import java.util.Map;

/**
 * Dependency injection module for repositories
 */
public class RepositoryModule implements InjectionModule {

    private final HibernateRepository hotelRepository;

    /**
     * Creates a new instance of {@link RepositoryModule} and initializes repositories
     */
    public RepositoryModule() {
        var configuration = new Configuration()
                .configure("hibernate.cfg.xml");
        var sessionFactory = configuration.buildSessionFactory();
        hotelRepository = new HibernateRepository(sessionFactory);
    }

    /**
     * Returns a map of class to instance mappings
     *
     * @return map of class to instance mappings
     */
    @Override
    public Map<Class<?>, Object> getMappings() {
        return Map.of(AgencyRepository.class, hotelRepository);
    }
}
