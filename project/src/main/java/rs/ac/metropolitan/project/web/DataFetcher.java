package rs.ac.metropolitan.project.web;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * Data fetcher for web requests
 */
public interface DataFetcher {

    /**
     * Returns a JSON node from the given URL
     *
     * @param url URL to fetch data from
     * @return JSON node from the given URL
     * @throws IOException if an I/O error occurs
     */
    JsonNode get(String url) throws IOException;
}
