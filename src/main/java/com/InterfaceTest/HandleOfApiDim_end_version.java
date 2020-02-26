package com.InterfaceTest;

import com.Util.HttpClientUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
/*
 * 模糊匹配接口性能测试
 * URL：http://10.145.220.62:8058/gis/iscenter/AddressSearch/AddressSearchController/getAddrGrad.do?addr_full=addressName
 *
 *
 *
 * */
public class HandleOfApiDim_end_version {

    public static Writer writer1;

    public static String  urlIP="http://10.145.209.143:8062/gis/";//21环境
    public static String initial_FlieName="C:\\Users\\user\\Desktop\\";

//    static String  urlIP="http://10.145.131.213:8058/gis/";//生产环境
//    public static String initial_FlieName="C:\\Users\\gisadm\\Desktop\\";

    static {
        try {
            writer1 = new FileWriter(initial_FlieName+"地址智能匹配表.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        //模糊匹配
        MatchData();
        writer1.close();
    }

    /**
     * 地址智能匹配
     * @throws IOException
     */
    public static void  MatchData() throws IOException {

        String  path1=initial_FlieName+"模糊查询.txt";
        Reader in = new FileReader(new File(path1));
        BufferedReader reader = new BufferedReader(in);
        String s = "";

        int count=0;
        //地址智能匹配
        while((s=reader.readLine())!=null) {

            count++;

            if(s.trim().length()==0) continue;

            String sss=s.replaceAll(" ", "");
            String addressName = sss.replace("\t","");

            try {

                writer1.append("第"+count+"次"+"\t");

                long startTime=System.currentTimeMillis();

                getMatchData(addressName);

                long endTime0=System.currentTimeMillis();
                long S=endTime0-startTime;
                writer1.append("\t"+S+"毫秒");
                writer1.append("\r\n");
            }catch (IOException e) {
                e.printStackTrace();
            }catch (NullPointerException e){
                writer1.append(addressName+"\r"+"此条调用失败！"+"\r\n");
            }
        }

    }

    public static void getMatchData (String addressName ) throws IOException {

        writer1.append("参数地址:"+addressName);

        int L=addressName.length()/2;

        String address_lastName=addressName.substring(L);    //5~10

        int Last_randomNumber = (int)(Math.random() *address_lastName.length())+L ;  //括号内,下标0~4

        String address_allName="";
        try {
             address_allName=addressName.substring(0,Last_randomNumber);

        }catch (Exception e){

            writer1.append("\t"+"模糊地址:下标越界");
        }

        writer1.append("\t"+"模糊地址:"+address_allName);

        String url=urlIP+"giscenter/AddressSearch/AddressSearchController/queryAddrsByKey.do?address_name="+address_allName;

        String jsonRes = null;

        Object ADDR_FULL=null;

        Object  ALIAS=null;

        int count =0;

        try {
            jsonRes = HttpClientUtil.get(url);
        }catch (Exception e){

            writer1.append(addressName+"\r"+"转换失败!!");
            writer1.append("\t");

        }

        if (jsonRes.contains("ADDR_FULL")) {

            JSONObject resJson = JSONObject.fromObject(jsonRes);
            JSONObject res=  resJson.getJSONObject("data");
            JSONArray tagsArray = res.getJSONArray("addrs");

            for(Object obj : tagsArray){

                count++;
                JSONObject dataRes = JSONObject.fromObject(obj);
                ALIAS=dataRes.get("ALIAS");
                ADDR_FULL=dataRes.get("ADDR_FULL");
                writer1.append("\t\t"+"ALIAS:"+ALIAS+"\t"+"ADDR_FULL:"+ADDR_FULL);

                if (count==3){
                    break;
                }

            }
        }else {

            writer1.append("\t\t"+"模糊查找失败");

        }

    }
}
