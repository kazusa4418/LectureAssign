public class Day {
    /* 所属してる月 */
    private Month month;

    private DayOfWeek dayOfWeek;

    private boolean isHoliday;

    private int day;

    public Day(int day, DayOfWeek dayOfWeek, Month month) {
        this(day, dayOfWeek, month, false);
    }

    public Day(int day, DayOfWeek dayOfWeek, Month month, boolean isHoliday) {
        this.day = day;
        this.dayOfWeek = dayOfWeek;

        this.month = month;
        this.isHoliday = isHoliday;
    }

    public boolean isHoliday() {
        return isHoliday || dayOfWeek.isDayOff();
    }

    public String toString() {
        return day + " " + (isHoliday || dayOfWeek.isDayOff() ? "休日" : "平日") + " " + dayOfWeek;
    }

    public Month getMonth() {
        return month;
    }

    public boolean is(DayOfWeek dayOfWeek) {
        return this.dayOfWeek == dayOfWeek;
    }

    public Day next() {
        return month.getCalendar().nextDay(this);
    }

    public Day before() {
        return month.getCalendar().beforeDay(this);
    }
}
