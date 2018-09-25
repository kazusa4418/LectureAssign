import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Tester {
    public static void main(String... args) {
        List<LinkedList<Integer>> list = new ArrayList<>();

        LinkedList<Integer> ele = new LinkedList<>();

        for (int i = 0; i < 10; i++ ) {
            list.add(ele);
        }

        for (LinkedList li : list) {
            System.out.println(li);
        }
    }
}
