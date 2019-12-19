package Regex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

public class RegexOfAddress_init_version {

    public static Connection conn=getConnection();;
    public static Statement stat;
    public static ResultSet rs;
    public static Writer  writerA;// 数字型
    public static Writer  writerB;//识别
    public static Writer  writerC;//  741号-2
    public static Writer  writerD;//  2898弄43 ->
    public static Writer  writerE;//  207-1 ->
    public static Writer  writerF;//  119弄128支弄3
    public static Writer  writerG;//  1036临
    public static Writer  writerH;//   正规弄
    public static Writer  writer00;//  余量
    public static Writer  writerZ0;//I农

    static {
        try {
            writerA = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\纯数字型-门牌号.txt"));
            writerB = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\识别-门牌号.txt"));
            writerC = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\规范号-门牌号.txt"));

            writerD = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\规范弄号-门牌号.txt"));

            writerE = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\规范数字型-门牌号.txt"));

            writerF = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\规范支弄型-门牌号.txt"));

            writerG = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\规范临型-门牌号.txt"));

            writerH = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\规范弄号型-门牌号.txt"));

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
            writerB.close();
            writerC.close();
            writerD.close();
            writerE.close();
            writerF.close();
            writerG.close();
            writerH.close();
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

                        writerA.append(Building+"\t"+Building+"号"+"\r\n");

                    }else if (Pattern.matches(".+识别$", Building)){       //55识别       //后面 加号

                        writerB.append(Building+"\t"+Building+"号"+"\r\n");
                        writerB.flush();

                    }else if (Pattern.matches(".+农.+", Building)){/////////////////////////////////////未处理
                        String reBuilding=Building.replace('农','弄');  //673农1-16号       //农改成弄
                        writerZ0.append(reBuilding+"\r\n");

                    }else if (Pattern.matches("\\d+号-\\d+", Building)){             //741号-2    //这种统一处理成741-2号
                        String reBuilding=Building.replace("号","");
                        writerC.append(Building+"\t"+reBuilding+"号"+"\r\n");
                        writerC.flush();

                    }else if(Pattern.matches("\\d+弄\\d+", Building)){   //2898弄43 ->2898弄34号

                        writerD.append(Building+"\t"+Building+"号"+"\r\n");
                        writerD.flush();

                    }else if (Pattern.matches("\\d+-\\d+", Building)){             //207-1  ->207-1号

                        writerE.append(Building+"\t"+Building+"号"+"\r\n");
                        writerE.flush();

                    }else if (Pattern.matches("\\d+弄\\d+支弄\\d+", Building)){     //119弄128支弄16

                        writerF.append(Building+"\t"+Building+"号"+"\r\n");
                        writerF.flush();
                    }else if (Pattern.matches("\\d+临", Building)){                  //599临    后加号

                        writerG.append(Building+"\t"+Building+"号"+"\r\n");
                        writerG.flush();

                    }else if (Pattern.matches("\\d+[弄号]", Building)){

                        writerH.append(Building+"\r\n");
                        writerH.flush();
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
