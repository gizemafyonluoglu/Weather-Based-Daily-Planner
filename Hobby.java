import java.time.LocalDate;

public class Hobby {
    private String hobbyName;
    private int preferredMinutes;
    private int targetFrequencyPerWeek;
    private LocalDate lastDoneDate;

    public Hobby(String hobbyName, int preferredMinutes, int targetFrequencyPerWeek) {
        this.hobbyName = hobbyName;
        this.preferredMinutes = preferredMinutes;
        this.targetFrequencyPerWeek = targetFrequencyPerWeek;
        this.lastDoneDate = LocalDate.now();
    }

    public String getHobbyName() {
        return hobbyName;
    }
    public void setHobbyName(String hobbyName) {
        this.hobbyName = hobbyName;
    }
    
    public int getPreferredMinutes() {
        return preferredMinutes;
    }
    public int getTargetFrequencyPerWeek() {
        return targetFrequencyPerWeek;
    }

    public LocalDate getLastDoneDate() {
        return lastDoneDate;
    }
    public void setLastDoneDate(LocalDate lastDoneDate) {
        this.lastDoneDate = lastDoneDate;
    }
    @Override
    public String toString() {
        return "Hobby: " + hobbyName + "\nPreferred Minutes: " + preferredMinutes + "\nTarget Frequency Per Week: " + targetFrequencyPerWeek + "\nLast Done Date: " + lastDoneDate;
    }

}
