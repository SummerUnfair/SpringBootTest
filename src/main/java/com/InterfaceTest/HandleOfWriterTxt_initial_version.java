package com.InterfaceTest;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HandleOfWriterTxt_initial_version {

    public static String sqlString;
    private static String path;
    public static String url;
    public static String user;
    public static String password;

    String datefm="";
    String dateNow="";

    public static void main(String[] args) {
        HandleOfWriterTxt_initial_version G=new HandleOfWriterTxt_initial_version();
        G.initConfig();
        G.writeToTxt(sqlString);
    }

    public  void initConfig(){

        //获取key对应的value值
        sqlString ="select * from addr_tags a where a.AFFORDABLE_HOUSING_TAG is not null or a.WHOLE_FAMILY_ENJOY_30 is not null or a.WHOLE_FAMILY_ENJOY_50 is not null or a.FIX_OLD_CUSTOMER_720 is not null or a.RESIDENTIA_BROADBAND_20_10 is not null or a.BROADBAND_20 is not null\r\n";
        path = "C:\\Users\\user\\Desktop\\";

        url = "jdbc:oracle:thin:@10.145.206.20:1521:gis";
        user = "GIS";
        password = "M3fXy_TFaUt";

        datefm=getNowTime();
        dateNow=getNowTime();

        System.out.println("datefm:"+datefm);
        System.out.println("dateNow:"+dateNow);

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
    //获取上个月
    public static String getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, -1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        String lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }


    public void writeToTxt(String sqlString) {
        //日期处理
        String datefm="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try{
            Date date = new java.util.Date();
            datefm = sdf.format(date);
            System.out.println(datefm);
        }catch(Exception e){
            e.printStackTrace();
        };
        //写入操作准备
        try (
                //true表示会接着跑
                FileOutputStream fileOutputStream = new FileOutputStream(path+"combo_menu_"+datefm+".txt",true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                PrintWriter out = new PrintWriter(bufferedWriter);

                Connection conn = getConnection();
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sqlString)
        ) {

            //开始写入// \r\n即为换行
            out.write("展示顺序:\r\n" +
                    "地址ID\r\n" +
                    "经适房\r\n" +
                    "全家享新融合30档次宽带加装包\r\n" +
                    "全家享新融合50档次宽带加装包\r\n" +
                    "固话老用户回馈720年付套餐\r\n" +
                    "住宅宽频20套餐10元宽带加装包\r\n" +
                    "宽频套餐20元宽带加装包\r\n"+
                    "---------------------------------------------\r\n");
            int count = 0;
            while (rs.next()) {
                count++;
                int ADDRESS_ID=rs.getInt("ADDRESS_ID");
                String AFFORDABLE_HOUSING_TAG=rs.getString("AFFORDABLE_HOUSING_TAG");
                String WHOLE_FAMILY_ENJOY_30=rs.getString("WHOLE_FAMILY_ENJOY_30");
                String WHOLE_FAMILY_ENJOY_50=rs.getString("WHOLE_FAMILY_ENJOY_50");
                String FIX_OLD_CUSTOMER_720=rs.getString("FIX_OLD_CUSTOMER_720");
                String RESIDENTIA_BROADBAND_20_10=rs.getString("RESIDENTIA_BROADBAND_20_10");
                String BROADBAND_20 = rs.getString("BROADBAND_20");

                int index1=-1;
                int index2=-1;
                int index3=-1;
                int index4=-1;
                int index5=-1;
                int index6=-1;
                //1
                if(AFFORDABLE_HOUSING_TAG!=null) {
                    index1=1;
                }else {
                    index1=0;
                }
                //2
                if(WHOLE_FAMILY_ENJOY_30!=null) {
                    index2=1;
                }else {
                    index2=0;
                }
                //3
                if(WHOLE_FAMILY_ENJOY_50!=null) {
                    index3=1;
                }else {
                    index3=0;
                }
                //4
                if(FIX_OLD_CUSTOMER_720!=null) {
                    index4=1;
                }else {
                    index4=0;
                }
                //5
                if(RESIDENTIA_BROADBAND_20_10!=null) {
                    index5=1;
                }else {
                    index5=0;
                }
                if(BROADBAND_20!=null) {
                    index6=1;
                }else {
                    index6=0;
                }

                out.write(ADDRESS_ID+"|@|"+index1+"|@|"+index2+"|@|"+index3+"|@|"+index4+"|@|"+index5+"|@|"+index6+"\r\n");

                if(count%1000==0){
                    out.flush(); // 把缓存区内容压入文件
                    System.out.println("正在写入："+count+"条");
                }
            }
            System.out.println("总写入数量："+count);
            System.out.println( new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(Calendar.getInstance().getTime()));
            out.flush(); // 把缓存区内容压入文件
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取数据库连接
     */
    private Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url,user,password);
            conn.setAutoCommit(false);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return conn;
        }
    }
}
