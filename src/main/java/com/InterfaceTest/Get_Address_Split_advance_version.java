package com.InterfaceTest;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Get_Address_Split_advance_version {

    public static void main(String[] args) throws IOException {
        // difference("D:/wb/1.txt","D:/wb/2.txt");
        split("C:\\Users\\user\\Desktop\\suffix.txt", "C:\\Users\\user\\Desktop\\text3.txt");
    }

    //分离方法

    /**
     * @param path1 要分隔的文本
     * @param path2
     * @throws IOException
     */
    public static void split(String path1, String path2) throws IOException {
        Reader in = new FileReader(new File(path1));
        //为了提供读的效率而设计的
        BufferedReader reader = new BufferedReader(in);
        String s = "";
        // 分割后存放的文本
        Writer writer = new FileWriter(new File(path2));

        //reader.readLine 是按行读取数据，再将读取的数据放到s里
        while ((s = reader.readLine()) != null) {
            int index = -1;
            if (s.indexOf('市') != -1) {
                index = s.indexOf('市');
            } else if (s.indexOf('镇') != -1) {
                index = s.indexOf('镇');
            } else if (s.indexOf('县') != -1) {
                index = s.indexOf('县');
            } else if (s.indexOf('乡') != -1) {
                index = s.indexOf('乡');
            }
            if (index != -1) {
                writer.append(s.substring(0, index + 1) + "\t" + s.substring(index + 1) + "\r\n");
            } else {
                writer.append(s + "\r\n");
            }
        }
        writer.close();
        reader.close();
        System.out.println("SUCCESS!!!");
    }
    public static void difference(String path1, String path2) throws IOException {

        Reader in = new FileReader(new File(path1));
        BufferedReader reader = new BufferedReader(in);

        String s = "";
        Map<String, Integer> map1 = new HashMap<String, Integer>();
        while ((s = reader.readLine()) != null) {
            map1.put(s, 1);

        }
        reader.close();

        in = new FileReader(new File(path2));

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
