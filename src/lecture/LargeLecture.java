package lecture;

import office.Office;

import java.util.HashMap;

public class LargeLecture extends Lecture {

    public LargeLecture(String id, String name, int period, HashMap<Office, Integer> map) {
        super(id, name, period, map);
    }
}
