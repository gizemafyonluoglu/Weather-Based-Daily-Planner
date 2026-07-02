import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public class ClassSession {
    private String courseName;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    public ClassSession(String courseName, DayOfWeek day, LocalTime startTime, LocalTime endTime){
        this.courseName = courseName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public String getCourseName() {
        return this.courseName;
    }
    public DayOfWeek getDay() {
        return this.day;
    }
    public LocalTime getStartTime() {
        return this.startTime;
    }
    public LocalTime getEndTime() {
        return this.endTime;
    }
    @Override
    public String toString() {
        return courseName +
            "\nDay: " + day +
            "\nTime: " + startTime + " - " + endTime;
    }
    public long getDurationMinutes() {
        return Duration.between(startTime, endTime).toMinutes();
    }
    public boolean overlaps(LocalTime start, LocalTime end) {
        return start.isBefore(endTime) && end.isAfter(startTime);
    }

}
