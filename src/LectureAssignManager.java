import java.util.ArrayList;
import java.util.List;

public class LectureAssignManager {

    private Office[] offices;

    private Lecture[] lectures;

    public LectureAssignManager() {
        offices = Configuration.getOffices();
        lectures = Configuration.getLectures();
    }

    public void run() {
        for (Office o : offices) {
            System.out.println(o.getOfficeName() + ":" + o.getTrainingRoomList().length);
        }

        for (Lecture l : lectures) {
            System.out.println(l.getId() + ":" + l.getName() + ":" + l.getPeriod());
        }
    }

    void assign(Lecture lecture) {
        int period = lecture.getPeriod();

        int random = new java.util.Random().nextInt(offices.length);

        Office office = offices[random];
        Month[] months = getLeastAssignedMonth(office, lecture);



    }

    private Month[] getLeastAssignedMonth(Office office, Lecture lecture) {
        TrainingRoom[] rooms = office.getTrainingRoomList();
        Month[] months = Calendar.getInstance().getMonthList();

        int min = Integer.MAX_VALUE;
        for (Month month : months) {
            min = Math.min(min, office.inquire(month, lecture));
        }

        List<Month> list = new ArrayList<>();
        for (Month month : months) {
            if (office.inquire(month, lecture) == min) {
                list.add(month);
            }
        }
        return list.toArray(new Month[list.size()]);
    }
}
