package InterfaceTest;

import Util.HttpClientUtil;
import Util.JdbcUtil;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class HandleOfSubtype_end_version {

    public static Connection conn= JdbcUtil.getTestConnection();
    public static Statement st;
    public static float count=0;
    public static float successCount = 0;
    public static float failCount = 0;

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
            writer1 = new FileWriter(new File(initial_FlieName+"InfoText.txt"));

            writer1.append("\t\t"+"PAAS平台性能测试"+"\r\n");
            writer1.append("====================================================="+"\r\n");
            writer1.append("问题描述："+"同项目下相同接口于PAAS平台、本地平台及21服务平台，使用效率存在较大差异。"+"\r\n");
            writer1.append("差异内容："+"\r\n");
            writer1.append("\t"+"1.PAAS平台环境下接口调用存在较为严重的超时问题。本地平台无超时现象发生。21服务平台无超时现象发生。"+"\r\n");
            writer1.append("====================================================="+"\r\n");
            writer1.append("接口描述："+"\r\n");
            writer1.append("\t"+"接口名称：getAddrGrad.do"+"\r\n");
            writer1.append("\t"+"接口功能：对地址名称进行分级"+"\r\n");
            writer1.append("\t"+"接口入参：addr_full"+"\r\n");
            writer1.append("\t"+"接口出参：level、addrTerm"+"\r\n");
            writer1.append("====================================================="+"\r\n");
            writer1.append("测试数据描述："+"\r\n");
            writer1.append("\t"+"数据来源：x_address_info表"+"\r\n");
            writer1.append("\t"+"数据数量：10000条"+"\r\n");
            writer1.append("\t"+"数据获取方式：随机抽样"+"\r\n");
            writer1.append("\t"+"数据测试次数：早中晚分别执行三次"+"\r\n");
            writer1.append("====================================================="+"\r\n");
            writer1.append("测试数据总数"+"\t"+"通过总数"+"\t\t"+"失败总数"+"\t\t"+"失败率"+"\r\n");
            writer1.flush();
            get_AddressInfo();
            writer1.close();

        } catch(IOException e){
            System.out.println(e);
        }
    }
/*
获取地址
 */
    public static void get_AddressInfo() {

        for (int i = 0; i < 3; i++){
            try {
                st = conn.createStatement();
                ResultSet rs = st.executeQuery("select xa.addr_full from x_address_info xa where xa.subtype >4270 and xa.subtype < 4275 and rownum<10001 ");
                while (rs.next()) {
                    String addr_full = rs.getString("addr_full");
                    getMatchData(addr_full);
                }
                rs.close();
                st.close();
                count = successCount + failCount;
                float L = failCount / count;
                writer1.append(count + "\t\t" + successCount + "\t\t" + failCount + "\t\t" + L + "\r\n");
                count=0;
                failCount=0;
                successCount=0;
                L=0;
            } catch (SQLException e) {
                System.out.println("conn.createStatement():" + e);
            } catch (IOException e) {
                System.out.println("writer1:" + e);
            }
            System.out.println("1..");
        }

    }

    public static void getMatchData (String addressName){

        String url=urlIP+"AddressSearchController/getAddrGrad.do?addr_full="+addressName;
        String jsonRes = null;

        try {
            jsonRes = HttpClientUtil.get(url);
        }catch (Exception e){
            failCount++;
            System.out.println("HttpClientUtil:"+e);
        }

            if (jsonRes.contains("addrTerm")) {
                successCount++;
            } else {
                System.out.println("调用为空："+addressName);
            }
    }

}
