package com.InterfaceTest;

import com.Util.HttpClientUtil;
import com.Util.JdbcUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.sql.*;

/**
 * Information sources：地址文本
 * Output source：文本
 * function:校验信息是否匹配,获取网格编码
 * Created by FH on 20190910.
 */
public class HandleOfGridCode_initial_version {

    static String  urlIP="http://10.7.74.41/";//正式库
    //static String  urlIP="http://10.145.206.21:8089/";//测试库
    //static String  urlIP="http://10.6.246.32:8080/";//本地tomcat库
    static Writer writer;
    static Connection conn = JdbcUtil.getTestConnection();

    static PreparedStatement   ps;

    static {
        try {
            ps = conn.prepareStatement("select GRIDCODE from address_grid where address_id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            writer = new FileWriter("C:\\Users\\user\\Desktop\\DealWithAddress20190903.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException, SQLException {

        String  path1="C:\\Users\\user\\Desktop\\222.txt";

        smartMatchAddress(path1);

        writer.close();
    }

    //读入文本
    private static void smartMatchAddress(String path1) throws IOException, SQLException {

        Reader in = new FileReader(new File(path1));
        BufferedReader reader = new BufferedReader(in);
        String old_addr_full = "";

        while((old_addr_full=reader.readLine())!=null) {

            try {

                    if(old_addr_full.trim().length() == 0)   continue;

                    long startTime=System.currentTimeMillis();
                    getAddressName(old_addr_full);
                    long endTime0=System.currentTimeMillis();
                    long S=endTime0-startTime;
                    writer.append(S+"毫秒");
                    writer.append("\r\n");

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    //获取接口信息
    public static void  getAddressName(String old_addr_full) throws IOException, SQLException {

        writer.append(old_addr_full+"--");

        String url=urlIP+"addrselection/addressIndex/smartMatchAddress.do?addr_full="+old_addr_full;

        String jsonRes = null;
        Object addressId=null;
        Object addr_full=null;
        try {

            jsonRes = HttpClientUtil.get(url);

        } catch (Exception e) {

            writer.append("jsonRes获取失败!!"+"\r\n");

        }

        if (jsonRes.contains("location")) {

            //常见的java代码转换成json
            JSONObject resJson = JSONObject.fromObject(jsonRes);

            JSONArray tagsArray = resJson.getJSONArray("data");

            for(Object obj : tagsArray){

                JSONObject dataRes = JSONObject.fromObject(obj);
                addressId=dataRes.get("id");
                addr_full=dataRes.get("addr_full");
                writer.append(addressId+"--"+addr_full+"...");
                String GRIDCODE=gridCode(addressId);
                writer.append(GRIDCODE + "--");

            }

        }else {

            writer.append("未监测到json中存在location键!!"+"\r\n");

        }

    }

    public static String  gridCode(Object addressId)  throws SQLException, IOException{

        String address_id=addressId.toString();
        String GRIDCODE=null;

        ps.setString(1,address_id);
        ResultSet rs=ps.executeQuery();

        if(rs.next()) {

           GRIDCODE=rs.getString("GRIDCODE");

        }
        ps.close();
        return GRIDCODE;

    }

}
