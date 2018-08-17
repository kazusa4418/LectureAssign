public class Main {
    public static void main(String[] args) {
        if (args.length > 0) {
            Configuration.setYear(Integer.parseInt(args[0]));
        }

        LectureAssignManager lam = new LectureAssignManager();
        lam.run();
    }
}
