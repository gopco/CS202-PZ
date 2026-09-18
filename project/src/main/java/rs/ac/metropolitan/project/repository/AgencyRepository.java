package rs.ac.metropolitan.project.repository;

import rs.ac.metropolitan.project.model.Hotel;
import rs.ac.metropolitan.project.model.Reservation;

import java.util.List;

/**
 * Repository for agency
 */
public interface AgencyRepository {

    /**
     * Saves the hotel
     *
     * @param hotel hotel to save
     */
    void save(Hotel hotel);

    /**
     * Deletes the hotel
     *
     * @param hotel hotel to delete
     */
    void delete(Hotel hotel);

    /**
     * Finds all hotels
     *
     * @return list of all hotels
     */
    List<Hotel> findAll();

    /**
     * Saves the reservation
     *
     * @param reservation reservation to save
     */
    void save(Reservation reservation);

    /**
     * Deletes the reservation
     *
     * @param reservation reservation to delete
     */
    void delete(Reservation reservation);

}
