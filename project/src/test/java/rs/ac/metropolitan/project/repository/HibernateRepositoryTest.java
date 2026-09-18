package rs.ac.metropolitan.project.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.metropolitan.project.model.Hotel;
import rs.ac.metropolitan.project.model.Reservation;

import static org.junit.jupiter.api.Assertions.*;

class HibernateRepositoryTest {

    private SessionFactory sessionFactory;
    private HibernateRepository repository;

    @BeforeEach
    void setUp() {
        var configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        sessionFactory = configuration.buildSessionFactory();
        repository = new HibernateRepository(sessionFactory);
    }

    @AfterEach
    void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    void testHotelOperations() {
        var hotel = new Hotel();
        repository.save(hotel);

        var hotels = repository.findAll();
        assertFalse(hotels.isEmpty());

        repository.delete(hotel);
        hotels = repository.findAll();
        assertTrue(hotels.isEmpty());
    }

    @Test
    void testReservationOperations() {
        var reservation = new Reservation();
        repository.save(reservation);
        assertEquals(1L, reservation.getId());

        assertDoesNotThrow(() -> repository.delete(reservation));
    }
}