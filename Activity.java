import java.time.LocalDate;
import java.time.LocalTime;

public class Activity {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String activityName;
    private boolean completed;
    private String activityType;

    public Activity(LocalDate date, LocalTime startTime, LocalTime endTime, String activityName) {
        this(date, startTime, endTime, activityName, "General");
    }

    public Activity(LocalDate date, LocalTime startTime, LocalTime endTime, String activityName, String activityType) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityName = activityName;
        this.activityType = activityType;
        this.completed = false;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public long getDurationMinutes() {
        return java.time.Duration.between(startTime, endTime).toMinutes();
    }

    public boolean conflictsWith(LocalDate activityDate, LocalTime activityStart, LocalTime activityEnd) {
        if (!this.date.equals(activityDate)) {
            return false;
        }
        return activityStart.isBefore(this.endTime) && activityEnd.isAfter(this.startTime);
    }

    public boolean conflictsWith(Activity otherActivity) {
        return conflictsWith(otherActivity.getDate(), otherActivity.getStartTime(), otherActivity.getEndTime());
    }

    @Override
    public String toString() {
        String status;
        if (completed) {
            status = "Completed";
        } else {
            status = "Not completed";
        }

        return activityName
            + "\nType: " + activityType
            + "\nDate: " + date
            + "\nTime: " + startTime + " - " + endTime
            + "\nStatus: " + status;
    }
}
