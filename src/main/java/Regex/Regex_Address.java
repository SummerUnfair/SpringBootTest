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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex_Address {

    public static Connection conn=getConnection();;
    public static Statement stat;
    public static ResultSet rs;
    public static Set       set=new  HashSet();
    public static Writer writer;

    static {
        try {
            writer = new FileWriter(new File("C:\\Users\\Hasee\\Desktop\\initAddress\\规范地址类型.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main (String[] args)
    {

    }

    public static void Regex_Address(String Address_allName){

        //处理标准识别号
        if (Pattern.matches("(\\w+识别号)", Address_allName)){

            Matcher matcher0=Pattern.compile("(\\w+识别号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
            }
        }
        //处理标准弄 12弄     12a弄  12A号
        else if (Pattern.matches("(\\w+弄)", Address_allName)){

            Matcher matcher0=Pattern.compile("(\\w+弄)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
            }
        //处理标准号  12号  12a号
        }else if (Pattern.matches("(\\w+号)", Address_allName)){

            Matcher matcher0=Pattern.compile("(\\w+号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
            }
        //处理标准甲号    12甲号
        }else if (Pattern.matches("(\\w+甲号)", Address_allName)){

            Matcher matcher0=Pattern.compile("(\\w+甲号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
            }
        }
        //处理标准12-12号
        else if (Pattern.matches("(\\w+-\\w+号)", Address_allName)){

            Matcher matcher0=Pattern.compile("(\\w+-\\w+号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
            }
        }
        //处理标准临号    12临号
        else if (Pattern.matches("(\\w+临号)", Address_allName)){

            Matcher matcher0=Pattern.compile("(\\w+临号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
            }
        }
        //处理标准-临号  12-12临号
        else if (Pattern.matches("(\\w+-\\w+临号)", Address_allName)){

            Matcher matcher0=Pattern.compile("(\\w+-\\w+临号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
            }
        }
        //处理标准弄临号  12弄12临号
        else if (Pattern.matches("(\\w+弄)(\\w+临号)", Address_allName)){

            Matcher matcher0=Pattern.compile("(\\w+弄)(\\w+临号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
            }
        }
        //处理标准弄-临号  12弄1-2临号
        else if (Pattern.matches("(\\w+弄)(\\w+-\\w+临号)", Address_allName)){

            Matcher matcher0=Pattern.compile("(\\w+弄)(\\w+-\\w+临号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
            }
        }
        //处理标准弄号    12弄12号  12a弄12a号
        else if (Pattern.matches("(\\w+弄)(\\w+号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+弄)(\\w+号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
            }
        }
        //处理标准弄甲号    12弄12甲号  12a弄12a甲号
        else if (Pattern.matches("(\\w+弄)(\\w+甲号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+弄)(\\w+甲号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
            }
        }
        //处理标准弄12-12号    12弄12-12号  12a弄12a-12号
        else if (Pattern.matches("(\\w+弄)(\\w+-\\w+号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+弄)(\\w+-\\w+号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
            }
        }
        //处理标准弄支弄号    12弄12支弄12号
        else if (Pattern.matches("(\\w+弄)(\\w+支弄)(\\w+号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+弄)(\\w+支弄)(\\w+号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
                set.add(matcher0.group(3));
            }
        }
        //处理标准弄支弄     12弄12支弄   12a弄12a支弄
        else if (Pattern.matches("(\\w+弄)(\\w+支弄)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+弄)(\\w+支弄)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
            }
        }

        //处理标准支弄    12支弄12号  12a支弄12a号
        else if (Pattern.matches("(\\w+支弄)(\\w+号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+支弄)(\\w+号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
            }
        }
        //处理标准号幢    12号12幢  12a号12a幢
        else if (Pattern.matches("(\\w+号)(\\w+幢)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+号)(\\w+幢)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
            }
        }
        //处理标准弄支弄1-2号    12弄12支弄1-2号
        else if (Pattern.matches("(\\w+弄)(\\w+支弄)(\\w+-\\w+号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+弄)(\\w+支弄)(\\w+-\\w+号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
                set.add(matcher0.group(3));
            }
        }
        //处理标准幢    12幢
        else if (Pattern.matches("(\\w+幢)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+幢)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
            }
        }
        //处理标准幢号   12幢12号
        else if (Pattern.matches("(\\w+幢)(\\w+号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+幢)(\\w+号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
            }
        }
        //处理标准弄临号   4弄4临号
        else if (Pattern.matches("(\\w+弄)(\\w+临号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+弄)(\\w+临号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
            }
        }
        //处理标准弄临号   4弄4-4临号
        else if (Pattern.matches("(\\w+弄)(\\w+-\\w+临号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+弄)(\\w+-\\w+临号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
            }
        }
        //处理标准幢号   4幢4-4号
        else if (Pattern.matches("(\\w+幢)(\\w+-\\w+号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+幢)(\\w+-\\w+号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
            }
        }
        //处理标准弄幢号   4弄4幢4号
        else if (Pattern.matches("(\\w+弄)(\\w+幢)(\\w+号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+弄)(\\w+幢)(\\w+号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
                set.add(matcher0.group(3));
            }
        }
        //处理标准弄幢号   4弄4幢4-4号
        else if (Pattern.matches("(\\w+弄)(\\w+幢)(\\w+-\\w+号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+弄)(\\w+幢)(\\w+-\\w+号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
                set.add(matcher0.group(3));
            }
        }
        //处理标准- - 号   330-1-29号
        else if (Pattern.matches("(\\w+-\\w+-\\w+号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+-\\w+-\\w+号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
            }
        }
        //处理标准弄支弄临号    4弄4支弄4临
        else if (Pattern.matches("(\\w+弄)(\\w+支弄)(\\w+临号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+弄)(\\w+支弄)(\\w+临号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
                set.add(matcher0.group(3));
            }
        }
        //处理标准弄支弄 - 临号 4弄4支弄4-4临
        else if (Pattern.matches("(\\w+弄)(\\w+支弄)(\\w+-\\w+临号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+弄)(\\w+支弄)(\\w+-\\w+临号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
                set.add(matcher0.group(3));
            }
        }

        //处理标准弄支弄幢 -    4弄4支弄4幢4-4
        else if (Pattern.matches("(\\w+弄)(\\w+支弄)(\\w+幢)(\\w+-\\w+号)", Address_allName)){

            Matcher matcher0 =Pattern.compile("(\\w+弄)(\\w+支弄)(\\w+幢)(\\w+-\\w+号)").matcher(Address_allName);
            if (matcher0.find()){
                set.add(matcher0.group(1));
                set.add(matcher0.group(2));
                set.add(matcher0.group(3));
                set.add(matcher0.group(4));
            }
        }


    }



    //规则化数据模块
    public static String getAddress_Ritht_Name(String Address_allName){
            //123识别    207-1  123123   599临      1869-8临    26甲 515B    4弄4-4临  4弄4临
        if (Pattern.matches("([0-9]+弄[0-9]+临|[0-9]+弄[0-9]+-[0-9]+临|[0-9]+\\w|[0-9]+[甲乙丙丁]|[0-9]+临|[0-9]+-[0-9]+临|[0-9]+识别|[0-9]+-[0-9]+|[0-9]+)", Address_allName)){
            return Address_allName+"号";
            //123弄123-123   2898弄43     119弄128支弄16  119弄128支弄1-16  4弄4支弄4临 4弄4支弄4-4临
        }else if (Pattern.matches("(\\w+弄\\w+支弄\\w+-\\w+临|\\w+弄\\w+支弄\\w+临|[0-9]+弄[0-9]+支弄[0-9]+-[0-9]+|[0-9]+弄[0-9]+支弄[0-9]+|[0-9]+弄[0-9]+-[0-9]+|[0-9]+弄[0-9]+)", Address_allName)){
            return Address_allName+"号";

        }
        //4弄4支弄4幢4-4
        else if(Pattern.matches("\\w+弄\\w+支弄\\w+幢\\w+-\\w+", Address_allName)){

            return Address_allName+"号";

        }
        //1460弄5号临  10-12号临  12号临   51弄32-1号临
        else if (Pattern.matches("([0-9]+-[0-9]+|[0-9]+|[0-9]+弄[0-9]+|[0-9]+弄[0-9]+-[0-9]+)号临", Address_allName)){
            String reBuilding=Address_allName.replace("号临","");
            return reBuilding+"临号";

            //15号-1临    184号识别
        }else if(Pattern.matches("(\\w+号-\\w+临|\\w+号识别)", Address_allName)){
            String reBuilding=Address_allName.replace("号","");
            return reBuilding+"号";

            //2501弄301号301室 1019弄42号后门 1019弄42号边门    9385号101室号  266号后门  590弄72支弄15号后门  388号210室
        }else if (Pattern.matches("([0-9]+弄[0-9]+号(后门|边门)|[0-9]+号[0-9]+室号|[0-9]+号后门|[0-9]+弄[0-9]+号[0-9]+室|[0-9]+弄[0-9]+支弄[0-9]+号后门|[0-9]+号[0-9]+室)", Address_allName)){
            int a =Address_allName.indexOf("号");
            String reBuilding=Address_allName.substring(0,a+1);
            return reBuilding;
            //5530弄129号-2   5297号-14
        }else if (Pattern.matches("([0-9]+弄[0-9]+号-[0-9]+|\\d+号-\\d+)", Address_allName)){
            String reBuilding=Address_allName.replace("号","");
            return reBuilding+"号";

            //158弄7号甲
        }else if (Pattern.matches("([0-9]+弄[0-9]+号甲|\\w+号甲)", Address_allName)){
            String reBuilding=Address_allName.replace("号甲","");
            return reBuilding+"甲号";
        }
            //正确数据与无法处理数据
        else{
            return Address_allName;
        }
    }
    //huoquData
    public static void getAddress(){
        try{
            stat = conn.createStatement();
            rs=stat.executeQuery("select * from gp_city_address_1907 gp ");
            while(rs.next()){
                String Building=null;
                Building=rs.getString("门牌号");
                String Address_AllName=getAddress_Ritht_Name(Building);
                Regex_Address(Address_AllName);
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
