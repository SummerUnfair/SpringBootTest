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

public class RegexOfAddress_end_version {

    public static Connection conn=getConnection();;
    public static Statement stat;
    public static ResultSet rs;
    public static Writer  writerA;// 数字型    //  2898弄43 -> 号       207-1 ->号
    public static Writer  writerH;//   正规弄
    public static Writer  writer00;//  余量

    static {
        try {
            writerA = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\规范地址类型.txt"));
            writerH = new FileWriter(new  File("C:\\Users\\Hasee\\Desktop\\initAddress\\规范弄号型-门牌号.txt"));
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
            writerH.close();
            writer00.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    private static void RegexAddressName() {

        int count=0;

        try{
            stat = conn.createStatement();
            rs=stat.executeQuery("select * from gp_city_address_1907 gp ");
            while(rs.next()){
                count++;
                String Building=null;
                Building=rs.getString("门牌号");
                if (Building==null){continue;};
                    //boolean isInt = Pattern.matches("\\d+", Building);
                    if (Pattern.matches("([0-9]+识别|[0-9]+弄[0-9]+-[0-9]+|[0-9]+)", Building)){ //123123   123弄123-123   123识别

                        writerA.append(Building+"\t"+Building+"号"+"\r\n");

                    } else if(Pattern.matches("\\d+[-弄]\\d+", Building)){   //2898弄43   207-1

                        writerA.append(Building+"\t"+Building+"号"+"\r\n");
                        writerA.flush();

                    }else if (Pattern.matches("\\d+弄\\d+支弄\\d+", Building)){   //119弄128支弄16

                        writerA.append(Building+"\t"+Building+"号"+"\r\n");
                        writerA.flush();

                        ////////////////////////////////////////////////////////////////
                    }else if (Pattern.matches("([0-9]+|[0-9]+-[0-9]+)临", Building)){   //599临      1869-8临

                        writerA.append(Building+"\t"+Building+"号"+"\r\n");
                        writerA.flush();
//////////////////////////////////////////////////////////////
                    }else if (Pattern.matches("([0-9]+-[0-9]+|[0-9]+|[0-9]+弄[0-9]+)号临", Building)){  //1460弄5号临  10-12号临  12号临
                        writerA.append(Building+"\t");
                        String reBuilding=Building.replace("号临","");

                        writerA.append(reBuilding+"临号"+"\r\n");
                        writerA.flush();
                        ///////////////////////////
                    }else if (Pattern.matches("\\d+号-\\d+临*", Building)){  // 15号-1临   5297号-14
                        writerA.append(Building+"\t");
                        String reBuilding=Building.replace("号","");

                        writerA.append(reBuilding+"号"+"\r\n");
                        writerA.flush();
                        //////////////////
                    }else if (Pattern.matches("\\d+弄\\d+号\\d+(室号|室)", Building)){  //2501弄301号301室
                        writerA.append(Building+"\t");
                        int a =Building.indexOf("号");
                        String reBuilding=Building.substring(0,a+1);

                        writerA.append(reBuilding+"\r\n");
                        writerA.flush();
                        //////////////////1019弄42号后门 边门    9385号101室号  266号后门
                    }else if (Pattern.matches("(\\d+弄)*\\d+号(后门|边门|[0-9]+室号)", Building)){
                        writerA.append(Building+"\t");
                        int a =Building.indexOf("号");
                        String reBuilding=Building.substring(0,a+1);

                        writerA.append(reBuilding+"\r\n");
                        writerA.flush();
                    }
////////////////////////////正确数据//////////////////////////////////////////////////
                    else if (Pattern.matches("\\d+(支弄[0-9]+号|[弄号]|[甲乙丙丁]号|识别号)", Building)){

                        writerH.append(Building+"\r\n");                                        //123号  123弄   17支弄3号   1002甲号  123识别号
                        writerH.flush();
                    }else if (Pattern.matches("\\d+弄\\d+号", Building)){
                        writerH.append(Building+"\r\n");                                       //123弄123号
                        writerH.flush();
                    }else if (Pattern.matches("[0-9]+弄[0-9]+(幢|支弄)[0-9]+号", Building)){        //2165弄1幢120号   2465弄30支弄36号
                        writerH.append(Building+"\r\n");
                        writerH.flush();
                    }else if (Pattern.matches("\\d+-\\d+号", Building)){
                        writerH.append(Building+"\r\n");
                        writerH.flush();
                    }else if(Pattern.matches("\\d+(幢|弄)(\\d+|[0-9]+-[0-9]+)号", Building)){      //59幢61-64号  59幢61号   8889弄55-48号
                        writerH.append(Building+"\r\n");
                        writerH.flush();
                    }else if(Pattern.matches("\\d+弄\\d+-\\d号", Building)){      //38弄4-1号
                        writerH.append(Building+"\r\n");
                        writerH.flush();

                    }
//////////////////////////////////////////////////////////////////////////////
                    else{
                        writer00.append(Building+"\r\n");
                        writer00.flush();
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
