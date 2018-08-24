public class Lecture {
    private String id;
    private String name;
    private LectureElement[] lectureDay;
    private Day startDay;

    Lecture(String id, String name, int period) {
        this.name = name;
        this.id = id;

        lectureDay = new LectureElement[period];
        for (int i = 0; i < period; i++ ) {
            lectureDay[i] = new LectureElement();
        }
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    int getPeriod() {
        return lectureDay.length;
    }

    Day getStartDay() {
        return startDay;
    }

    void setStartDay(Day startDay) {
        this.startDay = startDay;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Lecture)) {
            return false;
        }
        Lecture lecture = (Lecture) o;

        return this.name.equals(lecture.getName());
    }
}
