package com.ferao.InterfaceTest;/*
 * @author Ferao
 * @date
 * @discription
 */

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Demp {
    public static void main(String args[]) {

        File file =new File("D:\\CV\\Password");
        File[] listFiles = file.listFiles();
        for (File listFile : listFiles) {
            System.out.println(listFile.toString());
        }
    }
    //获取下个月
    public static String getPreMonth() {

        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, 1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        String preMonth = dft.format(cal.getTime());
        System.out.println(preMonth);
        return preMonth;
    }

    // 当天
    public String getThisToday(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND,0);
        String start = sdf.format(cal.getTime());
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND,59);
        String end = sdf.format(cal.getTime());
        return start+"|"+end;
    }

    // 本周
    public String getThisWeekDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        ca.setFirstDayOfWeek(Calendar.MONDAY);
        ca.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        ca.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH),ca.get(Calendar.DATE),23,59,59);
        String end = sdf.format(ca.getTime());
        ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        ca.set(Calendar.HOUR_OF_DAY,0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND,0);
        String start = sdf.format(ca.getTime());
        return start+"|"+end;
    }
//    public static void main (String[] args) throws IOException {
//        FileInputStream in = new FileInputStream("D:\\CV\\个人简历信息资料\\photo.jpg");
//        FileOutputStream out = new FileOutputStream("D:\\SpringbootTesk\\SpringBootTest\\photo.jpg");
//        byte[] bytes= new byte[1024];
//        int len=0;
//        while((len=in.read(bytes))!=-1){
//            out.write(bytes,0,len);
//        }
//        in.close();
//        out.close();
//        System.out.println("success");

//        int[] nums = {2,5,0,4,1,-10};
//        System.out.println(Arrays.toString(nums));
        /*
         * 结果:[2, 5, 0, 4, 1, -10]
         */
        /* 之前:2 5 0 4 6 -10
         * 结果:-10 0 2 4 5 6
         */
//        FileInputStream in = new FileInputStream("a.txt");
//        byte[] bytes = new byte[2];
//        int len = in.read(bytes);
//        System.out.println(len);
//        System.out.println(Arrays.toString(bytes));
//        System.out.println(new String(bytes));

//        int len = in.read();
//        System.out.println(len);
//        int len1 = in.read();
//        System.out.println(len1);
//        int len2 = in.read();
//        System.out.println(len2);
//        in.close();

//    }
}
