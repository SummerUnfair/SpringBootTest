package InterfaceTest;

import Util.HttpClientUtil;
import Util.JdbcUtil;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class HandleOfSubtype_end_version {

    private static Connection conn= JdbcUtil.getTestConnection();
    private static Statement st;

    private static float count=0;
    private static float successCount = 0;
    private static float failCount = 0;
    private static long  TIME;
    private static long countdata;

    private static Writer writer1;   //用于文本一
    private static Writer writer2;   //用于文本二

    private static String  urls;
    private static String init_Path;
    private static String iName;

    public static void main(String[] args){

        try {
            init_Pro();
            init_TxtName();
            init_Txt();
            //////////////////////////////////////
            get_AddressInfo();
            //////////////////////////////////////
            writer1.close();
            writer2.close();

        } catch(IOException e){
            System.out.println(e);
        }
    }

    /*
    * 生成标准文件名
    * */
    public static void init_TxtName(){
        try {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String TIME=df.format(new Date());  //日期
        writer1 = new FileWriter(new File(init_Path+iName+TIME+".txt"));
        writer2=  new FileWriter(new File(init_Path+iName+TIME+"-messages"+".txt"));
        } catch(IOException e){
            System.out.println(e);
        }
    }


    /*
    *加载配置文件
    * */
    public static void init_Pro(){
        try {
        Properties prop = new Properties();
        InputStream inStream = HandleOfLog_end_version.class.getClassLoader().getResourceAsStream("HandleOfLogR.properties");
        prop.load(inStream);
        init_Path=(String)prop.get("init_Path");  //初始路径
        urls=(String)prop.get("url");             //被测接口URL
        iName=(String)prop.get("iName");          //被测接口名
        } catch(IOException e){
            System.out.println(e);
        }
    }
    /*
    * 文本起始内容
    * */
    public static void init_Txt(){

        try {
        writer1.append("\t\t"+"PAAS平台性能测试"+"\r\n");
        writer1.append("====================================================="+"\r\n");
        writer1.append("问题描述："+"同项目下相同接口于PAAS平台、本地平台及21服务平台，使用效率存在差异。"+"\r\n");
        writer1.append("差异内容："+"\r\n");
        writer1.append("\t"+"1.PAAS平台环境下接口调用存在超时问题。本地平台无超时现象发生。21服务平台无超时现象发生。"+"\r\n");
        writer1.append("====================================================="+"\r\n");
        writer1.append("接口描述："+"\r\n");
        writer1.append("\t"+"接口名称："+iName+"\r\n");
        writer1.append("\t"+"接口功能：对地址名称进行分级"+"\r\n");
//        writer1.append("\t"+"接口入参：addr_full"+"\r\n");
//        writer1.append("\t"+"接口出参：level、addrTerm"+"\r\n");
        writer1.append("====================================================="+"\r\n");
        writer1.append("测试数据描述："+"\r\n");
        writer1.append("\t"+"数据来源：x_address_info表"+"\r\n");
        writer1.append("\t"+"数据数量：10000条"+"\r\n");
        writer1.append("\t"+"数据获取方式：随机抽样"+"\r\n");
        writer1.append("====================================================="+"\r\n");
        writer1.append("测试数"+"\t\t"+"通过数"+"\t\t"+"失败数"+"\t\t"+"失败率"+"\t\t"+"平均响应时间(毫秒)"+"\r\n");
        writer1.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
获取地址
 */
    public static void get_AddressInfo() {

        if (iName.equals("getAddrGrad")){

            for (int i = 0; i < 3; i++){
                try {
                    st = conn.createStatement();
                    ResultSet rs = st.executeQuery("select xa.addr_full from (select * from x_address_info_new ORDER BY dbms_random.value) xa  WHERE xa.subtype >4270 and xa.subtype < 4275 and rownum<10001 ");
                    while (rs.next()) {
                        String addr_full = rs.getString("addr_full");
                        addr_full=addr_full.replaceAll(" +","");
                        getMatchData(addr_full);
                    }
                    rs.close();
                    st.close();
                    /////////////////////////////////////////////////////
                    count = successCount + failCount;
                    float L = failCount / count;
                    long AvgTime=TIME/countdata;
                    writer1.append(count + "\t\t" + successCount + "\t\t" + failCount + "\t\t" + L + "\t\t" + AvgTime +"\r\n");
                    count=0;
                    failCount=0;
                    successCount=0;
                    L=0;
                } catch (SQLException e) {

                } catch (IOException e) {

                }
                System.out.println("1..");
            }
            try {
                writer1.append("====================================================="+"\r\n");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                writer1.append("系统时间："+df.format(new Date()));

            } catch (IOException e) {

            }

        } else if (iName.equals("queryAddrsByKey")){

            for (int i = 0; i < 3; i++){
                try {
                    st = conn.createStatement();
                    ResultSet rs = st.executeQuery("select xa.addr_full from (select * from c_address ORDER BY dbms_random.value) xa  WHERE xa.subtype >4270 and xa.subtype < 4275 and  xa.addresstype_id=101000  and xa.addrstate=4265 and rownum<10001 ");
                    while (rs.next()) {
                        String addr_full = rs.getString("addr_full");
                        addr_full=addr_full.replaceAll(" +","");
                        getMatchData2(addr_full);
                    }
                    rs.close();
                    st.close();
                    /////////////////////////////////////////////////////
                    count = successCount + failCount;
                    float L = failCount / count;
                    long AvgTime=TIME/countdata;
                    writer1.append(count + "\t\t" + successCount + "\t\t" + failCount + "\t\t" + L + "\t\t" + AvgTime +"\r\n");
                    count=0;
                    failCount=0;
                    successCount=0;
                    L=0;
                } catch (SQLException e) {

                } catch (IOException e) {

                }
                System.out.println("1..");
            }
            try {
                writer1.append("====================================================="+"\r\n");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                writer1.append("系统时间："+df.format(new Date()));

            } catch (IOException e) {

            }
        }
    }
    public static void getMatchData (String addressName){

        String url=urls+iName+".do?addr_full="+addressName;
        String jsonRes = null;

        try {
            long startTime=System.currentTimeMillis();
            jsonRes = HttpClientUtil.get(url);
            long endTime0=System.currentTimeMillis();
            long S=endTime0-startTime;
            Use_Time(addressName,S);
            TIME=TIME+S;
            countdata++;
        }catch (Exception e){
            failCount++;
        }
        //校验是否调用成功
        try {
            if (jsonRes.contains("addrTerm")) {
                successCount++;
            } else {

            }
        }catch (Exception e){

        }
    }

    /*
    *
    *
    * */
    public static void getMatchData2 (String addressName){

        int L=addressName.length()/2;

        String address_lastName=addressName.substring(L);    //5~10

        int Last_randomNumber = (int)(Math.random() *address_lastName.length())+L ;  //括号内,下标0~4

        String address_allName="";
        try {
            address_allName=addressName.substring(0,Last_randomNumber);

        }catch (Exception e){

            System.out.print("");
        }


        String url=urls+iName+".do?address_name="+address_allName;
        String jsonRes = null;

        try {
            long startTime=System.currentTimeMillis();
            jsonRes = HttpClientUtil.get(url);
            long endTime0=System.currentTimeMillis();
            long S=endTime0-startTime;
            writer2.append(addressName+"\t");
            Use_Time(address_allName,S);
            TIME=TIME+S;
            countdata++;
        }catch (Exception e){
            failCount++;
        }
        //校验是否调用成功
        try {
            if (jsonRes.contains("ADDR_FULL")) {
                successCount++;
            } else {
                successCount++;
                writer2.append("调用为空："+addressName);
            }
        }catch (Exception e){

        }
    }


/*
* message文本获取
* */
    public static void Use_Time(String address_allName,long S ){

        try {
            writer2.append(address_allName+"\t"+S+"毫秒"+"\r\n");
        }catch (Exception e){

        }
    }

}
