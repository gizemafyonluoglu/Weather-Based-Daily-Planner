public class Weather {
    private double temperature;
    private String weatherCondition;
    private double windSpeed;
    private double humidity;

    public Weather(double temperature, String weatherCondition,
                   double windSpeed, double humidity) {
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }
    public String getWeatherCondition() {
        return weatherCondition;
    }
    public double getWindSpeed() {
        return windSpeed;
    }
    public double getHumidity() {
        return humidity;
    }

    public boolean isRainy() {
        return weatherCondition.equalsIgnoreCase("rain")
            || weatherCondition.equalsIgnoreCase("rainy");
    }

    public boolean isSnowy() {
        return weatherCondition.equalsIgnoreCase("snow")
            || weatherCondition.equalsIgnoreCase("snowy");
    }

    public boolean isStormy() {
        return weatherCondition.equalsIgnoreCase("storm")
            || weatherCondition.equalsIgnoreCase("stormy");
    }

    public boolean isGoodForOutdoor() {
        return temperature >= 19 && temperature <= 25
            && humidity >= 30 && humidity <= 50
            && windSpeed <= 10
            && !isRainy()
            && !isSnowy()
            && !isStormy();
    }

    public String getOutdoorReason() {
        if (isGoodForOutdoor()) {
            String message = "The weather is good for an outdoor activity.";
            return message;
        }
        if (temperature > 25) {
            String message = "It is too hot outside. Suggesting an indoor activity...";
            return message;
        }
        if (temperature < 19) {
            String message = "It is too cold outside. Suggesting an indoor activity...";
            return message;
        }
        if (windSpeed > 10) {
            String message = "It is too windy outside. Suggesting an indoor activity...";
            return message;
        }
        if (humidity > 50) {
            String message = "It is too humid outside. Suggesting an indoor activity...";
            return message;
        }
        if (humidity < 30) {
            String message = "The air is too dry outside. Suggesting an indoor activity...";
            return message;
        }
        if (isRainy()) {
            String message = "It is rainy outside. Suggesting an indoor activity...";
            return message;
        }
        if (isSnowy()) {
            String message = "It is snowy outside. Suggesting an indoor activity...";
            return message;
        }
        if (isStormy()) {
            String message = "It is stormy outside. Suggesting an indoor activity...";
            return message;
        }
        return "Outdoor activity is not recommended.";
    }

    public String toString() {
        return "Weather: " + weatherCondition
             + "\nTemperature: " + temperature + "°C"
             + "\nWind speed: " + windSpeed + " km/h"
             + "\nHumidity: " + humidity + "%";
    }
}