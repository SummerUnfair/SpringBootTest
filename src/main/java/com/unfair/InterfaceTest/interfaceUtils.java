//package com.unfair.InterfaceTest;
//
//import com.unfair.Util.HttpClientUtil;
//import com.unfair.Util.JdbcUtils;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPReply;
//import redis.clients.jedis.Jedis;
//
//import java.io.*;
//import java.sql.*;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Properties;
//
///*
// * 模糊匹配接口性能测试
// * URL：http://10.145.220.62:8058/gis/iscenter/AddressSearch/AddressSearchController/getAddrGrad.do?addr_full=addressName
// *
// * String类型数据清理、Redis存储数据、数据库批量导入数据、日期时间处理、ftp服务器导入数据、
// * */
//public class interfaceUtils {
//
//    private static Writer writer;
//    private static String url_Prefix , url_Suffix;
//    private static String all_path;
//    private static String read_name , output_name;
//    private static List<String> list;
//    private static Connection conn;
//    private static String sqlString;
//    private static String ftpPath;
//
//    /**
//     * cleanName(); 文件中获取数据
//     * celanName(String sql); mysql中获取数据
//     */
//    public static void main(String[] args) throws Exception {
//        //接口性能测试
//        initConfig();
//        cleanName();
//        //cleanName(args[0]);  //cleanName(sqlString);
//        invoke();
//        writer.close();
//
//        //对比两文本不同
//
//    }
//
//    /**
//     * 初始化配置信息
//     */
//    public static void initConfig(){
//        try {
//            String time= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//            all_path="C:\\Users\\user\\Desktop\\";
//            read_name="模糊查询.txt";
//            output_name="地址智能匹配表";
//            writer = new FileWriter(all_path+output_name+time+".txt");
//            url_Prefix="http://10.145.209.143:8062/gis/";
//            url_Suffix="giscenter/AddressSearch/AddressSearchController/queryAddrsByKey.do?address_name=";
//            ftpPath = "//ftpdata//receive/eda_in//in_1410//";
//            System.out.println("init success..");
//            if (read_name == null){
//                sqlString="select * from GIS_pipeline_info";
//                Properties prop = new Properties();
//                InputStream inStream = interfaceUtils.class.getClassLoader().getResourceAsStream("initResource.properties");
//                prop.load(inStream);
//                String urlIP=(String)prop.get("urlIP");
//                conn= JdbcUtils.getConnection();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 地址清洗（数据源-->关系型数据库）
//     */
//    public static void cleanName(String sql) throws SQLException {
//        //可能读到为空|可能数据存在问题
//        PreparedStatement ps=conn.prepareStatement(sql);
//        ResultSet rs=ps.executeQuery();
//        while(rs.next()) {
//
//            String addressName = rs.getString("SERIALNUM");
//            if (addressName==null||addressName.trim().length()==0) continue;
//            list.add(rs.getString("SERIALNUM"));
//        }
//        System.out.println("clean success..");
//    }
//    /**
//     * 地址清洗（数据源-->TXT）
//     */
//    public static void  cleanName() throws IOException {
//        //当前日期
//        String datefm="";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        try{
//            Date date = new java.util.Date();
//            datefm = sdf.format(date);
//            System.out.println(datefm);
//        }catch(Exception e){
//            e.printStackTrace();
//        };
//
//        list = new ArrayList<String>();
//        String read_path=all_path+read_name;
//        Reader in = new FileReader(new File(read_path));
//        BufferedReader reader = new BufferedReader(in);
//        String s = "";
//        int index= -1;
//        while((s=reader.readLine())!=null) {
//            if(s.trim().length()==0) continue;
//            String addressName=s.replace(" ", "").replace("\t","").split(",")[0];
//            if((index=addressName.indexOf("镇"))!=-1){
//                addressName=addressName.substring(0, index + 1);
//            }
//            list.add(addressName);
//            if (list.size()%5000==0){
//                System.out.println("clean 5000..");
//            }
//        }
//        System.out.println("clean success..");
//    }
//    /**
//     * 地址写入（写入源-->ftp）
//     */
//    public void uploadToFtp(){
//        FTPClient ftp = new FTPClient();
//        int index= ftpPath.lastIndexOf("//");
//        try {
//            ftp.connect("10.7.6.118");
//            ftp.login("u_gis", "eda-dh99");
//
//            int reply = ftp.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                ftp.disconnect();
//                System.out.println("连接失败");
//                return;
//            }
//            System.out.println("ftp连接成功");
//
//            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);//设置上传文件的类型
//            ftp.changeWorkingDirectory(ftpPath.substring(0,index));//获取上传的路径地址
//            FileInputStream fis= new FileInputStream(all_path+output_name+".txt");
//            boolean res=ftp.storeFile(output_name+".txt",fis);//修改上传的文件名,保持上传后文件名一致
//            if(res){
//                System.out.println("上传成功");
//            }
//            fis.close();//关闭流
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            if (ftp.isConnected())
//                try {
//                    ftp.disconnect();
//                }
//                catch (IOException ioe) {
//                    ioe.printStackTrace();
//                }
//        }
//
//    }
//
//
//
//
//    /**
//     * 远程调用接口
//     */
//    public static void invoke() throws IOException {
//        int count=0;
//        for (String clean_name : list) {
//            count++;
//            String sub_name = subAddress(clean_name);
//            String url=url_Prefix+url_Suffix+sub_name;
//            String jsonRes = null;
//            long startTime=0;
//            long endTime=0;
//            long S=0;
//            try {
//                startTime=System.currentTimeMillis();
//                jsonRes = HttpClientUtil.get(url);
//                endTime=System.currentTimeMillis();
//                S=endTime-startTime;
//                writer.append("第"+count+"次"+"\t"+"参数地址:"+clean_name+"\t"+"模糊地址:"+sub_name+"\t"+S+"毫秒"+"\r\n");
//            }catch (Exception e){
//                writer.append(clean_name+"\r"+"转换失败!!");
//                writer.append("\t");
//            }
//            analysis_Json(jsonRes);
//        }
//    }
//
//    /**
//     * 字符串随机抽取算法
//     */
//    public static String subAddress(String clean_name) {
//        int L=clean_name.length()/2;
//        String address_lastName=clean_name.substring(L);    //5~10
//        int Last_randomNumber = (int)(Math.random() *address_lastName.length())+L ;  //括号内,下标0~4
//        String sub_name="";
//        sub_name=clean_name.substring(0,Last_randomNumber);
//        return sub_name;
//    }
//
//    /**
//     * 解析Json数据
//     */
//    public static void analysis_Json(String json_Info) throws IOException {
//        Object ADDR_FULL=null;
//        Object  ALIAS=null;
//        int count =0;
//        if (json_Info.contains("ADDR_FULL")) {
//
//            JSONObject resJson = JSONObject.fromObject(json_Info);
//            JSONObject res=  resJson.getJSONObject("data");
//            JSONArray tagsArray = res.getJSONArray("addrs");
//
//            for(Object obj : tagsArray){
//
//                count++;
//                JSONObject dataRes = JSONObject.fromObject(obj);
//                ALIAS=dataRes.get("ALIAS");
//                ADDR_FULL=dataRes.get("ADDR_FULL");
//                writer.append("\t\t"+"ALIAS:"+ALIAS+"\t"+"ADDR_FULL:"+ADDR_FULL);
//                if (count==3){
//                    break;
//                }
//            }
//        }else {
//            writer.append("\t\t"+"模糊查找失败");
//        }
//    }
//
//    private static Connection getFeraoConnection() {
//        Connection conn = null;
//        String url = "jdbc:oracle:thin:@10.7.100.15:1521:gis";
//        String user = "gis";
//        String password = "4WY_Md1BE28";
//
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection(url, user, password);
//            conn.setAutoCommit(false);
//            return conn;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return conn;
//        }
//    }
//}
