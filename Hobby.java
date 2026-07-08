import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Hobby {
    private String hobbyName;
    private int preferredMinutes;
    private int targetFrequencyPerWeek;
    private LocalDate lastDoneDate;
    private boolean outdoor;

    public Hobby(String hobbyName, int preferredMinutes, int targetFrequencyPerWeek) {
        this(hobbyName, preferredMinutes, targetFrequencyPerWeek, false);
    }

    public Hobby(String hobbyName, int preferredMinutes, int targetFrequencyPerWeek, boolean outdoor) {
        this.hobbyName = hobbyName;
        this.preferredMinutes = preferredMinutes;
        this.targetFrequencyPerWeek = targetFrequencyPerWeek;
        this.lastDoneDate = null;
        this.outdoor = outdoor;
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

    public boolean isOutdoor() {
        return outdoor;
    }

    public void setOutdoor(boolean outdoor) {
        this.outdoor = outdoor;
    }

    public long daysSinceLastDone(LocalDate today) {
        if (lastDoneDate == null) {
            return Long.MAX_VALUE;
        }
        return ChronoUnit.DAYS.between(lastDoneDate, today);
    }

    public void markDone(LocalDate date) {
        this.lastDoneDate = date;
    }

    @Override
    public String toString() {
        String place;
        if (outdoor) {
            place = "Outdoor";
        } else {
            place = "Indoor";
        }

        String lastDoneText;
        if (lastDoneDate == null) {
            lastDoneText = "Never done before";
        } else {
            lastDoneText = lastDoneDate.toString();
        }

        return "Hobby: " + hobbyName
            + "\nPreferred Minutes: " + preferredMinutes
            + "\nTarget Frequency Per Week: " + targetFrequencyPerWeek
            + "\nPlace: " + place
            + "\nLast Done Date: " + lastDoneText;
    }
}
