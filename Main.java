import java.time.*;
import java.util.*;

public class Main {
    private static ArrayList<Hobby> hobbies = new ArrayList<>();
    private static ArrayList<ClassSession> classes = new ArrayList<>();
    private static ArrayList<Activity> activities = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Weather-Based Daily Planner!");

        addHobbies();
        addClasses();

        boolean running = true;
        while (running) {
            System.out.println("\n1) Show calendar");
            System.out.println("2) Add activity");
            System.out.println("3) Suggest hobby");
            System.out.println("4) Mark activity completed");
            System.out.println("5) Exit");

            int choice = readInt("Choose: ");

            if (choice == 1) {
                showCalendar();
            } else if (choice == 2) {
                addActivity();
            } else if (choice == 3) {
                suggestHobby();
            } else if (choice == 4) {
                markCompleted();
            } else if (choice == 5) {
                running = false;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void addHobbies() {
        int count = readInt("How many hobbies? ");

        for (int i = 0; i < count; i++) {
            String name = readString("Hobby name: ");
            int minutes = readInt("Preferred minutes: ");
            int times = readInt("Target times per week: ");

            String outdoorAnswer = readString("Outdoor hobby? yes/no: ");
            boolean outdoor = outdoorAnswer.equalsIgnoreCase("yes");

            Hobby hobby = new Hobby(name, minutes, times, outdoor);

            String date = readString("Last done date yyyy-mm-dd or never: ");

            if (!date.equalsIgnoreCase("never")) {
                hobby.setLastDoneDate(LocalDate.parse(date));
            }

            hobbies.add(hobby);
        }
    }

    private static void addClasses() {
        int count = readInt("How many class sessions? ");

        for (int i = 0; i < count; i++) {
            String name = readString("Course name: ");

            String daysText = readString("Days (example: MONDAY,WEDNESDAY,FRIDAY): ");
            String[] dayParts = daysText.split("[, ]+");

            for (int j = 0; j < dayParts.length; j++) {
                String oneDayText = dayParts[j].trim().toUpperCase();
                if (oneDayText.length() == 0) {
                    continue;
                }

                DayOfWeek day = DayOfWeek.valueOf(oneDayText);
                LocalTime start = LocalTime.parse(readString("Start time for " + oneDayText + " HH:mm: "));
                LocalTime end = LocalTime.parse(readString("End time for " + oneDayText + " HH:mm: "));
                classes.add(new ClassSession(name, day, start, end));
            }
        }
    }

    private static void addActivity() {
        String name = readString("Activity name: ");

        LocalDate date = LocalDate.parse(readString("Date yyyy-mm-dd: "));
        LocalTime start = LocalTime.parse(readString("Start HH:mm: "));
        LocalTime end = LocalTime.parse(readString("End HH:mm: "));

        if (hasConflict(date, start, end)) {
            System.out.println("Conflict found. Activity not added.");
            return;
        }

        activities.add(new Activity(date, start, end, name));
        System.out.println("Activity added.");
    }

    private static void suggestHobby() {
        LocalDate date = LocalDate.parse(readString("Date yyyy-mm-dd: "));
        LocalTime start = LocalTime.parse(readString("Free start HH:mm: "));
        LocalTime end = LocalTime.parse(readString("Free end HH:mm: "));

        if (hasConflict(date, start, end)) {
            System.out.println("This time is not free.");
            return;
        }

        double[] location = LocationService.getLocationFromInternet();
        double latitude = location[0];
        double longitude = location[1];

        Weather weather = WeatherService.getWeatherFromInternet(latitude, longitude);
        System.out.println(weather);

        long freeMinutes = Duration.between(start, end).toMinutes();
        Hobby best = null;
        long longest = -1;

        for (int i = 0; i < hobbies.size(); i++) {
            Hobby hobby = hobbies.get(i);

            if (hobby.isOutdoor() && !weather.isGoodForOutdoor()) {
                continue;
            }

            if (hobby.getPreferredMinutes() <= freeMinutes) {
                long days = hobby.daysSinceLastDone(date);

                if (days > longest) {
                    longest = days;
                    best = hobby;
                }
            }
        }

        if (best == null) {
            System.out.println("No suitable hobby found.");
            return;
        }

        LocalTime suggestedEnd = start.plusMinutes(best.getPreferredMinutes());

        System.out.println("\nSuggestion:");
        System.out.println("You haven't done " + best.getHobbyName() + " for " + longest + " days.");
        System.out.println(weather.getOutdoorReason());
        System.out.println("I suggest " + best.getHobbyName() + " from " + start + " to " + suggestedEnd);

        String answer = readString("Add it? yes/no: ");
        if (answer.equalsIgnoreCase("yes")) {
            activities.add(new Activity(date, start, suggestedEnd, best.getHobbyName()));
            System.out.println("Added.");
        }
    }

    private static void markCompleted() {
        for (int i = 0; i < activities.size(); i++) {
            System.out.println((i + 1) + ". " + activities.get(i).getActivityName());
        }

        int index = readInt("Which one completed? ") - 1;

        if (index >= 0 && index < activities.size()) {
            Activity activity = activities.get(index);
            activity.setCompleted(true);

            for (int i = 0; i < hobbies.size(); i++) {
                Hobby hobby = hobbies.get(i);

                if (hobby.getHobbyName().equalsIgnoreCase(activity.getActivityName())) {
                    hobby.markDone(activity.getDate());
                }
            }

            System.out.println("Marked completed.");
        } else {
            System.out.println("Invalid activity number.");
        }
    }

    private static boolean hasConflict(LocalDate date, LocalTime start, LocalTime end) {
        for (int i = 0; i < classes.size(); i++) {
            ClassSession classSession = classes.get(i);

            if (classSession.conflictsWith(date, start, end)) {
                System.out.println("Conflict with class: " + classSession.getCourseName());
                return true;
            }
        }

        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);

            if (activity.conflictsWith(date, start, end)) {
                System.out.println("Conflict with activity: " + activity.getActivityName());
                return true;
            }
        }

        return false;
    }

    private static void showCalendar() {
        System.out.println("\nClasses:");
        for (int i = 0; i < classes.size(); i++) {
            System.out.println(classes.get(i));
            System.out.println();
        }

        System.out.println("Activities:");
        for (int i = 0; i < activities.size(); i++) {
            System.out.println(activities.get(i));
            System.out.println();
        }
    }

    private static int readInt(String message) {
        System.out.print(message);
        return Integer.parseInt(input.nextLine());
    }

    private static double readDouble(String message) {
        System.out.print(message);
        return Double.parseDouble(input.nextLine());
    }

    private static String readString(String message) {
        System.out.print(message);
        return input.nextLine();
    }
}