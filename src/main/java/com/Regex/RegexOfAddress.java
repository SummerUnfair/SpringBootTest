package com.Regex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

public class RegexOfAddress {

    public static Connection conn=getConnection();;
    public static Statement stat;
    public static ResultSet rs;
    public static Writer  writerA;// 数字型
    public static Writer  writerA0;//I数字型
    public static Writer  writerB;//识别
    public static Writer  writerB0;//I识别
    public static Writer  writerC;//  741号-2
    public static Writer  writerC0;// 741-2号

    public static Writer  writerD;//  2898弄43 ->
    public static Writer  writerD0;// 2898弄34号

    public static Writer  writerE;//  207-1 ->
    public static Writer  writerE0;// 207-1号

    public static Writer  writerF;//  119弄128支弄3
    public static Writer  writerF0;// 119弄128支弄3号

    public static Writer  writer00;//  余量
    public static Writer  writerZ0;//I农

    static {
        try {
            writerA = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\纯数字型-门牌号.txt"));
            writerA0 = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\endAddress\\I纯数字型-门牌号.txt"));
            writerB = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\识别-门牌号.txt"));
            writerB0 = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\endAddress\\I识别-门牌号.txt"));
            writerC = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\规范号-门牌号.txt"));
            writerC0 = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\endAddress\\I规范号-门牌号.txt"));

            writerD = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\规范弄号-门牌号.txt"));
            writerD0 = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\endAddress\\I规弄号-门牌号.txt"));

            writerE = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\规范数字型-门牌号.txt"));
            writerE0 = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\endAddress\\I规范数字型-门牌号.txt"));

            writerF = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\规范支弄型-门牌号.txt"));
            writerF0 = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\endAddress\\I规范支弄型-门牌号.txt"));

            writerZ0 = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\endAddress\\I农-门牌号.txt"));
            writer00 = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\余量.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main (String[] args)
    {
        RegexAddressName();
        try {
            writerA.close();
            writerA0.close();
            writerB.close();
            writerB0.close();
            writerC.close();
            writerC0.close();
            writerD.close();
            writerD0.close();
            writerE.close();
            writerE0.close();
            writerF.close();
            writerF0.close();
            writerZ0.close();
            writer00.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    private static void RegexAddressName() {

        int count=0;
        String Building=null;
        try{
            stat = conn.createStatement();
            rs=stat.executeQuery("select * from gp_city_address_1907 gp");
            while(rs.next()){
                count++;
                Building=rs.getString("门牌号");
                if (Building==null){continue;};
                    //boolean isInt = Pattern.matches("\\d+", Building);
                    if (Pattern.matches("\\d+", Building)){               //纯数字

                        writerA.append(Building+"\r\n");
                        writerA0.append(Building+"号"+"\r\n");

                    }else if (Pattern.matches(".+识别$", Building)){       //55识别       //后面 加号
                        writerB.append(Building+"\r\n");
                        writerB0.append(Building+"号"+"\r\n");

                    }else if (Pattern.matches(".+农.+", Building)){
                        String reBuilding=Building.replace('农','弄');  //673农1-16号       //农改成弄
                        writerZ0.append(reBuilding+"\r\n");

                    }else if (Pattern.matches("\\d+号-\\d+", Building)){             //741号-2    //这种统一处理成741-2号
                        String reBuilding=Building.replace("号","");
                        writerC.append(Building+"\r\n");
                        writerC0.append(reBuilding+"号"+"\r\n");
                    }else if(Pattern.matches("\\d+弄\\d+", Building)){                //2898弄43 ->2898弄34号
                        writerD.append(Building+"\r\n");
                        writerD0.append(Building+"号"+"\r\n");
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    }else if (Pattern.matches("\\d+-\\d+", Building)){             //207-1  ->207-1号
                        writerE.append(Building+"\r\n");
                        writerE0.append(Building+"号"+"\r\n");
                    }else if (Pattern.matches("\\d+弄\\d+支弄\\d+", Building)){     //119弄128支弄16

                        writerF.append(Building+"\r\n");
                        writerF0.append(Building+"号"+"\r\n");
                    }

                    else{
                        writer00.append(Building+"\r\n");
                    }

                if (count%5000==0){
                        System.out.println("5000..");
                    }
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }


    private static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@10.145.206.20:1521:gis", "GIS", "M3fXy_TFaUt");
            conn.setAutoCommit(false);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return conn;
        }
    }
}
