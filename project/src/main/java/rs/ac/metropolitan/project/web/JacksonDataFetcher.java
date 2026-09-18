package rs.ac.metropolitan.project.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

/**
 * Data fetcher for web requests that uses Jackson
 */
public class JacksonDataFetcher implements DataFetcher {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Returns a JSON node from the given URL
     *
     * @param url URL to fetch data from
     * @return JSON node from the given URL
     * @throws IOException if an I/O error occurs
     */
    @Override
    public JsonNode get(String url) throws IOException {
        return objectMapper.readValue(new URL(url), new TypeReference<>() {
        });
    }
}
