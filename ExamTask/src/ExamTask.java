import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andrew on 29.05.2014.
 */
public class ExamTask {
    //static String strPattern = "\\d{1,2}";
    static String strPattern = "(\\d{1,2})(.)(.*)(.*?)";
    static Pattern pattern = Pattern.compile(strPattern);
    static AtomicInteger atom = new AtomicInteger(0);

    public static void main(String[] args) throws IOException {
        /*
        String s1 = "1.sdfsdfg";
        String s2 = "1. sdfsdfg";
        String s3 = "12.sdfsdfg";
        String s4 = "12. sdfsdfg";
        String s5 = "12 sdfsdfg";
        String s6 = "sdfsdfg";
        Matcher matcher = pattern.matcher(s5);

        if (matcher.find( )) {
            System.out.println("Count: " + matcher.groupCount());
            for (int i = 0; i < matcher.groupCount(); i++) {
                System.out.println("Group " + i + ":" + matcher.group(i));
            }
        } else {
            System.out.println("NO MATCH");
        }
        System.exit(0);
        */


        if (args.length < 1) throw new RuntimeException("Specify file with questions");
        StringBuilder file = new StringBuilder();
        for (String s : args) file.append(s).append(" ");

        Formatter fmt = new Formatter();
        Map<Integer, String> qMap = read(file.toString());
        int questionsNum = qMap.size();
        int maxlen = maxLen(qMap);
        String line = line(maxlen) + "\n";
        String middle ="%"+ (maxlen/2 - 1) + "s";
        String end ="%"+ (maxlen/2 - 1)  + "s";

        List<Integer> questions = new ArrayList<Integer>();
        for (int i = 1; i < questionsNum + 1; i++) questions.add(i);

        int bilet = 1;
        Iterator it = questions.listIterator();
        while(it.hasNext()) {
            fmt.format("%s", line);
            //fmt.format("%s "+ middle + " " + end, "|", "Билет № " + bilet + ".", "|\n");
            fmt.format("%s "+ middle, "|", "Билет № " + bilet + ".\n");
            for (int i = 1; i < 3; i ++) {
                try {
                    String qnum = "|" + i + ".";
                    int randomValue = new Random().nextInt(questions.size());
                    int q = questions.get(randomValue);
                    String space = "%" + (maxlen - qMap.get(q).length() - 3) + "s";
                    fmt.format("%s %s " + space, qnum, qMap.get(q), "|\n");
                    questions.remove((Integer) q);
                } catch (Exception ex) {
                    String qnum = "|" + i + ".";
                    int random = 0;
                    while (random == 0) random = new Random().nextInt(questionsNum);
                    String space = "%" + (maxlen - qMap.get(random).length() - 3) + "s";
                    fmt.format("%s %s " + space, qnum, qMap.get(random), "|\n");
                    //fmt.format("%s", "|2. ??? (Add manual)\n");
                }
            }
            bilet++;
            fmt.format("%s %" + maxlen + "s", "|", "|\n");
        }
        fmt.format("%s", line);

        System.out.println(fmt);
    }

    // Чтение локального файла построчно
    public static Map<Integer, String> read(String filename) throws IOException {
        Map<Integer, String> map = new HashMap<Integer, String>();

        BufferedReader in = new BufferedReader(new FileReader(filename));
        while (in.ready()) {
            int delim = 0;
            String s = in.readLine();
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                delim = s.indexOf(".");
                int qNum = Integer.valueOf(s.substring(0, delim));
                String txt = s.substring(delim + 2);
                map.put(qNum, txt);
            }
            else if (!s.isEmpty()) {
                int qNum = atom.incrementAndGet();
                String txt = s;
                map.put(qNum, txt);
            }
        }
        in.close();

        return map;
    }

    public static int maxLen(Map<Integer, String> map) {
        int len = 0;
        for (String s : map.values())
            if (s.length() > len) len = s.length();

        len += 5; // casuse we have "|1. ... |"

        if (len%2 !=0) len++;

        return len;
    }

    static String line(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i< len; i++) sb.append("-");

        return sb.toString();
    }
}
