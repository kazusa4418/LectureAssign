package lecture;

import lecture.Lecture;
import office.Office;

import java.util.HashMap;

public class SmallLecture extends Lecture {
    public SmallLecture(String id, String name, int period, HashMap<Office, Integer> map) {
        super(id, name, period, map);
    }
}
