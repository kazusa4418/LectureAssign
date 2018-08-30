public class LectureElement {
    private Lecture lecture;

    public LectureElement(Lecture lecture) {
        this.lecture = lecture;
    }

    Lecture getLecture() {
        return lecture;
    }

    public String toString() {
        return lecture.toString();
    }
}
