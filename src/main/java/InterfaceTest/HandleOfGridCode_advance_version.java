package InterfaceTest;

import Util.HttpClientUtil;
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
public class HandleOfGridCode_advance_version {

    static String  urlIP="http://10.7.74.41/";//正式库
    //static String  urlIP="http://10.145.206.21:8089/";//测试库
    //static String  urlIP="http://10.6.246.32:8080/";//本地tomcat库

    static Writer writer;
    static Connection conn= getConnection();
    static PreparedStatement ps;

    static {
        try {
            ps = conn.prepareStatement("select GRIDCODE from address_grid where address_id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, SQLException {

        String  path1=  args[0];
        String path2=args[1];

        smartMatchAddress(path1,path2);

        writer.close();
    }

    public static void smartMatchAddress(String path1,String path2) throws IOException, SQLException {

        Reader in = new FileReader(new File(path1));
        BufferedReader reader = new BufferedReader(in);
        String s = "";
        writer = new FileWriter(path2);

        while((s=reader.readLine())!=null) {

            if(s.trim().length() == 0)   continue;
            writer.append(s+"\t");
            String sss=s.replaceAll(" ", "");
            String old_addr_full = sss.replace("\t","");

            try {

                getAddressName(old_addr_full);
                writer.append("\r\n");

            }catch (Exception e){

                writer.append("getAddressName方法调用失败!!"+"\r\n");

            }
        }

    }

    public static void getAddressName(String old_addr_full) throws IOException, SQLException {

        String url=urlIP+"addrselection/addressIndex/smartMatchAddress.do?addr_full="+old_addr_full;

        String jsonRes = null;
        Object addressId=null;
        Object addr_full=null;

        try {
            jsonRes = HttpClientUtil.get(url);

        } catch (Exception e) {

            writer.append("jsonRes获取失败!!"+"\r\n");

        }

        if (jsonRes.contains("addr_full")) {

            //常见的java代码转换成json
            JSONObject resJson = JSONObject.fromObject(jsonRes);

            JSONArray tagsArray = resJson.getJSONArray("data");
            for(Object obj : tagsArray){

                JSONObject dataRes = JSONObject.fromObject(obj);
                addressId=dataRes.get("id");
                addr_full=dataRes.get("addr_full");
                writer.append(addressId+"\t"+addr_full+"\t");
                String GRIDCODE=gridCode(addressId);

                if(GRIDCODE!=null){

                    writer.append(GRIDCODE + "\t");
                }
                else {
                    writer.append(""+"\t");
                }

            }
        }else{

            writer.append("未监测到json中存在addr_full键!!");
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

    private static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@10.7.100.15:1521:gis", "GIS", "4WY_Md1BE28");
            conn.setAutoCommit(false);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return conn;
        }
    }

}

