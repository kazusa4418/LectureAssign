import java.util.ArrayList;
import java.util.List;

public class Office {
    private String name;

    private List<TrainingRoom> rooms = new ArrayList<>();

    Office(String name, int roomNumber) {
        this.name = name;

        for (int i = 0; i < roomNumber; i++ ) {
            rooms.add(new TrainingRoom());
        }
    }

    String getOfficeName() {
        return name;
    }

    TrainingRoom[] getTrainingRoomList() {
        return rooms.toArray(new TrainingRoom[rooms.size()]);
    }

    int inquire(Month month, Lecture lecture) {
        int count = 0;

        for (TrainingRoom room : rooms) {
            count += room.count(month, lecture);
        }
        return count;
    }

    int inquire(Lecture lecture) {
        int count = 0;

        for (TrainingRoom room : rooms) {
            count += room.count(lecture);
        }
        assert count % lecture.getPeriod() == 0;
        return count / lecture.getPeriod();
    }
}