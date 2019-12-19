package InterfaceTest;

import Util.HttpClientUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class HandleOfSubType_Ending {

    static Connection conn= getConnection();
    static Statement st;

    public static Writer writer1;
    public static String  urlIP;
    public static String initial_FlieName;

    public static void main(String[] args){

        try {
            Properties prop = new Properties();
            InputStream inStream = HandleOfLog_end_version.class.getClassLoader().getResourceAsStream("HandleOfLogR.properties");
            prop.load(inStream);
            initial_FlieName=(String)prop.get("initial_FlieName");
            urlIP=(String)prop.get("urlIP");
            writer1 = new FileWriter(new File(initial_FlieName+"fourInfoText.txt"));

            get_AddressInfo();
            writer1.close();

        } catch(IOException e){
            System.out.println(e);
        }
    }

    public static void get_AddressInfo(){

        try {
            st = conn.createStatement();
            int count = 0;
            ResultSet rs = st.executeQuery("select xa.id,xa.addr_full,xa.subtype from x_address_info xa where xa.subtype >4270 and xa.subtype < 4275  and xa.id between -2000000 and  -74 ");

            while (rs.next()) {
                count++;
                String id = rs.getString("id");
                String addr_full = rs.getString("addr_full");
                String subtype = rs.getString("subtype");

                if (count % 5000 == 0) {
                    System.out.println("5000..");
                }

                try {
                    writer1.append(id+ "|@|"+addr_full + "|@|");
                    getMatchData(addr_full, subtype);
                    writer1.append("\r\n");

                } catch (NullPointerException e) {
                    System.out.println("getMatchData:" + e);
                } catch (IOException e) {
                    System.out.println("getMatchData:" + e);
                }

            }
        }catch (SQLException e){
            System.out.println("conn.createStatement():"+e);
        }

    }

    public static void getMatchData (String addressName,String addressSubtype ){

        String url=urlIP+"AddressSearchController/getAddrGrad.do?addr_full="+addressName;
        String jsonRes = null;
        Object level=null;
        Object addrTerm=null;

        int count =0;
        int len=0;
        try {
            //long startTime=System.currentTimeMillis();
            jsonRes = HttpClientUtil.get(url);
            //long endTime0=System.currentTimeMillis();
            //long S=endTime0-startTime;
            //System.out.println("调用时间："+S+"毫秒");
        }catch (Exception e){
            System.out.println("HttpClientUtil:"+e);
        }

        try{
            if (jsonRes.contains("addrTerm")) {

                len = jsonRes.split("addrTerm").length - 1;

                JSONObject resJson = JSONObject.fromObject(jsonRes);
                JSONArray ja = resJson.getJSONArray("result");
                int type = -1;
                String adr = null;
                String number = "4269";
                for (Object obj : ja) {
                    count++;

                    JSONObject dataRes = JSONObject.fromObject(obj);
                    level = dataRes.get("level");
                    addrTerm = dataRes.get("addrTerm");

                    String types = level + "";
                    if (types.equals(number)) {

                        adr = addrTerm + "";
                        type = 1;

                    }

                    if (len == count) {

                        String levels = level + "";

                        String adrTypeName = null;

                        if (type == 1) {
                            adrTypeName = adr + "";
                            type = -1;
                        } else {
                            adrTypeName = adr + "";
                        }

                        if (addressSubtype.equals(levels)) {

                            writer1.append(level + "|@|" + addressSubtype + "|@|" + "相同" + "|@|" + adrTypeName);

                            count = 0;
                            return;
                        } else {
                            writer1.append(level + "|@|" + addressSubtype + "|@|" + "不相同" + "|@|" + adrTypeName);

                            count = 0;
                            return;
                        }
                    }
                }
            } else {
                writer1.append("无调用结果");
            }
        }catch (IOException e){
            System.out.println("writer1:"+e);
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
            System.out.println(e);
            return conn;
        }
    }
}
