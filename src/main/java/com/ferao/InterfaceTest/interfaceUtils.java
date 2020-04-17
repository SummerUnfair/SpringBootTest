package com.ferao.InterfaceTest;

import com.ferao.Util.HttpClientUtil;
import com.ferao.Util.JdbcUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/*
 * 模糊匹配接口性能测试
 * URL：http://10.145.220.62:8058/gis/iscenter/AddressSearch/AddressSearchController/getAddrGrad.do?addr_full=addressName
 * */
public class interfaceUtils {

    private static Writer writer;
    private static String url_Prefix , url_Suffix;
    private static String all_path;
    private static String read_name , output_name;
    private static List<String> list;
    private static Connection conn;
    private static String sqlString;

    /**
     * cleanName(); 文件中获取数据
     * celanName(String sql); mysql中获取数据
     */
    public static void main(String[] args) throws Exception {
        //接口性能测试
        initConfig();
        cleanName();
        //cleanName(args[0]);  //cleanName(sqlString);
        invoke();
        writer.close();

        //对比两文本不同

    }

    /**
     * 初始化配置信息
     */
    public static void initConfig(){
        try {
            all_path="C:\\Users\\user\\Desktop\\";
            read_name="模糊查询.txt";
            output_name="地址智能匹配表.txt";
            writer = new FileWriter(all_path+output_name);
            url_Prefix="http://10.145.209.143:8062/gis/";
            url_Suffix="giscenter/AddressSearch/AddressSearchController/queryAddrsByKey.do?address_name=";
            System.out.println("init success..");
            if (read_name == null){
                sqlString="select * from GIS_pipeline_info";
                Properties prop = new Properties();
                InputStream inStream = interfaceUtils.class.getClassLoader().getResourceAsStream("initResource.properties");
                prop.load(inStream);
                String urlIP=(String)prop.get("urlIP");
                conn= JdbcUtils.getConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 库中地址清洗
     */
    public static void cleanName(String sql) throws SQLException {
        //可能读到为空|可能数据存在问题
        PreparedStatement ps=conn.prepareStatement(sql);
        ResultSet rs=ps.executeQuery();
        while(rs.next()) {

            String addressName = rs.getString("SERIALNUM");
            if (addressName==null||addressName.trim().length()==0) continue;
            list.add(rs.getString("SERIALNUM"));
        }
        System.out.println("clean success..");
    }

    /**
     * 文件地址清洗
     */
    public static void  cleanName() throws IOException {
        //当前日期
        String datefm="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try{
            Date date = new java.util.Date();
            datefm = sdf.format(date);
            System.out.println(datefm);
        }catch(Exception e){
            e.printStackTrace();
        };

        list = new ArrayList<String>();
        String read_path=all_path+read_name;
        Reader in = new FileReader(new File(read_path));
        BufferedReader reader = new BufferedReader(in);
        String s = "";
        int index= -1;
        while((s=reader.readLine())!=null) {
            if(s.trim().length()==0) continue;
            String addressName=s.replace(" ", "").replace("\t","").split(",")[0];
            if((index=addressName.indexOf("镇"))!=-1){
                addressName=addressName.substring(0, index + 1);
            }
            list.add(addressName);
            if (list.size()%5000==0){
                System.out.println("clean 5000..");
            }
        }
        System.out.println("clean success..");
    }

    /**
     * 远程调用接口
     */
    public static void invoke() throws IOException {
        int count=0;
        for (String clean_name : list) {
            count++;
            String sub_name = subAddress(clean_name);
            String url=url_Prefix+url_Suffix+sub_name;
            String jsonRes = null;
            long startTime=0;
            long endTime=0;
            long S=0;
            try {
                startTime=System.currentTimeMillis();
                jsonRes = HttpClientUtil.get(url);
                endTime=System.currentTimeMillis();
                S=endTime-startTime;
                writer.append("第"+count+"次"+"\t"+"参数地址:"+clean_name+"\t"+"模糊地址:"+sub_name+"\t"+S+"毫秒"+"\r\n");
            }catch (Exception e){
                writer.append(clean_name+"\r"+"转换失败!!");
                writer.append("\t");
            }
            analysis_Json(jsonRes);
        }
    }

    /**
     * 字符串随机抽取算法
     */
    public static String subAddress(String clean_name) {
        int L=clean_name.length()/2;
        String address_lastName=clean_name.substring(L);    //5~10
        int Last_randomNumber = (int)(Math.random() *address_lastName.length())+L ;  //括号内,下标0~4
        String sub_name="";
        sub_name=clean_name.substring(0,Last_randomNumber);
        return sub_name;
    }

    /**
     * 解析Json数据
     */
    public static void analysis_Json(String json_Info) throws IOException {
        Object ADDR_FULL=null;
        Object  ALIAS=null;
        int count =0;
        if (json_Info.contains("ADDR_FULL")) {

            JSONObject resJson = JSONObject.fromObject(json_Info);
            JSONObject res=  resJson.getJSONObject("data");
            JSONArray tagsArray = res.getJSONArray("addrs");

            for(Object obj : tagsArray){

                count++;
                JSONObject dataRes = JSONObject.fromObject(obj);
                ALIAS=dataRes.get("ALIAS");
                ADDR_FULL=dataRes.get("ADDR_FULL");
                writer.append("\t\t"+"ALIAS:"+ALIAS+"\t"+"ADDR_FULL:"+ADDR_FULL);
                if (count==3){
                    break;
                }
            }
        }else {
            writer.append("\t\t"+"模糊查找失败");
        }
    }
}
