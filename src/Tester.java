import java.util.stream.IntStream;

import java.util.List;

import java.time.MonthDay;

import java.time.Year;

import java.time.DayOfWeek;

import java.time.LocalDate;



import static java.util.Arrays.asList;

import static java.time.Month.APRIL;

import static java.time.DayOfWeek.SATURDAY;

import static java.time.DayOfWeek.SUNDAY;



class Tester {

    public static void main(String... args) {

        // 指定日(エイプリルフール)

        MonthDay aprilFool = MonthDay.of(APRIL, 1);

        // 今年

        int thisYear = 2015;

        // 土曜日と日曜日は休日の設定

        List<DayOfWeek> dayOff = asList(SATURDAY, SUNDAY);



        // 今後 30 年間について調べる

        IntStream.range(0, 30)

                .mapToObj(i -> Year.of(thisYear + i))



                // 指定日が休日かどうか

                .map(year -> year.atMonthDay(aprilFool))

                .filter(day -> dayOff.contains(day.getDayOfWeek()))



                .map(LocalDate::getYear)

                .forEach(System.out::println);

    }

}
