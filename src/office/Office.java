package office;

import calendar.Month;
import lecture.Lecture;

import java.util.ArrayList;
import java.util.List;

public class Office {
    private String name;

    private List<TrainingRoom> rooms = new ArrayList<>();

    public Office(String name, RoomSize[] sizes) {
        this.name = name;

        for (int i = 0; i < sizes.length; i++ ) {
            rooms.add(new TrainingRoom(this, i + 1, sizes[i]));
        }
    }

    public String getOfficeName() {
        return name;
    }

    public TrainingRoom[] getTrainingRoomList() {
        return rooms.toArray(new TrainingRoom[0]);
    }

    public int inquire(Month month, Lecture lecture) {
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

    public String toString() {
        return name;
    }
}