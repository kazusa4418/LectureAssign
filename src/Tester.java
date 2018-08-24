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
        if (args.length > 0) {
            Configuration.setYear(Integer.parseInt(args[0]));
        }

        LectureAssignManager lam = new LectureAssignManager();
        lam.run();

        lam.runAssign();
    }
}
