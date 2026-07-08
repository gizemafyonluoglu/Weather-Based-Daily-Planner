# Balance

## About
Balance is a Java terminal-based planner that helps students balance their academic schedule with their hobbies. The application automatically considers the user's class schedule, available free time, hobby preferences, and real-time weather conditions to recommend activities. It also encourages users to maintain a balanced lifestyle by suggesting hobbies they have not practiced recently and recommending new hobbies when appropriate.

## Features

### Schedule Management
- Add class sessions on one or multiple days of the week.
- Specify different class times for each day.
- Add personal activities to the calendar.
- Detect scheduling conflicts between classes and activities.
- Display all classes and activities in a calendar-like list.

### Hobby Management
- Add indoor and outdoor hobbies.
- Set preferred duration for each hobby.
- Record how often a hobby should be completed each week.
- Store the last completion date of each hobby.
- Mark hobbies as completed to keep recommendations up to date.

### Smart Hobby Recommendation
- Finds hobbies that have not been practiced for the longest time.
- Considers:
  - Available free time
  - Indoor or outdoor preference
  - Current weather conditions
- If no suitable hobby exists:
  - Suggests starting a new indoor hobby when the weather is unsuitable for outdoor activities.
  - Suggests starting a new outdoor hobby when the weather is suitable but the user has no outdoor hobbies.
- Generates random hobby suggestions from an activity library without recommending hobbies the user already has.

### Weather Integration
- Automatically estimates the user's location using their public IP address.
- Retrieves live weather information from the Open-Meteo API.
- Uses:
  - Temperature
  - Humidity
  - Wind speed
  - Weather condition
to determine whether outdoor activities are appropriate.

## Technologies

- Java
- Java HttpClient
- Object-Oriented Programming (OOP)
- Open-Meteo Weather API
- IP Geolocation API

## Project Structure

| Class                     | Responsibility                                                        |
|---------------------------|-----------------------------------------------------------------------|
| `Main.java`               | User interface and application flow                                   |
| `Hobby.java`              | Stores hobby information                                              |
| `Activity.java`           | Stores user activities                                                |
| `ClassSession.java`       | Stores class schedule information                                     |
| `Weather.java`            | Represents weather data                                               |
| `WeatherService.java`     | Retrieves weather data from the Open-Meteo API                        |
| `LocationService.java`    | Retrieves the user's approximate location                             |
| `ActivityLibrary.java`    | Stores predefined indoor and outdoor hobbies and suggests new hobbies |

## Future Improvements

- Save user information to files or a database.
- Weekly and monthly calendar view.
- GUI version using JavaFX.
- Google Calendar synchronization.
- Notifications and reminders.
- Smarter recommendation algorithm based on user habits and preferences.
- Activity statistics and hobby progress tracking.

## Author
**Gizem Afyonluoglu**