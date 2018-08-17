import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {
    private static int calendarYear = Calendar.getInstance().get(Calendar.YEAR);
    private static List<Office> offices = new ArrayList<>();
    private static List<Lecture> lectures = new ArrayList<>();

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
        return offices.toArray(new Office[offices.size()]);
    }

    public static Lecture[] getLectures() {
        return lectures.toArray(new Lecture[lectures.size()]);
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

            try (Reader reader = new InputStreamReader(new FileInputStream(f), "SHIFT-JIS")) {
                Properties properties = new Properties();
                properties.load(reader);

                String officeName = properties.getProperty("officeName", "empty");
                String roomNumber = properties.getProperty("roomNumber", "1");

                Office office = new Office(officeName, Integer.parseInt(roomNumber));
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

            try (Reader reader = new InputStreamReader(new FileInputStream(f), "SHIFT-JIS")) {
                Properties properties = new Properties();
                properties.load(reader);

                String lectureId = properties.getProperty("lectureId", "empty");
                String lectureName = properties.getProperty("lectureName", "empty");
                String period = properties.getProperty("period", "1");
                int holdingTimes = Integer.parseInt(properties.getProperty("holdingTimes", "0"));

                for (int i = 0; i < holdingTimes; i++ ) {
                    lectures.add(new Lecture(lectureId, lectureName, Integer.parseInt(period)));
                }
            }
            catch (IOException | NumberFormatException err) {
                logger.log(Level.SEVERE, err.toString());
            }
        }
    }
}
