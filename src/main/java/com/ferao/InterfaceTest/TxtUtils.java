package com.ferao.InterfaceTest;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TxtUtils {

    private static List<String> nowList;
    /**
     *  对比两个文本，得到不相同的数据
     * @param mainTxt   主文本
     * @param minorTxt  次文本
     * @throws IOException
     */
    public static void different_txt(String mainTxt, String minorTxt) throws IOException {

        Reader in = new FileReader(new File(mainTxt));
        BufferedReader reader = new BufferedReader(in);
        String s = "";
        Map<String, Integer> mainMap = new HashMap<String, Integer>();
        while ((s = reader.readLine()) != null) {
            mainMap.put(s, 1);
        }
        reader.close();

        in = new FileReader(new File(minorTxt));
        reader = new BufferedReader(in);
        while ((s = reader.readLine()) != null) {
            if (!mainMap.containsKey(s)) {
                System.out.println(s);
            }
        }
        reader.close();
    }

    /**
     *  文件夹内当天生成的文件
     *  localhost_access_log.2019-09-01.txt
     * @param dir_path 文件夹路径
     */
    public static void getNowFile(String dir_path){
        nowList = new ArrayList<>();
        File f1 =new File(dir_path);
        File[] listFiles = f1.listFiles();
        for (File listFile : listFiles) {
            if(listFile.getName().endsWith(".txt")){
                int firstIndex = listFile.getName().indexOf(".");
                int lastIndex = listFile.getName().lastIndexOf(".");
                String subName = listFile.getName().substring(firstIndex + 1, lastIndex);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String nowTime=df.format(new Date());
                if (subName.equals(nowTime)){
                    nowList.add(listFile.toString());
                }
            }
        }
    }

    //获取当月
    public static String getNowTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        String lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    //获取下个月
    public static String getPreMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, 1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        String preMonth = dft.format(cal.getTime());
        return preMonth;
    }


}
