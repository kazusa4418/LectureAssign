public class Calendar {

    /* =============== SINGLETON =============== */
    private static Calendar instance = new Calendar();

    public static Calendar getInstance() {
        return instance;
    }
    /* ========================================= */

    private int year;
    private Month[] months;

    private Calendar() {
        year = Configuration.getYear();

        Month[] tmp = new Month[12];
        for (int i = 1; i <= 12; i++ ) {
            tmp[i - 1] = new Month(year, i, this);
        }
        months = tmp;
    }

    public int getYear() {
        return year;
    }

    public Day getDay(String date) {
        String[] dates = date.split("/");
        if (dates.length != 2) {
            throw new IllegalArgumentException("argument type is illegal.");
        }

        int month = Integer.parseInt(dates[0]);
        int day = Integer.parseInt(dates[1]);

        return months[month - 1].getDay(day);
    }

    Day nextDay(Day day) {
        for (int i = 0; i < months.length; i++ ) {
            /* 一か月の日の配列を取得してくる */
            Day[] days = months[i].getDayList();

            /* 取得した配列の中に引数の日が存在するか調べる */
            for (int j = 0; j < days.length; j++ ) {
                /* 存在した場合、その次の日のインスタンスを取得し、返す */
                if (days[j] == day) {
                    /* 次の日が同じ月ならば */
                    if (++j < days.length) {
                        return days[j];
                    }
                    /* 次の日が次の月ならば */
                    else {
                        return months[i + 1].getDay(1);
                    }
                }
            }
        }
        throw new IllegalArgumentException("argument does not exist.");
    }

    Day nextDay(Day day, int num) {
        for (int i = 0; i < num; i++ ) {
            day = nextDay(day);
        }
        return day;
    }

    Day beforeDay(Day day) {
        checkInclude(day);

        Month month = day.getMonth();

        Day[] days = month.getDayList();
        for (int i = 0; i < days.length; i++ ) {
            if (day != days[i]) {
                continue;
            }
            if (--i < 0) {
                return days[i];
            }
            else {
                Day[] dayList = nextMonth(month).getDayList();
                return dayList[dayList.length - 1];
            }
        }
        throw new AssertionError();
    }

    Day beforeDay(Day day, int num) {
        for (int i = 0; i < num; i++ ) {
            day = beforeDay(day);
        }
        return day;
    }

    Month nextMonth(Month month) {
        checkInclude(month);

        for (int i = 0; i < months.length; i++ ) {
            if (month != months[i]) {
                continue;
            }

            if (i + 1 < months.length) {
                return months[i + 1];
            }
            else {
                return months[0];
            }
        }
        throw new AssertionError("An error occurred with Calendar#nextMonth.\n" +
                                                "It is an error that should not occur.");
    }

    Month beforeMonth(Month month) {
        checkInclude(month);

        for (int i = 0; i < months.length; i++ ) {
            if (month != months[i]) {
                continue;
            }

            if (i - 1 >= 0) {
                return months[i - 1];
            }
            else {
                return months[months.length - 1];
            }
        }
        throw new AssertionError("An error occurred with Calendar#beforeMonth.\n" +
                                                "It is an error that should not occur.");
    }

    public Month[] getMonthList() {
        return months;
    }

    public void checkInclude(Day day) {
        Month month = day.getMonth();
        if (this != month.getCalendar()) {
            throw new IllegalArgumentException("this Day type object is not included in this calendar.");
        }
    }

    public void checkInclude(Month month) {
        if (this != month.getCalendar()) {
            throw new IllegalArgumentException("this Month type object is not included in this calendar.");
        }
    }

    public int count(Lecture lecture) {
        int count = 0;

        for (Month month : months) {
            count += month.count(lecture);
        }
        return count;
    }
}
