import calendar.Calendar;
import calendar.Day;
import calendar.DayNotFoundException;
import calendar.DayOfWeek;
import calendar.Month;
import configuration.Configuration;
import lecture.LargeLecture;
import lecture.Lecture;
import lecture.LectureUtils;
import lecture.SmallLecture;
import office.Office;
import office.RoomSize;
import office.TrainingRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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

    public void show() {
        for (Office office : offices) {
            TrainingRoom[] rooms = office.getTrainingRoomList();
            for (TrainingRoom room : rooms) {
                System.out.println(room);
                room.showPlans();
            }
        }
    }

    public void runAssign() {
        for (Lecture lecture : lectures) {
            if (lecture instanceof LargeLecture) {
                LargeLecture largeLecture = (LargeLecture) lecture;
                assign(largeLecture);
            }
            else {
                SmallLecture smallLecture = (SmallLecture) lecture;
                assign(smallLecture);
            }
        }
    }

    private void assign(LargeLecture lecture) {
        Office office = selectOffice(lecture);

        // TODO: 割り振られている数が最も少ない月を単純に取ってきてしまうと
        // TODO: 配置の問題で月に１講義しか入れられなかった月が必ず選択されてしまう不具合を修正する
        Month month = select(getLeastAssignedMonth(office, lecture));

        assignLecture(office, month, lecture);
        if (office.getOfficeName().equals("KAWASAKI"))
        System.out.println(office + ": " + lecture + " : ASSIGN!!!");
    }

    private void assign(SmallLecture lecture) {
        Office office = selectOffice(lecture);

        Month month = select(getLeastAssignedMonth(office, lecture));

        assignLecture(office, month, lecture);
        System.out.println(office + ": " + lecture + " : ASSIGN!!!");
    }

    private Office selectOffice(Lecture lecture) {
        HashMap<Office, Integer> weight = lecture.getOfficeWeighting();

        List<Office> officeList = new ArrayList<>();
        for (Office office : weight.keySet()) {
            int count = weight.get(office);

            for (int i = 0; i < count; i++ ) {
                officeList.add(office);
            }
        }
        Collections.shuffle(officeList);

        return officeList.get(new Random().nextInt(officeList.size()));
    }
    private <E> E select(E[] array) {
        Random random = new Random();
        return array[random.nextInt(array.length)];
    }

    private <E> E select(List<E> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    private Month[] getLeastAssignedMonth(Office office, Lecture lecture) {
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
        return list.toArray(new Month[0]);
    }

    private void assignLecture(Office office, Month month, LargeLecture lecture) {
        // large lecture を行える研修室だけ取得する
        final TrainingRoom[] rooms = Arrays.stream(office.getTrainingRoomList())
                .filter((room) -> room.size() == RoomSize.LARGE).toArray(TrainingRoom[]::new);

        Day[] mondays = month.getDays(DayOfWeek.MONDAY);

        // 取得してきた月曜日のうち割当可能な日のみassignablesに入れる
        List<Day> assignableDays = new ArrayList<>();
        for (Day monday : mondays) {
            if (canAssign(rooms, monday, lecture)) {
                assignableDays.add(monday);
            }
        }

        //TODO: アサインできる日が存在しなかった場合の処理を実装する
        // assignableからランダムに割り当てる日を決める
        Day assignedDay;
        try {
            assignedDay = select(assignableDays);
        }
        catch (IllegalArgumentException err) {
            System.err.println(office + ": " + assignableDays.size() + ", " + month);
            throw new AssertionError();
        }

        // assignedDayにlectureを割り当てることのできる研修室を取得する
        List<TrainingRoom> assignableRooms = new ArrayList<>();
        for (TrainingRoom room : rooms) {
            if (canAssign(room, assignedDay)) {
                assignableRooms.add(room);
            }
        }

        // assignableRoomsからランダムに割り当てる研修室を決める
        TrainingRoom assignedRoom = select(assignableRooms);

        // 割り当てる
        try {
            assignedRoom.assign(assignedDay, lecture);
        }
        // DayNotFoundExceptionをキャッチしないと行けないこと自体がおかしいよね
        catch (DayNotFoundException err) {
            throw new AssertionError();
        }
    }

    private void assignLecture(Office office, Month month, SmallLecture lecture) {
        TrainingRoom[] rooms = office.getTrainingRoomList();

        Day[] days = month.getDayList();

        // 取得してきた日のうち割当可能な日のみassignablesに入れる
        List<Day> assignableDays = new ArrayList<>();
        for (Day day : days) {
            if (canAssign(rooms, day, lecture)) {
                assignableDays.add(day);
            }
        }

        // assignableDaysから割り当てる日を決める
        Day assignedDay = select(assignableDays);

        // assignedDayにlectureを割り当てることのできる研修室を取得する
        List<TrainingRoom> assignableRooms = new ArrayList<>();
        for (TrainingRoom room : rooms) {
            if (canAssign(room, assignedDay, lecture)) {
                assignableRooms.add(room);
            }
        }

        // assignableRoomsからランダムに割り当てる研修室を決める
        TrainingRoom assignedRoom = select(assignableRooms);

        // 割り当てる
        try {
            assignedRoom.assign(assignedDay, lecture);
        }
        // DayNotFoundExceptionをキャッチしないと行けない事自体ナンセンス
        catch (DayNotFoundException err) {
            throw new AssertionError();
        }
    }

    private boolean canAssign(TrainingRoom[] rooms, Day day, LargeLecture lecture) {
        // 月~金のすべてで研修がなく祝日が含まれていない研修室があるか確かめる

        boolean flag1 = false;

        loop:
        for (TrainingRoom room : rooms) {
            Day d = day;

            for (int i = 0; i < 5; i++) {
                // 何らかの講義が行われていたらこの研修室は使えない
                if (room.getLecture(d) != null || d.isHoliday()) {
                    continue loop;
                }

                try {
                    d = d.next();
                } catch (DayNotFoundException err) {
                    // その週に休日・祝日があるかわからないのであれば検討する意味はない
                    return false;
                }
            }
            flag1 = true;
            break;
        }

        boolean flag2 = true;

        for (TrainingRoom room : rooms) {
            Day d = day;

            // 今週に同じ講義が割り当てられていないか確かめる
            if (LectureUtils.equals(room.getLecture(d), lecture)) {
                flag2 = false;
                break;
            }

            // 先週に同じ講義が割り当てられていないか確かめる
            try {
                d = day.before(7);
            }
            catch (DayNotFoundException err) {
                return false;
            }

            if (LectureUtils.equals(room.getLecture(d), lecture)) {
                flag2 = false;
                break;
            }

            // 来週に同じ講義が割り当てられていないか確かめる
            try {
                d = day.next(7);
            }
            catch (DayNotFoundException err) {
                return false;
            }

            if (LectureUtils.equals(room.getLecture(d), lecture)) {
                flag2 = false;
                break;
            }
        }
        return flag1 && flag2;
    }

    private boolean canAssign(TrainingRoom[] rooms, Day day, SmallLecture lecture) {
        int period = lecture.getPeriod();

        loop:
        for (TrainingRoom room : rooms) {
            Day d = day;

            System.out.println("======================================");

            for (int i = 0; i < period; i++) {
                if (room.getLecture(d) != null || d.isHoliday()) {
                    continue loop;
                }
                try {
                    System.out.println(d);
                    d = d.next();
                }
                catch (DayNotFoundException err) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean canAssign(TrainingRoom room, Day day) {
        Day d = day;

        for (int i = 0; i < 5; i++) {
            if (room.getLecture(day) != null || d.isHoliday()) {
                return false;
            }
            try {
                d = d.next();
            }
            catch (DayNotFoundException err) {
                return false;
            }
        }
        return true;
    }

    private boolean canAssign(TrainingRoom room, Day day, Lecture lecture) {
        Day d = day;

        for (int i = 0; i < lecture.getPeriod(); i++) {
            if (room.getLecture(d) != null || d.isHoliday()) {
                return false;
            }
            try {
                d = d.next();
            }
            catch (DayNotFoundException err) {
                return false;
            }
        }
        return true;
    }
}
