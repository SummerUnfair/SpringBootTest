package com.ferao.GetSimpleData;

import com.ferao.Util.HttpClientUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Get_HttpInfo_inital_version {

    public static Writer writer1;


    static {
        try {
            writer1 = new FileWriter("C:\\Users\\user\\Desktop\\安装地址二分词表.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        String pathName="C:\\Users\\user\\Desktop\\互联网专线安装地址.txt";

        try {

            getAddressName(pathName);
            writer1.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }


    public static void  getAddressName(String pathName) throws IOException {

        Reader in = new FileReader(new File(pathName));
        BufferedReader reader = new BufferedReader(in);
        String s = "";
        Map<String,Integer> map =new HashMap<String,Integer>();
        String addressName=null;
        while((s=reader.readLine())!=null) {

            if(s.trim().length()==0) continue;
            String sss=s.replaceAll(" ", "");
            addressName = sss.replace("\t","");

            map.put(addressName,1);

        }

        for (String key : map.keySet()) {

            try {
                getHttpInfo(key);
            }catch (Exception e){

                writer1.append(addressName+"此条数据获取超时！！");
            }

            writer1.append("\r\n");

        }

    }

    public static void  getHttpInfo(String addressName) throws IOException {

        String url="http://192.168.74.189:10086/AddressCenter/addrIntf/phraseSegment.do?phrase="+addressName+"&analyzer=hanlp_synonym";

        String jsonRes = null;

        Object termName=null;

        try {

            jsonRes = HttpClientUtil.get(url);

        }catch (Exception e){

            writer1.append(addressName+"\r"+"转换失败!!");

        }

        if (jsonRes.contains("term")) {

            JSONArray resJson = JSONArray.fromObject(jsonRes);

             for(Object obj : resJson){
                JSONObject dataRes = JSONObject.fromObject(obj);
                termName=dataRes.get("term");

                writer1.append(termName+"\t");

            }
        }else{

            writer1.append("此地址无分词信息！！");
        }

    }

}
