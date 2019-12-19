package InterfaceTest;

import Util.HttpClientUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Output source：文本
 *
 * The test interface：模糊匹配 查找下级地址 根据流水号查找地址 插入地址信息
 * http://10.145.209.143:8062/gis/giscenter/AddressSearch/AddressSearchController/queryAddrsByKey.do?address_name=柳林
 *
 * http://10.145.209.143:8062/gis/giscenter/AddressSearch/AddressSearchController/getAddrByParentId.do?parent_address_id=44882270
 *
 * http://10.145.209.143:8062/gis/giscenter/AddressSearch/AddressSearchController/queryAddrsBySerialNum.do?serial=10100num1
 *
 * http://10.145.209.143:8062/gis/giscenter/AddressSearch/AddressSearchController/addAddrsInfo.do?serial=10101num1&address_id=123456&address=123&main_bureau=eee&sub_bureau=1234
 *
 * function:ApiTest
 * Created by FH on 20190910.
 */
public class HandleOfApiTest_one_version {

    public static Writer writer1;
    public static Writer writer2;
    public static Writer writer3;
    public static Writer writer4;

//    public static String user="GIS";
//    public static String password="M3fXy_TFaUt";
//    public static String url="jdbc:oracle:thin:@10.145.206.20:1521:gis";
//    public static String  urlIP="http://10.145.209.143:8062/gis/";//21环境
//    public static String initial_FlieName="C:\\Users\\user\\Desktop\\";

    public static String user="GIS";
    public static String password="4WY_Md1BE28";
    public static String url="jdbc:oracle:thin:@10.7.100.15:1521:gis";
    static String  urlIP="http://10.145.131.213:8058/gis/";//生产环境
    public static String initial_FlieName="C:\\Users\\gisadm\\Desktop\\";

    public static Connection  conn= getConnection();
    public static PreparedStatement ps;

    static {
        try {
            writer1 = new FileWriter(initial_FlieName+"地址智能匹配表.txt");
            writer2 = new FileWriter(initial_FlieName+"选址信息获取表.txt");
            writer3 = new FileWriter(initial_FlieName+"父级ID查找子级地址表.txt");
            writer4 = new FileWriter(initial_FlieName+"插入地址信息耗时表.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        //模糊匹配
        MatchData();

        //根据流水号查找地址
        locationInformation();

        //查找下级地址
        subLevel();

        //插入地址信息
        addInformation();

        writer1.close();
        writer2.close();
        writer3.close();
        writer4.close();

    }

    public static void addInformation(){

        int count=0;
        for (int i=2401;i<2501;i++){

            String serial=i+"";

            int  address_id=i*2;

            String address=i+"";

            String main_bureau=i+"";

            String  sub_bureau=i+"";

            try {

                long startTime=System.currentTimeMillis();

                handleAddInformation(serial,address_id,address,main_bureau,sub_bureau);

                long endTime0=System.currentTimeMillis();
                long S=endTime0-startTime;
               count++;
                    writer4.append("当前已插入"+count+"条"+"\t"+S+"毫秒");
                    writer4.append("\r\n");

            } catch (SQLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static void handleAddInformation(String serial,int address_id,String address,String main_bureau,String  sub_bureau) throws SQLException, IOException {

        String url=urlIP+"giscenter/AddressSearch/AddressSearchController/addAddrsInfo.do?serial="+serial+"&address_id="+address_id+"&address="+address+"&main_bureau="+main_bureau+"&sub_bureau="+sub_bureau;

        try {
            HttpClientUtil.get(url);
        }catch (Exception e){

            writer4.append("\t"+"调用超时");

        }
//        String sql="insert into GIS_pipeline_info (SERIALNUM,ADDRESS_ID,ADDR_FULL,BRANCH,SUBBRANCH) values (?,?,?,?,?)";
//
//        ps=conn.prepareStatement(sql);
//
//        ps.setString(1,serial);
//        ps.setInt(2,address_id);
//        ps.setString(3,address);
//        ps.setString(4,main_bureau);
//        ps.setString(5,sub_bureau);
//
//        ps.executeUpdate();
//        ps.close();
//        conn.commit();

    }


    public static void subLevel() throws IOException {

        String  path1=initial_FlieName+"父级查子级.txt";
        Reader in = new FileReader(new File(path1));
        BufferedReader reader = new BufferedReader(in);
        String addressId = "";
        int count =0;
        //父级ID查找子级地址
        while((addressId=reader.readLine())!=null) {

            count++;
            if(addressId.trim().length()==0) continue;

            try {
                writer3.append("第"+count+"次"+"\t");

                long startTime=System.currentTimeMillis();

                handleSubLevel(addressId);

                long endTime0=System.currentTimeMillis();
                long S=endTime0-startTime;
                writer3.append("\t\t"+S+"毫秒");
                writer3.append("\r\n");
            }catch (IOException e) {
                e.printStackTrace();
                writer3.append(addressId+"\t"+"调用失败！！"+"\r\n");

            }
        }

    }

    public static void handleSubLevel(String addressId) throws IOException {

        writer3.append( "父级id:"+addressId+"\t");

        String url=urlIP+"giscenter/AddressSearch/AddressSearchController/getAddrByParentId.do?parent_address_id="+addressId;

        String jsonRes = null;

        Object PARENT_ADDRESS_ID=null;

        try {
            jsonRes = HttpClientUtil.get(url);
        }catch (Exception e){

            writer3.append(addressId+"\r\r"+"转换失败!!");
            writer3.append("\r\n");
        }

        if (jsonRes.contains("addrs")) {

            //常见的java代码转换成json
            JSONObject resJson = JSONObject.fromObject(jsonRes);

            JSONArray tagsArray = resJson.getJSONArray("addrs");
            for(Object obj : tagsArray){
                JSONObject dataRes = JSONObject.fromObject(obj);
                PARENT_ADDRESS_ID=dataRes.get("PARENT_ADDRESS_ID");

                writer3.append("\t\t"+PARENT_ADDRESS_ID);

            }
        }else {

            writer3.append("\t\t"+"调用成功，未查询到符合条件的地址数据!!");

        }

    }


    public static void locationInformation() throws IOException {

        //流水号
        List<String> lu=null;

        try {
            //创建sql命令对象,用于sql语句发送到数据库中
            ps=conn.prepareStatement("select * from GIS_pipeline_info");
            //执行sql
            ResultSet rs=ps.executeQuery();

            lu=new ArrayList<String>();
            while(rs.next()) {

                //将对象存储到集合中
                lu.add(rs.getString("SERIALNUM"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e){

            writer2.append("流水号为空");
            writer2.append("\r\n");

        }

        int count=0;

        for (String a : lu){

            count++;
            if(a==null||a.trim().length()==0)continue;
            //if(a.trim().length()==0)continue;
            try {

                writer2.append("第"+count+"次"+"\t");

                long startTime=System.currentTimeMillis();
                HandleLocationInformation(a);
                long endTime0=System.currentTimeMillis();
                long S=endTime0-startTime;
                writer2.append("\t\t"+S+"毫秒"+"\r\n");
            }catch (Exception e){

            writer2.append("流水号:"+a+"\r\r"+"调用失败！！");
            writer2.append("\r\n");
            }


        }
    }

    /**
     * 选址信息
     * @param a 流水号
     */
    public static void HandleLocationInformation(String a) throws IOException {

        writer2.append("流水号:"+a+"\t");

        String url=urlIP+"giscenter/AddressSearch/AddressSearchController/queryAddrsBySerialNum.do?serial="+a;

        String jsonRes = null;

        Object address_id=null;

        try {
            jsonRes = HttpClientUtil.get(url);
        }catch (Exception e){

            writer2.append(a+"\r\r"+"转换失败!!");
            writer2.append("\r\n");
        }

        if (jsonRes.contains("address_id")) {

            try {
                //常见的java代码转换成json
                JSONObject resJson = JSONObject.fromObject(jsonRes);

                JSONObject tagsObject= resJson.getJSONObject("address_id");

                address_id=(Object) tagsObject;

                writer2.append("--"+address_id);

            }catch (Exception e){

            }

            writer2.append("\t\t"+"调用成功！！");
        }else {

            writer2.append("\t\t"+"调用成功,返参不包含address_id!!");

        }

    }




    /**
     * 地址智能匹配
     * @throws IOException
     */
    public static void  MatchData() throws IOException {

        String  path1=initial_FlieName+"智能匹配.txt";
        Reader in = new FileReader(new File(path1));
        BufferedReader reader = new BufferedReader(in);
        String s = "";

        int count=0;
        //地址智能匹配
        while((s=reader.readLine())!=null) {

            count++;

            if(s.trim().length()==0) continue;

            String sss=s.replaceAll(" ", "");
            String addressName = sss.replace("\t","");

            try {

                writer1.append("第"+count+"次"+"\t");

                long startTime=System.currentTimeMillis();

                getMatchData(addressName);

                long endTime0=System.currentTimeMillis();
                long S=endTime0-startTime;
                writer1.append("\t"+S+"毫秒");
                writer1.append("\r\n");
            }catch (IOException e) {
                e.printStackTrace();
            }catch (NullPointerException e){
                writer1.append(addressName+"\r\r"+"此条调用失败！"+"\r\n");
            }
        }

    }


    public static void getMatchData (String addressName ) throws IOException {


        writer1.append("参数地址:"+addressName);

        String url=urlIP+"giscenter/AddressSearch/AddressSearchController/queryAddrsByKey.do?address_name="+addressName;

        String jsonRes = null;

        Object ADDR_FULL=null;

        try {
            jsonRes = HttpClientUtil.get(url);
        }catch (Exception e){

            writer1.append(addressName+"\r"+"转换失败!!");
            writer1.append("\t");

        }

        if (jsonRes.contains("ADDR_FULL")) {

            try {
                //常见的java代码转换成json
                JSONObject resJson = JSONObject.fromObject(jsonRes);
                JSONArray tagsArray = resJson.getJSONArray("addrs");

                for(Object obj : tagsArray){
                    JSONObject dataRes = JSONObject.fromObject(obj);
                    ADDR_FULL=dataRes.get("ADDR_FULL");

                    writer1.append("--"+ADDR_FULL);

                }
            }catch (Exception e){

                writer1.append("\t");
            }
            writer1.append("\t\t"+"调用成功");

        }else {

            writer1.append("\t\t"+"调用成功,未匹配到标准地址!!");

        }

    }

    private static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return conn;
        }
    }

}
