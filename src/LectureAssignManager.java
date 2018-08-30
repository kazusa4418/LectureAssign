import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

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
        for (int i = 0; i < offices.length; i++ ) {
            TrainingRoom[] rooms = offices[i].getTrainingRoomList();
            for (int j = 0; j < rooms.length; j++ ) {
                System.out.println(rooms[j]);
                rooms[j].showPlans();
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
            }
        }
    }

    private void assign(LargeLecture lecture) {
        Office office = select(offices);

        Month month = select(getLeastAssignedMonth(office, lecture));

        assignLecture(office, month, lecture);

    }

    //TODO: プロパティにオフィスごとの重みづけはされているのでそれに従ってオフィスを取得するメソッドを作成する

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
        Day assignedDay = select(assignableDays);

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
        assignedRoom.assign(assignedDay, lecture);
    }

    private boolean canAssign(TrainingRoom[] rooms, Day day, LargeLecture lecture) {
        // 月~金のすべてで研修がなく祝日が含まれていない研修室があるか確かめる

        boolean flag1 = false;

        loop:
        for (TrainingRoom room : rooms) {
            Day d = day;

            for (int i = 0; i < 5; i++) {
                if (room.getLecture(day) != null || d.isHoliday()) {
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
            // 先週に同じ講義が割り当てられていないか確かめる
            d = day.before(7);
            if (LectureUtils.equals(room.getLecture(d), (lecture))) {
                flag2 = false;
            }

            // 来週に同じ講義が割り当てられていないか確かめる
            d = day.next(7);
            if (LectureUtils.equals(room.getLecture(d), (lecture))) {
                flag2 = false;
            }
        }
        return flag1 && flag2;
    }

    private boolean canAssign(TrainingRoom room, Day day) {
        Day d = day;

        for (int i = 0; i < 5; i++) {
            if (room.getLecture(day) != null || d.isHoliday()) {
                return false;
            }
            try {
                d = d.next();
            } catch (DayNotFoundException err) {
                return false;
            }
        }
        return true;
    }
}
