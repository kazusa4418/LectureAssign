public class DayNotFoundException extends RuntimeException {
    public DayNotFoundException() {
        super("next day not found.");
    }
}
