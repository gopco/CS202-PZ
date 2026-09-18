package rs.ac.metropolitan.project.web;

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * Weather info service
 */
public interface WeatherInfoService {

    /**
     * Returns the weather info
     *
     * @return weather info
     */
    WeatherInfo getWeatherInfo();

    /**
     * Schedules the weather info refresh
     *
     * @param consumer consumer to consume the weather info
     * @return executor service
     */
    ExecutorService scheduleWeatherInfoRefresh(Consumer<WeatherInfo> consumer);
}
