package com.ferao.InterfaceTest;

import com.ferao.Pojo.AddrInfo;
import com.ferao.Util.HttpClientUtil;
import com.ferao.Util.JdbcUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 核对URL返回数据
 */
public class I_CheckInfo_2 {

    private static Connection  conn;
    private static List<AddrInfo> list ;
    public static Writer writer1;
    public static String  urlIP;
    //public static String initial_FlieName="D:\\licl\\";
    public static String initial_FlieName;

    public static void main(String[] args) throws IOException {

        initConfig();
        System.out.println("load success!");
        get_AddressInfo();
        System.out.println("filed success!");
        call_Result();
        //模糊匹配
        //clear_Address();

        writer1.close();
    }

    /**
     * 初始化配置
     */
    public static void initConfig(){

        try {
            Properties prop = new Properties();
            InputStream inStream = I_CheckInfo_2.class.getClassLoader().getResourceAsStream("initResource.properties");
            prop.load(inStream);
            initial_FlieName=(String)prop.get("initial_FlieName");
            urlIP=(String)prop.get("urlIP");

            conn= JdbcUtil.getTestConnection();
            writer1 = new FileWriter(initial_FlieName+"AllInfoText.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void get_AddressInfo()  {

        Statement st= null;
        ResultSet rs= null;
        list = new ArrayList<AddrInfo>();
        try {
            st = conn.createStatement();
            rs= st.executeQuery("select xa.addr_full,xa.subtype from x_address_info xa where xa.subtype >4270 and xa.subtype < 4275");
            while (rs.next()){
                String addr_full= rs.getString("addr_full");
                String subtype=rs.getString("subtype");
                list.add(new AddrInfo(addr_full,subtype));
                if (list.size()%5000==0){
                    System.out.println("filed 5000");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static void call_Result () throws IOException {

        for (AddrInfo o : list){
            writer1.append(o.getAddr_full()+"|@|");
            String url=urlIP+"AddressSearch/AddressSearchController/getAddrGrad.do?addr_full="+o.getAddr_full();

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
                e.printStackTrace();
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


                        if (o.getSubtype().equals(levels)){

                            writer1.append(level+"|@|"+o.getSubtype()+"|@|"+"相同"+"|@|"+adrTypeName);

                            count=0;
                            return;
                        }else{
                            writer1.append(level+"|@|"+o.getSubtype()+"|@|"+"不相同"+"|@|"+adrTypeName);

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
    }

}
