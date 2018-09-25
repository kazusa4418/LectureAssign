package calendar;

import configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Calendar {

    /* =============== SINGLETON =============== */
    private static Calendar instance = new Calendar();

    public static Calendar getInstance() {
        return instance;
    }
    /* ========================================= */

    private Month[] months;

    private Calendar() {
        int year = Configuration.getYear();

        List<Month> tmp = new ArrayList<>();

        // TODO: 月によって与える年を変える分岐をどうにかできないものか
        for (int num : new int[] { 4, 5, 6, 7, 8, 9, 10, 11, 12, 1, 2, 3 }) {
            if (num >= 4) {
                tmp.add(new Month(year, num, this));
            }
            else {
                tmp.add(new Month(year + 1, num, this));
            }
        }
        // TODO: 気に入らない
        months = tmp.toArray(new Month[0]);

        for (Month month : months) {
            System.out.println(month);
        }
    }

//    public int getYear() {
//        return year;
//    }
//
//    public Day getDay(String date) {
//        String[] dates = date.split("/");
//        if (dates.length != 2) {
//            throw new IllegalArgumentException("argument type is illegal.");
//        }
//
//        int month = Integer.parseInt(dates[0]);
//        int day = Integer.parseInt(dates[1]);
//
//        return months[month - 1].getDay(day);
//    }

    private Day nextDay(Day day) throws DayNotFoundException {
        checkInclude(day);

        Day[] dayList = day.getMonth().getDayList();

        int dayInt = day.getInt() - 1;

        if (++dayInt < dayList.length) {
            return dayList[dayInt];
        }
        else {
            try {
                return nextMonth(day.getMonth()).getDay(1);
            }
            catch (MonthNotFoundException err) {
                throw new DayNotFoundException();
            }
        }

        // DayクラスやMonthクラスを工夫したら上のような簡潔なプログラムになった。
        // 今後のために修正前の冗長なプログラムも残しておく。

//        for (int i = 0; i < months.length; i++ ) {
//            /* 一か月の日の配列を取得してくる */
//            Day[] days = months[i].getDayList();
//
//            /* 取得した配列の中に引数の日が存在するか調べる */
//            for (int j = 0; j < days.length; j++ ) {
//                /* 存在した場合、その次の日のインスタンスを取得し、返す */
//                if (days[j] == day) {
//                    /* 次の日が同じ月ならば */
//                    if (++j < days.length) {
//                        return days[j];
//                    }
//                    /* 次の日が次の月ならば */
//                    else {
//                        // 来年度だった場合例外が発生する
//                        try {
//                            return months[i + 1].getDay(1);
//                        }
//                        catch (ArrayIndexOutOfBoundsException err) {
//                            throw new DayNotFoundException();
//                        }
//                    }
//                }
//            }
//        }
//        throw new IllegalArgumentException("argument does not exist.");
    }

    Day nextDay(Day day, int num) throws DayNotFoundException {
        for (int i = 0; i < num; i++ ) {
            day = nextDay(day);
        }
        return day;
    }

    private Day beforeDay(Day day) throws DayNotFoundException {
        checkInclude(day);

        Day[] dayList = day.getMonth().getDayList();
        int dayInt = day.getInt() - 1;

        if (--dayInt >= 0) {
            return dayList[dayInt];
        }
        else {
            try {
                return beforeMonth(day.getMonth()).getLastDay();
            }
            catch (MonthNotFoundException err) {
                throw new DayNotFoundException();
            }
        }
    }

    Day beforeDay(Day day, int num) throws DayNotFoundException {
        for (int i = 0; i < num; i++ ) {
            day = beforeDay(day);
        }
        return day;
    }

    private Month nextMonth(Month month) throws MonthNotFoundException {
        checkInclude(month);

        for (int i = 0; i < months.length; i++ ) {
            if (month != months[i]) {
                continue;
            }

            if (i + 1 < months.length) {
                return months[i + 1];
            }
            else {
                throw new MonthNotFoundException();
            }
        }
        // これが踏まれるはずないんだよ。
        throw new AssertionError();
    }

    private Month beforeMonth(Month month) throws MonthNotFoundException {
        checkInclude(month);

        for (int i = 0; i < months.length; i++ ) {
            if (month != months[i]) {
                continue;
            }

            if (--i >= 0) {
                return months[i];
            }
            else {
                throw new MonthNotFoundException();
            }
        }
        // これが踏まれるはずないんだよ。
        throw new AssertionError();
    }

    public Month[] getMonthList() {
        return months;
    }

    private void checkInclude(Day day) {
        Month month = day.getMonth();
        if (this != month.getCalendar()) {
            throw new IllegalArgumentException("this calendar.Day type object is not included in this calendar.");
        }
    }

    private void checkInclude(Month month) {
        if (this != month.getCalendar()) {
            throw new IllegalArgumentException("this calendar.Month type object is not included in this calendar.");
        }
    }
}
