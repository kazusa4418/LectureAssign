public class LargeLecture extends Lecture {

    public LargeLecture(String id, String name, int period) {
        super(id, name, period);
    }

    public String toString() {
        return getId() + " " + getStartDay();
    }
}
