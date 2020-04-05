package com.ferao.InterfaceTest;

import com.ferao.Pojo.Emp;
import com.ferao.Util.HttpClientUtil;
import com.ferao.Util.JdbcUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*
* 核对URL返回数据
* */
public class I_CheckInfo_1 {

    static Connection conn ;
    static Statement st;
    public static Writer writer1;
    public static String  urlIP;
    static List<Emp> list ;
    public static String initial_FlieName;

    public static void main(String[] args) {

        initConfig();
        System.out.println("load success!");
        get_AddressInfo();
        System.out.println("filed success!");
        call_Result();
    }

    /**
     * 初始化配置资源
     */
    public static void initConfig(){
        try {
            conn= JdbcUtil.getTestConnection();
            Properties prop = new Properties();
            InputStream inStream = I_CheckInfo_1.class.getClassLoader().getResourceAsStream("initResource.properties");
            prop.load(inStream);
            initial_FlieName=(String)prop.get("initial_FlieName");
            urlIP=(String)prop.get("urlIP");
            writer1 = new FileWriter(new File(initial_FlieName+"fourInfoText.txt"));
            list = new ArrayList<Emp>();
        } catch (IOException e) {
            System.out.println("fail to load !");
        }
    }

    /**
     * 获取表信息
     */
    public static void get_AddressInfo(){
        ResultSet rs = null;
        Emp emp =null ;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select xa.id,xa.addr_full,xa.subtype from x_address_info xa where xa.subtype >4270 and xa.subtype < 4275  and xa.id between -2000000 and  -74 ");
            while (rs.next()) {
                String id = rs.getString("id");
                String addr_full = rs.getString("addr_full");
                String subtype = rs.getString("subtype");
                emp =new Emp(id,addr_full,subtype);
                //装载集合
                list.add(emp);
                if (list.size() % 5000 ==0){
                    System.out.println("filed 5000");
                }
            }
        }catch (SQLException e){
            System.out.println("conn.createStatement():"+e);
        }
    }

    /**
     * 对比逐条信息
     */
    public static void call_Result () {
        //getMatchData(addr_full, subtype);
        for (Emp o : list){

            String url=urlIP+"AddressSearchController/getAddrGrad.do?addr_full="+o.getAddr_full();
            String jsonRes = null;
            Object level=null;
            Object addrTerm=null;
            int count =0;
            int len=0;
            try {
                writer1.append(o.getId()+ "|@|"+o.getAddr_full() + "|@|");
                jsonRes = HttpClientUtil.get(url);
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

                            if (o.getSubtype().equals(levels)) {

                                writer1.append(level + "|@|" + o.getSubtype() + "|@|" + "相同" + "|@|" + adrTypeName);

                                count = 0;
                                return;
                            } else {
                                writer1.append(level + "|@|" + o.getSubtype() + "|@|" + "不相同" + "|@|" + adrTypeName);

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
        try {
            writer1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
