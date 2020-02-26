package com.InterfaceTest;

import com.Util.HttpClientUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
/*
* 模糊匹配接口性能测试
* URL：http://10.145.220.62:8058/gis/iscenter/AddressSearch/AddressSearchController/getAddrGrad.do?addr_full=addressName
* */
public class HandleOfApiDim_advance_version {

    public static Writer writer1;
    public static String  urlIP= "http://10.145.220.62:8058/gis/";
    public static String initial_FlieName="E:\\AllTxt\\";

    static {
        try {
            writer1 = new FileWriter(initial_FlieName+"StreetText.txt");
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
     * clear
     * @throws IOException
     */
    public static void  MatchData() throws IOException {

        String  path1=initial_FlieName+"街道.txt";
        Reader in = new FileReader(new File(path1));
        BufferedReader reader = new BufferedReader(in);

        String s = "";

        int count=0;
        //地址智能匹配
        while((s=reader.readLine())!=null) {

            count++;

            if(s.trim().length()==0) continue;

            String sss=s.replaceAll(" ", "");
            String addressNames = sss.replace("\t","");

            String[] address =addressNames.split(",");

            String addressName= address[0];
            String addressSubtype=address[1];
            if (count%1000==0){
                System.out.println("1000..");
            }

            try {

                writer1.append("number："+count+">>"+addressName+"\t");

                getMatchData(addressName,addressSubtype);

                writer1.append("\r\n");
            }catch (NullPointerException e){
                writer1.append("调用超时.."+"\r\n");
                //writer1.append("\r\n");
            }catch (IOException e) {
                //e.printStackTrace();
            }
        }

    }

    public static void getMatchData (String addressName,String addressSubtype ) throws IOException {

        String url=urlIP+"giscenter/AddressSearch/AddressSearchController/getAddrGrad.do?addr_full="+addressName;

        String jsonRes = null;

        Object level=null;
        Object addrTerm=null;

        int count =0;
        int len=0;
        try {
            jsonRes = HttpClientUtil.get(url);
        }catch (Exception e){

//            writer1.append(addressName+"\t"+"转换失败!!");
//            writer1.append("\t");

        }

        if (jsonRes.contains("addrTerm")) {

            len=jsonRes.split("addrTerm").length-1;
            //System.out.println("len:"+len);
            JSONObject resJson = JSONObject.fromObject(jsonRes);
            JSONArray ja=resJson.getJSONArray("result");

            for(Object obj : ja){
                count++;
                //System.out.println(count);
                JSONObject dataRes = JSONObject.fromObject(obj);
                level=dataRes.get("level");
                addrTerm=dataRes.get("addrTerm");
                if(len==count){
                    String levels =level+"";
                    if (addressSubtype.equals(levels)){
                        writer1.append("level:"+level+"，"+"addrTerm:"+addrTerm+"\t"+"相同");
                        count=0;
                        return;
                    }else{
                        writer1.append("level:"+level+"，"+"addrTerm:"+addrTerm+"\t"+"不相同"+"\t"+"测试值"+level+"，标准值："+addressSubtype);
                        count=0;
                        return;
                    }
                }
                writer1.append("level:"+level+"，"+"addrTerm:"+addrTerm+"\t");
            }
        }

        else {
            writer1.append("无调用结果");
        }
    }
}
