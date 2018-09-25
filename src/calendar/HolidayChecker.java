package calendar;

import calendar.DayOfWeek;

public class HolidayChecker {
    public static boolean check(int month, int day, DayOfWeek dayOfWeek) {
        // 元日
        if (month == 1 && day == 1) {
            return true;
        }
        // 成人の日
        if (month == 1 && (day - 1) / 7 == 1 && dayOfWeek == DayOfWeek.MONDAY) {
            return true;
        }
        // 建国記念の日
        if (month == 2 && day == 11) {
            return true;
        }
        // 春分の日
        if (month == 3 && day == 21) {
            return true;
        }
        // 昭和の日
        if (month == 4 && day == 29) {
            return true;
        }
        // 憲法記念日
        if (month == 5 && day == 3) {
            return true;
        }
        // みどりの日
        if (month == 5 && day == 4) {
            return true;
        }
        // こどもの日
        if (month == 5 && day == 5) {
            return true;
        }
        // 海の日
        if (month == 7 && (day - 1) / 7 == 2 && dayOfWeek == DayOfWeek.MONDAY) {
            return true;
        }
        // 山の日
        if (month == 8 && day == 11) {
            return true;
        }
        // 敬老の日
        if (month == 9 && (day - 1) / 7 == 2 && dayOfWeek == DayOfWeek.MONDAY) {
            return true;
        }
        // 秋分の日 9月22日か9月23日か、どちらかが該当するらしい
        if (month == 9 && day == 22) {
            return true;
        }
        // 体育の日
        if (month == 10 && (day - 1) / 7 == 1 && dayOfWeek == DayOfWeek.MONDAY) {
            return true;
        }
        // 文化の日
        if (month == 11 && day == 3) {
            return true;
        }
        // 勤労感謝の日
        if (month == 11 && day == 23) {
            return true;
        }
        // 天皇誕生日
        return month == 12 && day == 23;
    }
}
