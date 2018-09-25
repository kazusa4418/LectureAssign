package configuration;

import lecture.LargeLecture;
import lecture.Lecture;
import lecture.LectureList;
import lecture.SmallLecture;
import office.Office;
import office.OfficeList;
import office.RoomSize;
import util.LoggerProvider;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {
    private static int calendarYear = Calendar.getInstance().get(Calendar.YEAR);
    private static OfficeList offices = new OfficeList();
    private static LectureList lectures = new LectureList();

    private static Logger logger = LoggerProvider.getSimpleFormatLogger("configuration", "./");

    static {
        readProperties();
    }

    public static int getYear() {
        return calendarYear;
    }
    public static void setYear(int year) {
        calendarYear = year;
    }

    public static Office[] getOffices() {
        return offices.toArray();
    }

    public static Lecture[] getLectures() {
        return lectures.toArray();
    }

    private static void readProperties() {
        readOfficeProperty();
        readLectureProperty();
    }

    private static void readOfficeProperty() {
        File file = new File("./properties/office");
        if (!file.exists()) {
            throw new AssertionError("./properties/office does not exists.");
        }

        File[] files = file.listFiles();

        assert files != null;
        for (File f : files) {
            // .propertiesファイルでなかった場合
            if (!f.getName().endsWith(".properties")) {
                continue;
            }

            try (Reader reader = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8)) {
                Properties properties = new Properties();
                properties.load(reader);

                String officeName = properties.getProperty("officeName", "empty");

                String rooms  = properties.getProperty("rooms", "small");

                String[] arrayRooms = rooms.split(" ");
                RoomSize[] sizes = Arrays.stream(arrayRooms).map(RoomSize::get).toArray(RoomSize[]::new);

                Office office = new Office(officeName, sizes);
                offices.add(office);
            }
            catch (IOException | NumberFormatException err) {
                logger.log(Level.SEVERE, err.toString());
            }
        }
    }

    private static void readLectureProperty() {
        File file = new File("./properties/lecture");
        if (!file.exists()) {
            throw new AssertionError("./properties/lecture does not exists.");
        }

        File[] files = file.listFiles();

        assert files != null;
        for (File f : files) {
            // .propertiesファイルでなかった場合
            if (!f.getName().endsWith(".properties")) {
                continue;
            }

            try (Reader reader = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8)) {
                Properties properties = new Properties();
                properties.load(reader);

                String lectureId = properties.getProperty("lectureId", "empty");
                String lectureName = properties.getProperty("lectureName", "empty");
                int period = Integer.parseInt(properties.getProperty("period", "1"));
                int holdingTimes = Integer.parseInt(properties.getProperty("holdingTimes", "0"));
                RoomSize size = RoomSize.get(properties.getProperty("roomSize", "small"));

                HashMap<Office, Integer> office_weighting = toMap(properties.getProperty("office", null));

                for (int i = 0; i < holdingTimes; i++ ) {
                    Lecture lecture;

                    if (size == RoomSize.LARGE) {
                        lecture = new LargeLecture(lectureId, lectureName, period, office_weighting);
                    }
                    else {
                        lecture = new SmallLecture(lectureId, lectureName, period, office_weighting);
                    }

                    lectures.add(lecture);
                }
            }
            catch (IOException | NumberFormatException err) {
                logger.log(Level.SEVERE, err.toString());
            }
        }
    }

    private static HashMap<Office, Integer> toMap(String s) {
        HashMap<Office, Integer> map = new HashMap<>();
        String[] ss = s.split(" ");

        for (int i = 0; i < ss.length; i += 2) {
            map.put(offices.get(ss[i]), Integer.parseInt(ss[i + 1]));
        }
        return map;
    }
}
