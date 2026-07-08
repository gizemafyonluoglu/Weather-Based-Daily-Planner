import java.net.URI;
import java.time.Duration;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {

    public static Weather getWeatherFromInternet(double latitude, double longitude) {
        try {
            return getDetailedWeather(latitude, longitude);
        } catch (Exception firstError) {
            System.out.println("Detailed weather request failed.");
            System.out.println("Reason: " + firstError.getClass().getSimpleName() + " - " + firstError.getMessage());

            try {
                return getSimpleWeather(latitude, longitude);
            } catch (Exception secondError) {
                System.out.println("Could not get weather data from the internet.");
                System.out.println("Reason: " + secondError.getClass().getSimpleName() + " - " + secondError.getMessage());
                return new Weather(20, "clear", 0, 40);
            }
        }
    }

    private static Weather getDetailedWeather(double latitude, double longitude) throws Exception {
        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude
                + "&longitude=" + longitude
                + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m,weather_code";

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Java Weather Planner")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IllegalArgumentException("Weather API status code: " + response.statusCode()
                    + " Body: " + response.body());
        }

        String json = response.body();

        double temperature = extractDouble(json, "temperature_2m");
        double humidity = extractDouble(json, "relative_humidity_2m");
        double windSpeed = extractDouble(json, "wind_speed_10m");
        int weatherCode = (int) extractDouble(json, "weather_code");

        String condition = convertWeatherCode(weatherCode);

        return new Weather(temperature, condition, windSpeed, humidity);
    }

    private static Weather getSimpleWeather(double latitude, double longitude) throws Exception {
        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude
                + "&longitude=" + longitude
                + "&current_weather=true";

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Java Weather Planner")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IllegalArgumentException("Simple weather API status code: " + response.statusCode()
                    + " Body: " + response.body());
        }

        String json = response.body();

        double temperature = extractDouble(json, "temperature");
        double windSpeed = extractDouble(json, "windspeed");
        int weatherCode = (int) extractDouble(json, "weathercode");
        String condition = convertWeatherCode(weatherCode);

        return new Weather(temperature, condition, windSpeed, 40);
    }

    private static double extractDouble(String json, String key) {
        String searchText = "\"" + key + "\"";
        int searchStart = 0;

        while (true) {
            int keyIndex = json.indexOf(searchText, searchStart);

            if (keyIndex == -1) {
                throw new IllegalArgumentException("Could not find numeric value for " + key + " in weather response.");
            }

            int colonIndex = json.indexOf(":", keyIndex);
            if (colonIndex == -1) {
                throw new IllegalArgumentException("Could not find ':' after " + key + ".");
            }

            int startIndex = colonIndex + 1;

            while (startIndex < json.length() && json.charAt(startIndex) == ' ') {
                startIndex++;
            }

            int endIndex = startIndex;

            while (endIndex < json.length()
                    && json.charAt(endIndex) != ','
                    && json.charAt(endIndex) != '}') {
                endIndex++;
            }

            String valueText = json.substring(startIndex, endIndex).trim();

            if (valueText.startsWith("\"")) {
                searchStart = endIndex;
            } else {
                return Double.parseDouble(valueText);
            }
        }
    }

    private static String convertWeatherCode(int code) {
        if (code == 0) {
            return "clear";
        }
        if (code == 1 || code == 2 || code == 3) {
            return "cloudy";
        }
        if (code == 45 || code == 48) {
            return "foggy";
        }
        if ((code >= 51 && code <= 67) || (code >= 80 && code <= 82)) {
            return "rainy";
        }
        if ((code >= 71 && code <= 77) || (code >= 85 && code <= 86)) {
            return "snowy";
        }
        if (code >= 95 && code <= 99) {
            return "stormy";
        }

        return "unknown";
    }
}