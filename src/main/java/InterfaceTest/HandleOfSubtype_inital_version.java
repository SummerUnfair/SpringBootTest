package InterfaceTest;

import Util.HttpClientUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class HandleOfSubtype_inital_version {

    static Connection  conn= getConnection();
    static Statement st;

    public static Writer writer1;
    public static String  urlIP;
    //public static String initial_FlieName="D:\\licl\\";
    public static String initial_FlieName;


    static {
        try {
            writer1 = new FileWriter(initial_FlieName+"AllInfoText.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        Properties prop = new Properties();
        InputStream inStream = HandleOfLog_end_version.class.getClassLoader().getResourceAsStream("HandleOfLogR.properties");
        prop.load(inStream);
        initial_FlieName=(String)prop.get("initial_FlieName");
        urlIP=(String)prop.get("urlIP");

        //模糊匹配
        //clear_Address();
        try {
            get_AddressInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        writer1.close();
    }

    public static void get_AddressInfo() throws SQLException, IOException {

        st=conn.createStatement();
        int count=0;
        ResultSet rs= st.executeQuery("select xa.addr_full,xa.subtype from x_address_info xa where xa.subtype >4270 and xa.subtype < 4275");

        while (rs.next()){
            count++;
           String addr_full= rs.getString("addr_full");
           String subtype=rs.getString("subtype");

            if (count%5000==0){
                System.out.println("5000..");
            }

            try {

                writer1.append(addr_full+"|@|");

                getMatchData(addr_full,subtype);

                writer1.append("\r\n");
            }catch (NullPointerException e){
                writer1.append("调用超时.."+"\r\n");
                //writer1.append("\r\n");
            }catch (IOException e) {
                //e.printStackTrace();
            }

        }



    }




    /**
     * clear
     * @throws IOException
     */
//    public static void  clear_Address() throws IOException {
//
//        String  path1=initial_FlieName+"街道.txt";
//        Reader in = new FileReader(new File(path1));
//        BufferedReader reader = new BufferedReader(in);
//
//        String s = "";
//
//        int count=0;
//        //地址智能匹配
//        while((s=reader.readLine())!=null) {
//
//            count++;
//
//            if(s.trim().length()==0) continue;
//
//            String sss=s.replaceAll(" ", "");
//            String addressNames = sss.replace("\t","");
//
//            String[] address =addressNames.split(",");
//
//            String addressName= address[0];
//            String addressSubtype=address[1];
//            if (count%1000==0){
//                System.out.println("1000..");
//            }
//
//            try {
//
//                writer1.append("number："+count+">>"+addressName+"\t");
//
//                getMatchData(addressName,addressSubtype);
//
//                writer1.append("\r\n");
//            }catch (NullPointerException e){
//                writer1.append("调用超时.."+"\r\n");
//                //writer1.append("\r\n");
//            }catch (IOException e) {
//                //e.printStackTrace();
//            }
//        }
//
//    }

    public static void getMatchData (String addressName,String addressSubtype ) throws IOException {

        String url=urlIP+"AddressSearch/AddressSearchController/getAddrGrad.do?addr_full="+addressName;

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

//            writer1.append(addressName+"\t"+"转换失败!!");
//            writer1.append("\t");

        }

        if (jsonRes.contains("addrTerm")) {

            len=jsonRes.split("addrTerm").length-1;
            //System.out.println("len:"+len);
            JSONObject resJson = JSONObject.fromObject(jsonRes);
            JSONArray ja=resJson.getJSONArray("result");
            int type=-1;
            String adr=null;
            String number="4269";
            for(Object obj : ja){
                count++;

                //System.out.println(count);
                JSONObject dataRes = JSONObject.fromObject(obj);
                level=dataRes.get("level");
                addrTerm=dataRes.get("addrTerm");

                String types=level+"";
                if (types.equals(number)){

                    adr=addrTerm+"";
                    type=1;

                }

                if(len==count){

                    String levels =level+"";

                    String adrTypeName=null;

                    if (type==1){
                         adrTypeName=adr+"";
                         type=-1;
                    }else{
                        adrTypeName=adr+"";
                    }


                    if (addressSubtype.equals(levels)){

                        writer1.append(level+"|@|"+addressSubtype+"|@|"+"相同"+"|@|"+adrTypeName);

                        count=0;
                        return;
                    }else{
                        writer1.append(level+"|@|"+addressSubtype+"|@|"+"不相同"+"|@|"+adrTypeName);

                        count=0;
                        return;

                    }

                }

            }
        }



        else {
            writer1.append("无调用结果");
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
