import java.util.ArrayList;
import java.util.Random;

public class ActivityLibrary {
    private static String[] indoorActivities = {
        "Reading",
        "Drawing",
        "Piano",
        "Violin",
        "Cooking",
        "Yoga",
        "Journaling",
        "Coding",
        "Language study",
        "Sketching"
    };

    private static String[] outdoorActivities = {
        "Walking",
        "Running",
        "Cycling",
        "Photography walk",
        "Basketball",
        "Tennis",
        "Picnic"
    };

    public static String suggestIndoorHobby(ArrayList<Hobby> hobbies) {
        return suggestNewHobby(indoorActivities, hobbies);
    }

    public static String suggestOutdoorHobby(ArrayList<Hobby> hobbies) {
        return suggestNewHobby(outdoorActivities, hobbies);
    }

    private static String suggestNewHobby(String[] library, ArrayList<Hobby> hobbies) {
        Random random = new Random();

        for (int attempts = 0; attempts < library.length * 2; attempts++) {
            String suggestion = library[random.nextInt(library.length)];

            if (!userAlreadyHasHobby(suggestion, hobbies)) {
                return suggestion;
            }
        }

        return "No new hobby found in library.";
    }

    private static boolean userAlreadyHasHobby(String hobbyName, ArrayList<Hobby> hobbies) {
        for (int i = 0; i < hobbies.size(); i++) {
            if (hobbies.get(i).getHobbyName().equalsIgnoreCase(hobbyName)) {
                return true;
            }
        }

        return false;
    }
}