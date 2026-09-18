package rs.ac.metropolitan.project.di;

import rs.ac.metropolitan.project.web.JacksonDataFetcher;
import rs.ac.metropolitan.project.web.WeatherInfoService;
import rs.ac.metropolitan.project.web.WeatherInfoServiceImpl;

import java.util.Map;

/**
 * Dependency injection module for weather
 */
public class WeatherModule implements InjectionModule {

    private final WeatherInfoService weatherInfoService;

    /**
     * Creates a new instance of {@link WeatherModule} and initializes weather service
     */
    public WeatherModule() {
        weatherInfoService = new WeatherInfoServiceImpl(
                new JacksonDataFetcher(),
                "https://api.open-meteo.com/v1/forecast?latitude=44.804&longitude=20.4651&current=temperature_2m");
    }

    /**
     * Returns a map of class to instance mappings
     *
     * @return map of class to instance mappings
     */
    @Override
    public Map<Class<?>, Object> getMappings() {
        return Map.of(WeatherInfoService.class, weatherInfoService);
    }
}
