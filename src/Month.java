import java.util.ArrayList;
import java.util.List;

public class Month {
    /* 所属してるカレンダー */
    private Calendar calendar;

    private int month;
    private List<Day> days;

    public Month(int year, int month, Calendar calendar) {
        this.calendar = calendar;
        this.month = month;

        java.util.Calendar c = java.util.Calendar.getInstance();
        c.clear();

        //noinspection MagicConstant
        c.set(year, month - 1, 1);
        DayOfWeek dow = DayOfWeek.get(c.get(java.util.Calendar.DAY_OF_WEEK));

        c.add(java.util.Calendar.MONTH, 1);
        c.add(java.util.Calendar.DATE, -1);

        int lastDate = c.get(java.util.Calendar.DATE);

        List<Day> list = new ArrayList<>();
        for (int i = 1; i <= lastDate; i++ ) {
            boolean isHoliday = HolidayChecker.check(month, i, dow);
            list.add(new Day(i, dow, this, isHoliday));

            dow = dow.next();
        }
        this.days = list;
    }

    public Day getDay(int day) {
        if (day > days.size() || day < 1) {
            throw new IllegalArgumentException("This month is until the " + days.size() + "th.");
        }

        return days.get(day - 1);
    }

        public Day[] getDayList() {
        return days.toArray(new Day[days.size()]);
    }

    public Day[] getDays(DayOfWeek dayOfWeek) {
        List<Day> list = new ArrayList<>();
        for (Day day : days) {
            if (day.is(dayOfWeek)) {
                list.add(day);
            }
        }
        return list.toArray(new Day[list.size()]);
    }

    public int count(Lecture lecture) {
//        int count = 0;
//
//        for (Day day : days) {
//            Lecture scheduled = day.getLecture();
//
//            if (lecture.equals(scheduled)) {
//                count++;
//            }
//        }
//        // カウントされた日数が研修日数の倍数でないとおかしい
//        assert count % lecture.getPeriod() == 0;
//
//        // カウントされた日数をその研修の開催日数で割り、開催回数を返す
//        return count / lecture.getPeriod();
        return 0;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public String toString() {
        return month + "月";
    }
}
