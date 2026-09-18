package rs.ac.metropolitan.project.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JacksonDataFetcherTest {

    private JacksonDataFetcher dataFetcher;

    @BeforeEach
    void setUp() {
        dataFetcher = new JacksonDataFetcher();
    }

    @Test
    void testFetchData() throws IOException {
        var url = getClass().getResource("/weather.json");
        var data = dataFetcher.get(url.toString());
        assertNotNull(data);
        assertNotNull(data.get("current"));
        assertNotNull(data.get("current_units"));
    }
}