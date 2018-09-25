package lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LectureList implements Iterable<Lecture> {
    private List<Lecture> lectures = new ArrayList<>();

    public void add(Lecture lecture) {
        lectures.add(lecture);
    }

    Lecture get(int index) {
        return lectures.get(index);
    }

    public Lecture[] toArray() {
        return lectures.toArray(new Lecture[0]);
    }

    public Iterator<Lecture> iterator() {
        return lectures.iterator();
    }
}
