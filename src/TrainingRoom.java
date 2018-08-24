import java.util.HashMap;
import java.util.Map;

public class TrainingRoom {
    private Map<Month, Map<Day, Lecture>> map = new HashMap<>();

    private RoomSize size;

    public TrainingRoom(RoomSize size) {
        Calendar calendar = Calendar.getInstance();
        Month[] months = calendar.getMonthList();

        for (Month month : months) {
            Map<Day, Lecture> dayMap = new HashMap<>();

            for (Day day : month.getDayList()) {
                dayMap.put(day, null);
            }
            map.put(month, dayMap);
        }

        this.size = size;
    }

    public void setLecture(Day day, Lecture lecture) {
        Month month = day.getMonth();
        Map<Day, Lecture> dayMap = map.get(month);
        dayMap.put(day, lecture);
    }

    public int count(Month month, Lecture lecture) {
        int count = 0;

        Map<Day, Lecture> dayMap = map.get(month);

        for (Day d : dayMap.keySet()) {
            if (dayMap.get(d) == null) {
                continue;
            }

            if (lecture.equals(dayMap.get(d)) && dayMap.get(d).getStartDay().getMonth() == month) {
                count++;
            }
        }

        assert count % lecture.getPeriod() == 0;
        return count / lecture.getPeriod();
    }

    public int count(Lecture lecture) {
        int count = 0;
        for (Month month : map.keySet()) {
            Map<Day, Lecture> dayMap = map.get(month);

            for (Day d : dayMap.keySet()) {
                if (dayMap.get(d) == null) {
                    continue;
                }

                if (lecture.equals(dayMap.get(d))) {
                    count++;
                }
            }
        }
        assert count % lecture.getPeriod() == 0;
        return count / lecture.getPeriod();
    }

    public Lecture getLecture(Day day) {
        return map.get(day.getMonth()).get(day);
    }

    public RoomSize size() {
        return size;
    }

    public void assign(Day day, Lecture lecture) {
        Map<Day, Lecture> dayMap = map.get(day.getMonth());
        dayMap.put(day, lecture);
    }
}
