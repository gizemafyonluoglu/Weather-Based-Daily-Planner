import java.util.ArrayList;
import java.util.List;

public class User{
    private String name;
    private int age;
    private List<Hobby> hobbies;
    private List<Activity> preferredActivities;

    public User(String name, int age) {
        this.name = name;
        
        this.hobbies = new ArrayList<>();
        this.preferredActivities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public List<Activity> getPreferredActivities() {
        return preferredActivities;
    }

    public void addHobby(Hobby hobby) {
        this.hobbies.add(hobby);
    }

    public void addPreferredActivity(Activity activity) {
        this.preferredActivities.add(activity);
    }

    @Override
    public String toString() {
        return "User: " + name + "\nHobbies: " + getHobbies() + "\nPreferred Activities: " + getPreferredActivities();
    }
    public boolean conflictsWithClass(Activity activity, ClassSession[] classsession) {
        for (ClassSession classSession : classsession) {
            if (classSession.conflictsWith(
                    activity.getDate(),
                    activity.getStartTime(),
                    activity.getEndTime())) {
                return true;
            }
        }
        return false;
    }
}