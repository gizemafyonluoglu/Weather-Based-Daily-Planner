public class Weather {
    private double temperature;
    private String weatherCondition;
    private double windSpeed;
    private double humidity;

    public Weather(double temperature, String weatherCondition, double windSpeed, double humidity) {
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
            return "The weather is good for an outdoor activity.";
        }
        if (temperature > 25) {
            return "It is too hot outside. Suggesting an indoor activity...";
        }
        if (temperature < 19) {
            return "It is too cold outside. Suggesting an indoor activity...";
        }
        if (windSpeed > 10) {
            return "It is too windy outside. Suggesting an indoor activity...";
        }
        if (humidity > 50) {
            return "It is too humid outside. Suggesting an indoor activity...";
        }
        if (humidity < 30) {
            return "The air is too dry outside. Suggesting an indoor activity...";
        }
        if (isRainy()) {
            return "It is rainy outside. Suggesting an indoor activity...";
        }
        if (isSnowy()) {
            return "It is snowy outside. Suggesting an indoor activity...";
        }
        if (isStormy()) {
            return "It is stormy outside. Suggesting an indoor activity...";
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