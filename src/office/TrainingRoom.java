package office;

import calendar.Calendar;
import calendar.Day;
import calendar.DayNotFoundException;
import calendar.Month;
import lecture.Lecture;
import lecture.LectureElement;
import office.Office;

import java.util.HashMap;
import java.util.Map;

public class TrainingRoom {
    private Office office;
    private int no;
    private Map<Month, Map<Day, LectureElement>> map = new HashMap<>();

    private RoomSize size;

    public TrainingRoom(Office office, int no, RoomSize size) {
        this.office = office;
        this.no = no;
        Calendar calendar = Calendar.getInstance();
        Month[] months = calendar.getMonthList();

        for (Month month : months) {
            Map<Day, LectureElement> dayMap = new HashMap<>();

            for (Day day : month.getDayList()) {
                dayMap.put(day, null);
            }
            map.put(month, dayMap);
        }

        this.size = size;
    }

    public int count(Month month, Lecture lecture) {
        int count = 0;

        Map<Day, LectureElement> dayMap = map.get(month);

        for (Day d : dayMap.keySet()) {
            if (dayMap.get(d) == null) {
                continue;
            }

            LectureElement element = dayMap.get(d);
            if (lecture.equals(element.getLecture()) && element.getLecture().getStartDay().getMonth() == month) {
                count++;
            }
        }

        assert count % lecture.getPeriod() == 0;
        return count / lecture.getPeriod();
    }

    public int count(Lecture lecture) {
        int count = 0;
        for (Month month : map.keySet()) {
            Map<Day, LectureElement> dayMap = map.get(month);

            for (Day d : dayMap.keySet()) {
                if (dayMap.get(d) == null) {
                    continue;
                }

                if (lecture.equals(dayMap.get(d).getLecture())) {
                    count++;
                }
            }
        }
        assert count % lecture.getPeriod() == 0;
        return count / lecture.getPeriod();
    }

    public Lecture getLecture(Day day) {
        LectureElement element = map.get(day.getMonth()).get(day);
        return element != null ? element.getLecture() : null;
    }

    public RoomSize size() {
        return size;
    }

    public void assign(Day day, Lecture lecture) throws DayNotFoundException {
        Map<Day, LectureElement> dayMap = map.get(day.getMonth());

        lecture.setStartDay(day);
        for (LectureElement element : lecture.getElements()) {
            dayMap.put(day, element);
            day = day.next();
        }
    }

    public void showPlans() {
        for (Month month : map.keySet()) {
            Map<Day, LectureElement> dayMap = map.get(month);

            for (Day day : dayMap.keySet()) {
                if (dayMap.get(day) == null) {
                    continue;
                }
                System.out.println(day + " " + dayMap.get(day));
            }
        }
    }

    public String toString() {
        return office + " 研修室" + no;
    }
}
