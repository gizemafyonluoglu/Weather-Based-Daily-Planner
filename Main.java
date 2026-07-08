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
            }
        }
    }

    private static void addHobbies() {
        int count = readInt("How many hobbies? ");

        for (int i = 0; i < count; i++) {
            System.out.print("Hobby name: ");
            String name = input.nextLine();

            int minutes = readInt("Preferred minutes: ");
            int times = readInt("Target times per week: ");

            System.out.print("Outdoor hobby? yes/no: ");
            boolean outdoor = input.nextLine().equalsIgnoreCase("yes");

            Hobby hobby = new Hobby(name, minutes, times, outdoor);

            System.out.print("Last done date yyyy-mm-dd or never: ");
            String date = input.nextLine();

            if (!date.equalsIgnoreCase("never")) {
                hobby.setLastDoneDate(LocalDate.parse(date));
            }

            hobbies.add(hobby);
        }
    }

    private static void addClasses() {
        int count = readInt("How many class sessions? ");

        for (int i = 0; i < count; i++) {
            System.out.print("Course name: ");
            String name = input.nextLine();

            String daysText = readString("Days (example: MONDAY,WEDNESDAY,FRIDAY): ");
            LocalTime start = LocalTime.parse(readString("Start time HH:mm: "));
            LocalTime end = LocalTime.parse(readString("End time HH:mm: "));

            String[] dayParts = daysText.split(",");

            for (int j = 0; j < dayParts.length; j++) {
                String oneDayText = dayParts[j].trim().toUpperCase();
                DayOfWeek day = DayOfWeek.valueOf(oneDayText);
                classes.add(new ClassSession(name, day, start, end));
            }
        }
    }

    private static void addActivity() {
        System.out.print("Activity name: ");
        String name = input.nextLine();

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

        double temp = readDouble("Temperature: ");
        String condition = readString("Weather condition: ");
        double wind = readDouble("Wind speed: ");
        double humidity = readDouble("Humidity: ");

        Weather weather = new Weather(temp, condition, wind, humidity);

        if (hasConflict(date, start, end)) {
            System.out.println("This time is not free.");
            return;
        }

        long freeMinutes = Duration.between(start, end).toMinutes();
        Hobby best = null;
        long longest = -1;

        for (Hobby hobby : hobbies) {
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

        System.out.print("Add it? yes/no: ");
        if (input.nextLine().equalsIgnoreCase("yes")) {
            activities.add(new Activity(date, start, suggestedEnd, best.getHobbyName(), "Hobby"));
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

            for (Hobby hobby : hobbies) {
                if (hobby.getHobbyName().equalsIgnoreCase(activity.getActivityName())) {
                    hobby.markDone(activity.getDate());
                }
            }

            System.out.println("Marked completed.");
        }
    }

    private static boolean hasConflict(LocalDate date, LocalTime start, LocalTime end) {
        for (ClassSession c : classes) {
            if (c.conflictsWith(date, start, end)) {
                System.out.println("Conflict with class: " + c.getCourseName());
                return true;
            }
        }

        for (Activity a : activities) {
            if (a.conflictsWith(date, start, end)) {
                System.out.println("Conflict with activity: " + a.getActivityName());
                return true;
            }
        }

        return false;
    }

    private static void showCalendar() {
        System.out.println("\nClasses:");
        for (ClassSession c : classes) {
            System.out.println(c);
            System.out.println();
        }

        System.out.println("Activities:");
        for (Activity a : activities) {
            System.out.println(a);
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