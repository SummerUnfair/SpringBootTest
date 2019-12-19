package Regex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Regex_Address_Test {

    public static Connection conn=getConnection();;
    public static Statement stat;
    public static ResultSet rs;
    public static Set       set=new  HashSet();
    public static Writer writer;
    public static Writer writer1;

    static {
        try {
            writer = new FileWriter(new File("C:\\Users\\Hasee\\Desktop\\initAddress\\规范地址类型.txt"));
            writer1 = new FileWriter(new File("C:\\Users\\Hasee\\Desktop\\initAddress\\未处理地址.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main (String[] args)
    {
        try {
            getAddress();
            writer.close();
            writer1.close();
        }catch (Exception e){

        }
    }

    //规则化数据模块
    public static void getAddress_Ritht_Name(String Address_allName){
        try{    //123识别    207-1  123123   599临      1869-8临    26甲 515B
        if (Pattern.matches("([0-9]+\\w|[0-9]+[甲乙丙丁]|[0-9]+临|[0-9]+-[0-9]+临|[0-9]+识别|[0-9]+-[0-9]+|[0-9]+)", Address_allName)){
            writer.append(Address_allName+"号"+"\r\n");
            writer.flush();
            //123弄123-123   2898弄43     119弄128支弄16  119弄128支弄1-16
        }else if (Pattern.matches("([0-9]+弄[0-9]+支弄[0-9]+-[0-9]+|[0-9]+弄[0-9]+支弄[0-9]+|[0-9]+弄[0-9]+-[0-9]+|[0-9]+弄[0-9]+)", Address_allName)){
            writer.append(Address_allName+"号"+"\r\n");
//            int N0=Address_allName.indexOf("弄");
//            set.add(Address_allName.substring(0,N0+1));
//            set.add(Address_allName.substring(N0+1)+"号");
            writer.flush();
            //1460弄5号临  10-12号临  12号临   51弄32-1号临
        }else if (Pattern.matches("([0-9]+-[0-9]+|[0-9]+|[0-9]+弄[0-9]+|[0-9]+弄[0-9]+-[0-9]+)号临", Address_allName)){
            String reBuilding=Address_allName.replace("号临","");
            writer.append(reBuilding+"临号"+"\r\n");
            writer.flush();
            //15号-1临
        }else if(Pattern.matches("\\d+号-\\d+临", Address_allName)){
            String reBuilding=Address_allName.replace("号","");
            writer.append(reBuilding+"号"+"\r\n");
            writer.flush();
            //2501弄301号301室 1019弄42号后门 1019弄42号边门    9385号101室号  266号后门  590弄72支弄15号后门  388号210室
        }else if (Pattern.matches("([0-9]+弄[0-9]+号(后门|边门)|[0-9]+号[0-9]+室号|[0-9]+号后门|[0-9]+弄[0-9]+号[0-9]+室|[0-9]+弄[0-9]+支弄[0-9]+号后门|[0-9]+号[0-9]+室)", Address_allName)){
            int a =Address_allName.indexOf("号");
            String reBuilding=Address_allName.substring(0,a+1);
            writer.append(reBuilding+"\r\n");
            writer.flush();
            //5530弄129号-2   5297号-14
        }else if (Pattern.matches("([0-9]+弄[0-9]+号-[0-9]+|\\d+号-\\d+)", Address_allName)){
            String reBuilding=Address_allName.replace("号","");
            writer.append(reBuilding+"号"+"\r\n");
            writer.flush();
            //158弄7号甲
        }else if (Pattern.matches("[0-9]+弄[0-9]+号甲", Address_allName)){
            String reBuilding=Address_allName.replace("号甲","");
            writer.append(reBuilding+"甲号"+"\r\n");
            writer.flush();
        }
            //正确数据与无法处理数据
        else{
            writer1.append(Address_allName+"\r\n");
            writer1.flush();
        }
        }catch (Exception e){

        }
    }
    //huoquData
    public static void getAddress(){
        int count=0;
        try{
            stat = conn.createStatement();
            rs=stat.executeQuery("select * from gp_city_address_1907 gp ");
            while(rs.next()){
                count++;
                String Building=null;
                Building=rs.getString("门牌号");
                getAddress_Ritht_Name(Building);

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
