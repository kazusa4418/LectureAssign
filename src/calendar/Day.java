package calendar;

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

    public int getInt() {
        return day;
    }

    public boolean isHoliday() {
        return isHoliday || dayOfWeek.isDayOff();
    }

    public String toString() {
        return month +", "+ day + "日 " + (isHoliday || dayOfWeek.isDayOff() ? "休日" : "平日") + " " + dayOfWeek;
    }

    public Month getMonth() {
        return month;
    }

    public boolean is(DayOfWeek dayOfWeek) {
        return this.dayOfWeek == dayOfWeek;
    }

    public Day next() throws DayNotFoundException {
        return month.getCalendar().nextDay(this, 1);
    }

    public Day next(int num) throws DayNotFoundException {
        return month.getCalendar().nextDay(this, num);
    }

    public Day before() throws DayNotFoundException {
        return month.getCalendar().beforeDay(this, 1);
    }

    public Day before(int num) throws DayNotFoundException {
        return month.getCalendar().beforeDay(this, num);
    }
}
