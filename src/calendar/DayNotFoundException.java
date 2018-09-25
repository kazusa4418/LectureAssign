package calendar;

public class DayNotFoundException extends Exception {
    public DayNotFoundException() {
        super("next day not found.");
    }
}
