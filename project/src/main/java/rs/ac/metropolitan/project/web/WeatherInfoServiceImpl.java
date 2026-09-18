package rs.ac.metropolitan.project.web;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Weather info service implementation
 */
public class WeatherInfoServiceImpl implements WeatherInfoService {

    private final DataFetcher dataFetcher;
    private final String url;

    /**
     * Creates a new instance of {@link WeatherInfoServiceImpl} and initializes data fetcher
     *
     * @param dataFetcher data fetcher
     */
    public WeatherInfoServiceImpl(DataFetcher dataFetcher, String url) {
        this.dataFetcher = dataFetcher;
        this.url = url;
    }

    /**
     * Returns the weather info
     *
     * @return weather info
     */
    @Override
    public WeatherInfo getWeatherInfo() {
        try {
            var data = dataFetcher.get(url);
            return new WeatherInfo(
                    data.get("current").get("temperature_2m").asDouble() +
                            data.get("current_units").get("temperature_2m").asText());
        } catch (IOException e) {
            return new WeatherInfo("");
        }
    }

    /**
     * Schedules the weather info refresh
     *
     * @param consumer consumer to consume the weather info
     * @return executor service
     */
    @Override
    public ExecutorService scheduleWeatherInfoRefresh(Consumer<WeatherInfo> consumer) {
        var executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            var weatherInfo = getWeatherInfo();
            consumer.accept(weatherInfo);
        }, 0, 10, TimeUnit.MINUTES);
        return executorService;
    }
}
