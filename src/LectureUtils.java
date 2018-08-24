public class LectureUtils {
    public static boolean equals(Lecture l1, Lecture l2) {
        if (l1 == null && l2 == null) {
            return true;
        }

        if (l1 == null || l2 == null) {
            return false;
        }

        return l1.equals(l2);
    }
}
