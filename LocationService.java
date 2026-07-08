import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LocationService {

    public static double[] getLocationFromInternet() {
        try {
            String url = "https://ipapi.co/json/";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();

            double latitude = extractDouble(json, "latitude");
            double longitude = extractDouble(json, "longitude");

            return new double[] {latitude, longitude};

        } catch (Exception e) {
            System.out.println("Could not find location automatically.");
            return new double[] {39.9334, 32.8597};
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
}