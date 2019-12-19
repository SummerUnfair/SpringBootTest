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

public class Regex_Address_END1 {

    public static Connection conn=getConnection();;
    public static Statement stat;
    public static ResultSet rs;
    public static Set<String> set=new  HashSet<String>();
    public static Writer writer;
    public static Writer writer1;
    public static Writer writer2;
    public static Writer writer3;


    static {
        try {
            writer = new FileWriter(new File("C:\\Users\\Hasee\\Desktop\\initAddress\\规范地址类型.txt"));
            writer1 = new FileWriter(new File("C:\\Users\\Hasee\\Desktop\\initAddress\\无地址类型.txt"));
            writer2 = new FileWriter(new File("C:\\Users\\Hasee\\Desktop\\initAddress\\170类型.txt"));
            writer3 = new FileWriter(new File("C:\\Users\\Hasee\\Desktop\\initAddress\\170去重类型.txt"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main (String[] args) throws IOException {
        getAddress();

        for (String str : set) {
            writer3.append(str+"\r\n");
            writer3.flush();
        }
        writer.close();
        writer1.close();
        writer2.close();
        writer3.close();

    }
    public static void ENDING(String addressName) throws IOException {

        if (addressName.contains("弄")){

            Matcher matcher0= Pattern.compile(".*?(\\w+[-]?\\w*[临甲乙丙丁]?弄).*?").matcher(addressName);
            if (matcher0.find()){
                writer.append(matcher0.group(1)+"\r\n");
                writer.flush();
                writer2.append(matcher0.group(1)+"\t");
                set.add(matcher0.group(1));
            }

        }
        if (addressName.contains("弄")){

            Matcher matcher0= Pattern.compile(".*?([一二三四五六七八九]+[0-9]*?弄).*?").matcher(addressName);
            if (matcher0.find()){
                writer.append(matcher0.group(1)+"\r\n");
                writer.flush();
                writer2.append(matcher0.group(1)+"\t");
                set.add(matcher0.group(1));
            }

        }

        if (addressName.contains("支弄")){
            Matcher matcher0=Pattern.compile(".*?(\\w+[-]?\\w*[临甲乙丙丁]?支弄).*?").matcher(addressName);
            if (matcher0.find()){
                writer.append(matcher0.group(1)+"\r\n");
                writer.flush();
                writer2.append(matcher0.group(1)+"\t");
                set.add(matcher0.group(1));
            }

        }

        //一七00弄七十五支弄80
        if (addressName.contains("支弄")){
            Matcher matcher0=Pattern.compile(".*?([一二三四五六七八九]+支弄).*?").matcher(addressName);
            if (matcher0.find()){
                writer.append(matcher0.group(1)+"\r\n");
                writer.flush();
                writer2.append(matcher0.group(1)+"\t");
                set.add(matcher0.group(1));
            }

        }

        if(addressName.contains("号")) {
            if (Pattern.matches("(\\d[甲乙丙丁临]?号)",addressName)){

                Matcher matcher0= Pattern.compile("(\\d[甲乙丙丁临]?号)").matcher(addressName);
                if (matcher0.find()){
                    writer.append(matcher0.group(1)+"\r\n");
                    writer.flush();
                    writer2.append(matcher0.group(1)+"\t");
                    set.add(matcher0.group(1));
                }
            }
        }
        /////////
        if(addressName.contains("组")) {
            if (Pattern.matches("([一二三四五六七八九]+组)(\\w+号)",addressName)){

                Matcher matcher0= Pattern.compile("([一二三四五六七八九]+组)(\\w+号)").matcher(addressName);
                if (matcher0.find()){
                    writer.append(matcher0.group(1)+"\r\n");
                    writer.append(matcher0.group(2)+"\r\n");
                    writer.flush();
                    writer2.append(matcher0.group(1)+"\t");
                    writer2.append(matcher0.group(2)+"\t");
                    set.add(matcher0.group(1));
                    set.add(matcher0.group(2));

                }
            }
        }
        ///////////
        if(addressName.contains("号")) {
            if (Pattern.matches("\\w+弄(\\d[甲乙丙丁临]?号)",addressName)){

                Matcher matcher0= Pattern.compile("\\w+弄(\\d[甲乙丙丁临]?号)").matcher(addressName);
                if (matcher0.find()){
                    writer.append(matcher0.group(1)+"\r\n");
                    writer.flush();
                    writer2.append(matcher0.group(1)+"\t");
                    set.add(matcher0.group(1));
                }
            }
        }
        //识别
        if(addressName.contains("号")) {
            if (Pattern.matches("(\\d+识别号)",addressName)){

                Matcher matcher0= Pattern.compile("(\\d+识别号)").matcher(addressName);
                if (matcher0.find()){
                    writer.append(matcher0.group(1)+"\r\n");
                    writer.flush();
                    writer2.append(matcher0.group(1)+"\t");
                    set.add(matcher0.group(1));
                }
            }
        }
        if(addressName.contains("号")) {
                    Matcher matcher0 = Pattern.compile(".*?(\\w+[-]?\\w+[临甲乙丙丁楼]?号).*?").matcher(addressName);
                    if (matcher0.find()) {
                        writer.append(matcher0.group(1)+"\r\n");
                        writer.flush();
                        writer2.append(matcher0.group(1)+"\t");
                        set.add(matcher0.group(1));
                    }
        }


                if (addressName.contains("栋弄")){
                    Matcher matcher0=Pattern.compile(".*?(\\w+[-]?\\w*[临甲乙丙丁]?栋弄).*?").matcher(addressName);
                    if (matcher0.find()){
                        writer.append(matcher0.group(1)+"\r\n");
                        writer.flush();
                        writer2.append(matcher0.group(1)+"\t");
                        set.add(matcher0.group(1));
                    }

                }
                //区     一二区   C区  一二C区
                if (addressName.contains("区")){
                    Matcher matcher0=Pattern.compile(".*?([一二三四五六七八九A-Za-z]+区).*?").matcher(addressName);
                    if (matcher0.find()){
                        writer.append(matcher0.group(1)+"\r\n");
                        writer.flush();
                        writer2.append(matcher0.group(1)+"\t");
                        set.add(matcher0.group(1));
                    }

                }
                if (addressName.contains("街")){
                    Matcher matcher0=Pattern.compile(".*?(第?[一二三四五六七八九A-Za-z]+街).*?").matcher(addressName);
                    if (matcher0.find()){
                        writer.append(matcher0.group(1)+"\r\n");
                        writer.flush();
                        writer2.append(matcher0.group(1)+"\t");
                        set.add(matcher0.group(1));
                    }

                }
                if (addressName.contains("幢")||addressName.contains("栋")||addressName.contains("座")||addressName.contains("单元")||addressName.contains("号楼")){
                    Matcher matcher0=Pattern.compile(".*?(\\w+[-]?\\w*[栋幢座]|单元|号楼)").matcher(addressName);
                    if (matcher0.find()){
                        writer.append(matcher0.group(1)+"\r\n");
                        writer.flush();
                        writer2.append(matcher0.group(1)+"\t");
                        set.add(matcher0.group(1));
                    }

                }
        writer2.append("\r\n");
        writer2.flush();
        String [] Name={"号","弄","支弄","栋弄","幢","区","街","栋","座","单元","组"};
                if (!addressName.contains("号")&&!addressName.contains("弄")&&!addressName.contains("支弄")
                                               &&!addressName.contains("栋弄")&&!addressName.contains("幢")
                                               &&!addressName.contains("区")&&!addressName.contains("街")
                                               &&!addressName.contains("栋")&&!addressName.contains("座")
                                                &&!addressName.contains("单元")&&!addressName.contains("号楼")
                                                &&!addressName.contains("组")){
                    writer1.append(addressName+"\r\n");
                    writer1.flush();

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
        //4弄4支弄4幢4-4    55弄识别   86-1识别  373-A3  12A楼
        else if(Pattern.matches("(\\w+弄\\w+支弄\\w+幢\\w+-\\w+|\\w+-\\w+识别|\\w+弄识别|\\w+-\\w+|\\w+楼)", Address_allName)){
            return Address_allName+"号";
        }
        //1683临-1
        else if(Pattern.matches("\\w+临-\\w+临?", Address_allName)){
            String reBuilding=Address_allName.replace("临","");

            return reBuilding+"临号";

        }

        //1460弄5号临  10-12号临  12号临   51弄32-1号临
        else if (Pattern.matches("([0-9]+-[0-9]+|[0-9]+|[0-9]+弄[0-9]+|[0-9]+弄[0-9]+-[0-9]+)号临", Address_allName)){
            String reBuilding=Address_allName.replace("号临","");
            return reBuilding+"临号";

            //15号-1临    184号识别  2360弄63号-1临
        }else if(Pattern.matches("(\\w+号-\\w+临|\\w+号识别|\\w+弄\\w+号-\\w+临)", Address_allName)){
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
        int count=0;
        try{
            stat = conn.createStatement();
            rs=stat.executeQuery("select * from gp_city_address_1907 gp ");
            while(rs.next()){
                count++;
                String Building=null;
                Building=rs.getString("门牌号");
                writer2.append(Building+"\t");
                String Address_AllName=getAddress_Ritht_Name(Building);

                ENDING(Address_AllName);

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
