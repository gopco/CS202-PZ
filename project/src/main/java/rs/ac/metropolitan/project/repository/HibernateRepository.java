package rs.ac.metropolitan.project.repository;

import org.hibernate.SessionFactory;
import rs.ac.metropolitan.project.model.Hotel;
import rs.ac.metropolitan.project.model.Reservation;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for agency that uses Hibernate
 */
public class HibernateRepository implements AgencyRepository {
    private final SessionFactory sessionFactory;

    /**
     * Creates a new instance of {@link HibernateRepository} and initializes session factory
     *
     * @param sessionFactory session factory
     */
    public HibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Saves the hotel
     *
     * @param hotel hotel to save
     */
    @Override
    public void save(Hotel hotel) {
        sessionFactory.inTransaction(session -> session.merge(hotel));
    }

    /**
     * Deletes the hotel
     *
     * @param hotel hotel to delete
     */
    @Override
    public void delete(Hotel hotel) {
        sessionFactory.inTransaction(session -> session.remove(hotel));
    }

    /**
     * Finds all hotels
     *
     * @return list of all hotels
     */
    @Override
    public List<Hotel> findAll() {
        var hotels = new ArrayList<Hotel>();

        sessionFactory.inTransaction(session ->
                hotels.addAll(session.createQuery("from Hotel", Hotel.class).list()));

        return hotels;
    }

    /**
     * Saves the reservation
     *
     * @param reservation reservation to save
     */
    @Override
    public void save(Reservation reservation) {
        sessionFactory.inTransaction(session -> session.merge(reservation));
    }

    /**
     * Deletes the reservation
     *
     * @param reservation reservation to delete
     */
    @Override
    public void delete(Reservation reservation) {
        sessionFactory.inTransaction(session -> {
            Reservation managedReservation =
                    session.get(Reservation.class, reservation.getId());

            if (managedReservation != null) {
                session.remove(managedReservation);
            }
        });
    }
}