package InterfaceTest;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CompareToText {

    public static void main(String[] args) throws IOException {
        f();
    }

    public static void f() throws IOException {
        //文本一是主文本
        Reader in = new FileReader(new File("C:\\Users\\user\\Desktop\\text2.txt"));
        BufferedReader reader = new BufferedReader(in);

        String s = "";
        Map<String, Integer> map1 = new HashMap<String, Integer>();
        while ((s = reader.readLine()) != null) {
            map1.put(s, 1);

        }
        reader.close();
        //文本二是需要对比的文本
        in = new FileReader(new File("C:\\Users\\user\\Desktop\\text1.txt"));

        reader = new BufferedReader(in);

        s = "";
        while ((s = reader.readLine()) != null) {
            if (!map1.containsKey(s)) {
                System.out.println(s);
            }
        }
        reader.close();

    }
}
