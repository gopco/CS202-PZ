package rs.ac.metropolitan.project.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherInfoServiceImplTest {

    private WeatherInfoServiceImpl weatherInfoService;

    @BeforeEach
    void setUp() {
        weatherInfoService = new WeatherInfoServiceImpl(
                new JacksonDataFetcher(),
                getClass().getResource("/weather.json").toString());
    }

    @Test
    void testGetWeatherInfo() {
        var weatherInfo = weatherInfoService.getWeatherInfo();
        assertEquals(new WeatherInfo("8.5°C"), weatherInfo);
    }

    @Test
    void testScheduleWeatherInfoRefresh() throws InterruptedException {
        var weatherInfo = new AtomicReference<WeatherInfo>();
        var executorService = weatherInfoService.scheduleWeatherInfoRefresh(weatherInfo::set);
        Thread.sleep(1);
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        assertEquals(new WeatherInfo("8.5°C"), weatherInfo.get());
    }
}