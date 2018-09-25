package lecture;

import calendar.Day;
import office.Office;

import java.util.HashMap;

public class Lecture {
    private String id;
    private String name;
    private LectureElement[] lectureDay;
    private Day startDay;
    private HashMap<Office, Integer> officeWeighting;

    Lecture(String id, String name, int period, HashMap<Office, Integer> map) {
        this.name = name;
        this.id = id;

        lectureDay = new LectureElement[period];
        for (int i = 0; i < period; i++ ) {
            lectureDay[i] = new LectureElement(this);
        }
        officeWeighting = map;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPeriod() {
        return lectureDay.length;
    }

    public LectureElement[] getElements() {
        return lectureDay;
    }

    public Day getStartDay() {
        return startDay;
    }

    public void setStartDay(Day startDay) {
        this.startDay = startDay;
    }

    public HashMap<Office, Integer> getOfficeWeighting() {
        return officeWeighting;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Lecture)) {
            return false;
        }
        Lecture lecture = (Lecture) o;

        return this.id.equals(lecture.getId());
    }

    public String toString() {
        return getId() + " " + getStartDay();
    }
}
