import calendar.Calendar;
import calendar.Month;
import configuration.Configuration;
import office.Office;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignController {
    private static Map<Office, Status> officeMap;
    private static Map<Month, Status> monthsMap;

    static void init() {
        HashMap<Office, Status> tmpOffice = new HashMap<>();
        for (Office office : Configuration.getOffices()) {
            tmpOffice.put(office, Status.OK);
        }
        officeMap = tmpOffice;

        HashMap<Month, Status> tmpMonth = new HashMap<>();
        for (Month month : Calendar.getInstance().getMonthList()) {
            tmpMonth.put(month, Status.OK);
        }
        monthsMap = tmpMonth;
    }

    public static List<Office> getOffices() {
        ArrayList<Office> tmp = new ArrayList<>();

        for (Office office : officeMap.keySet()) {
            if (officeMap.get(office) == Status.OK) {
                tmp.add(office);
            }
        }
        return tmp;
    }

    public static Month[] getMonths() {
        ArrayList<Month> tmp = new ArrayList<>();

        for (Month month : monthsMap.keySet()) {
            if (monthsMap.get(month) == Status.OK) {
                tmp.add(month);
            }
        }
        return tmp.toArray(new Month[0]);
    }

    static void setStatus(Month month, Status status) {
        monthsMap.put(month, status);
    }
}

enum Status {
    OK,
    NG
}