package calendar;

public class MonthNotFoundException extends Exception {
    public MonthNotFoundException() {
        super("month not found.");
    }
}
