import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Andrew on 29.05.2014.
 */
public class ExamTask {
    // 126 - 4 - 1
    static String t = "------------------------------------------------------------------------------------------------------------------------------";

    public static void main(String[] args) throws IOException {
        if (args.length < 1) throw new RuntimeException("Specify file with questions");
        StringBuilder file = new StringBuilder();// = "D:\\google\\KKC\\ККС. Вопросы 3 семестр 2014.txt";
        for (String s : args) file.append(s).append(" ");

        Map<Integer, String> qMap = read(file.toString());
        int questionsNum = qMap.size();
        List<Integer> questions = new ArrayList<Integer>();
        for (int i = 1; i < questionsNum + 1; i++) questions.add(i);

        int bilet = 1;
        Iterator it = questions.listIterator();
        while(it.hasNext()) {
            System.out.println(t);
            String begin = "|\t\t\t\t\t\t\t  Билет №" + bilet;
            System.out.println("|\t\t\t\t\t\t\t  Билет №" + bilet);
            for (int i = 1; i < 3; i ++) {
                try {
                    int randomValue = new Random().nextInt(questions.size());
                    int q = questions.get(randomValue);
                    String txt = qMap.get(q);
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < t.length() - 5 - txt.length(); j++) sb.append(" ");
                    System.out.println("|" + i + ". " + qMap.get(q) + sb.toString() + "|");
                    questions.remove((Integer) q);
                } catch (Exception ex) {
                    System.out.println("|2. ??? (Add manual)");
                }
            }
            bilet++;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < t.length() - 2; j++) sb.append(" ");
            System.out.println("|" + sb.toString() + "|");
        }
        System.out.println(t);
    }

    // Чтение локального файла построчно
    public static Map<Integer, String> read(String filename) throws IOException {
        Map<Integer, String> map = new HashMap<Integer, String>();

        BufferedReader in = new BufferedReader(new FileReader(filename));
        while (in.ready()) {
            String s = in.readLine();
            if (!s.isEmpty()) {
                int delim = s.indexOf(".");
                int qNum = Integer.valueOf(s.substring(0, delim));
                String txt = s.substring(delim + 2);
                map.put(qNum, txt);
            }
        }
        in.close();

        return map;
    }
}
