public class Lecture {
    private String id;
    private String name;
    private int period;
    private Day startDay;

    Lecture(String id, String name, int period) {
        this.name = name;
        this.id = id;
        this.period = period;
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    int getPeriod() {
        return period;
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
