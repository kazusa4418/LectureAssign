package office;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OfficeList implements Iterable<Office> {
    private List<Office> offices = new ArrayList<>();

    public void add(Office office) {
        offices.add(office);
    }

    Office get(int index) {
        return offices.get(index);
    }

    public Office get(String name) {
        for (Office office : offices) {
            if (office.getOfficeName().equals(name)) {
                return office;
            }
        }
        return null;
    }

    public Office[] toArray() {
        return offices.toArray(new Office[0]);
    }

    public Iterator<Office> iterator() {
        return offices.iterator();
    }
}
