package com.InterfaceTest;

import com.Util.HttpClientUtil;

import java.io.*;

public class HandleOfApiDim_inital_version {

    public static Writer writer1;

    public static String  urlIP="http://10.145.209.143:8062/gis/";//21环境
    public static String initial_FlieName="C:\\Users\\user\\Desktop\\";


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

        String url=urlIP+"giscenter/AddressSearch/AddressSearchController/queryAddrsByKey.do?address_name="+addressName;

        String jsonRes = null;

        Object ADDR_FULL=null;

        try {
            jsonRes = HttpClientUtil.get(url);
        }catch (Exception e){

            writer1.append(addressName+"\r"+"转换失败!!");
            writer1.append("\t");

        }

        if (jsonRes.contains("ADDR_FULL")) {

            writer1.append("\t\t"+"模糊查找成功");

        }else {

            writer1.append("\t\t"+"模糊查找失败");

        }

    }

}
