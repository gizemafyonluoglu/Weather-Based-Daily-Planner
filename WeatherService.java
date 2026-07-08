import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {

    public static Weather getWeatherFromInternet(double latitude, double longitude) {
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude
                    + "&longitude=" + longitude
                    + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m,weather_code";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();

            double temperature = extractDouble(json, "temperature_2m");
            double humidity = extractDouble(json, "relative_humidity_2m");
            double windSpeed = extractDouble(json, "wind_speed_10m");
            int weatherCode = (int) extractDouble(json, "weather_code");

            String condition = convertWeatherCode(weatherCode);

            return new Weather(temperature, condition, windSpeed, humidity);

        } catch (Exception e) {
            System.out.println("Could not get weather data from the internet.");
            return new Weather(20, "clear", 0, 40);
        }
    }

    private static double extractDouble(String json, String key) {
        String searchText = "\"" + key + "\":";
        int startIndex = json.indexOf(searchText);

        if (startIndex == -1) {
            return 0;
        }

        startIndex = startIndex + searchText.length();
        int endIndex = json.indexOf(",", startIndex);

        if (endIndex == -1) {
            endIndex = json.indexOf("}", startIndex);
        }

        return Double.parseDouble(json.substring(startIndex, endIndex).trim());
    }

    private static String convertWeatherCode(int code) {
        if (code == 0) return "clear";
        if (code == 1 || code == 2 || code == 3) return "cloudy";
        if (code == 45 || code == 48) return "foggy";
        if ((code >= 51 && code <= 67) || (code >= 80 && code <= 82)) return "rainy";
        if ((code >= 71 && code <= 77) || (code >= 85 && code <= 86)) return "snowy";
        if (code >= 95 && code <= 99) return "stormy";

        return "unknown";
    }
}