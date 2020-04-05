package com.ferao.InterfaceTest;

import com.ferao.Util.HttpClientUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Output source：文本
 *
 * The test interface：
 * 根据路名及弄号查询标签接口
 *
 * 根据Id查询标签接口
 *
 * 根据多个Id查询标签接口
 *
 * function:ApiTest
 * Created by FH on 20190910.
 */
public class HandleOfApiTest_two_version {
    public static  Writer writer;
    // static String  urlIP="http://10.7.74.41/";//正式库
    static String  urlIP="http://10.145.206.21:8089/";//测试库
    //static String  urlIP="http://10.6.246.32:8080/";//本地tomcat库

    static {
        try {
            writer = new FileWriter("C:\\Users\\user\\Desktop\\Dealdata20190903.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //根据路名及弄号查询标签接口
        getTagsByRoadName();

        //根据Id查询标签接口
        getTagsById("19444420");

        //根据多个Id查询标签接口
        getTagsByIds("19444420,19444518");
        writer.close();
    }

    /*
    根据路名及弄号查询标签接口
     */
    private static void getTagsByRoadName() throws IOException {
        String road_name="汶水路";  //路名
        String alley_begin="215";   //弄的开始
        String alley_end="215";     //弄的结束
        String doorplate_begin="";  //号的开始
        String doorplate_end="";    //号的结束

        for(int i=0;i<100;i++) {
            long startTime=System.currentTimeMillis();
            getTagsByRoad_Alley_Door(road_name, alley_begin, alley_end, doorplate_begin, doorplate_end);
            long endTime0=System.currentTimeMillis();
            long S=endTime0-startTime;
            writer.append(S+"毫秒");
            writer.append("\r\n");
        }
    }

    /*
    根据Id查询标签接口
     */
    private static void getTagsById(String id) throws IOException {

        HandleOfApiTest_two_version deal = new HandleOfApiTest_two_version();

        for (int i = 0; i < 1000; i++) {

            long startTime=System.currentTimeMillis();
            deal.getTagsByAddrsId(id);
            long endTime0=System.currentTimeMillis();
            long S=endTime0-startTime;
          writer.append(S+"毫秒");
            writer.append("\r\n");
        }
    }

    /*
    根据多个Id查询标签接口
     */
    private static void getTagsByIds( String id) throws IOException {

        HandleOfApiTest_two_version deal = new HandleOfApiTest_two_version();

        for (int i = 0; i < 10; i++) {

            long startTime=System.currentTimeMillis();
            deal.getTagsByAddrsIds(id);
            long endTime0=System.currentTimeMillis();
            long S=endTime0-startTime;
            writer.append(S+"毫秒");
            writer.append("\r\n");
        }
        System.out.println("测试完成！");
    }

    private String getTagsByAddrsIds(String id) throws IOException {
        if(id.trim().length() == 0)   return "id不能为空";

        String url=urlIP+"gisAddrTags/address/getTagsByIds.do?address_ids="+id;

        String jsonRes = null;
        try {
            jsonRes = HttpClientUtil.get(url);
        } catch (Exception e1) {
            String msg="SmartMatch的getStandard_Address方法调用接口失败,原地址为=("
                    +id+")";
            e1.printStackTrace();
        }
        if (jsonRes.contains("tags")) {
            //常见的java代码转换成json
            JSONObject resJson = JSONObject.fromObject(jsonRes);
            //进入addrs
            JSONArray dataone = resJson.getJSONArray("addrs");
            Object address_id=null;
            Object tagCode=null;
            for(Object obj : dataone){
                JSONObject dataRes = JSONObject.fromObject(obj);
                address_id = dataRes.get("address_id");
                writer.append(address_id+":");
                JSONArray tagCodeArray = dataRes.getJSONArray("tags");
                for (Object tag : tagCodeArray) {
                    JSONObject dataTag = JSONObject.fromObject(tag);
                    tagCode = dataTag.get("tagCode");
                    writer.append(tagCode+",");
                }
                writer.append("--");
            }
        }else {
            String msg="SmartMatch的getStandard_Address方法未查询到数据,原地址为=("
                    +id+")";
        }
        return  "SUCEESS!!";
    }


    public static String getTagsByAddrsId(String id) throws IOException {
        if(id.trim().length() == 0)   return "id不能为空";

        writer.append(id+":");

        String url=urlIP+"gisAddrTags/address/getTagsById.do?address_id="+id;

        String jsonRes = null;
        Object tagCode=null;
        try {
            jsonRes = HttpClientUtil.get(url);
        } catch (Exception e1) {
            String msg="SmartMatch的getStandard_Address方法调用接口失败,原地址为=("
                    +id+")";

            e1.printStackTrace();
        }

        if (jsonRes.contains("tags")) {

            //常见的java代码转换成json
            JSONObject resJson = JSONObject.fromObject(jsonRes);
            //进入addrs
            JSONArray tagsArray = resJson.getJSONArray("tags");
            for(Object obj : tagsArray){
                JSONObject dataRes = JSONObject.fromObject(obj);
                tagCode=dataRes.get("tagCode");
                writer.append(tagCode+"--");
            }
        }else {
            String msg="SmartMatch的getStandard_Address方法未查询到数据,原地址为=("
                    +id+")";
        }
        return  "SUCEESS!!";
    }



    /**
     * 测试接口是否正常(树状深度|||)
     * @param road_name
     * @param alley_begin
     * @param alley_end
     * @param doorplate_begin
     * @param doorplate_end
     * @return
     * @throws IOException
     */

    public static String getTagsByRoad_Alley_Door(String road_name,String alley_begin,String alley_end,String doorplate_begin,String doorplate_end) throws IOException {


        if(road_name.trim().length() == 0)   return "内容有误";
        String url=urlIP+"gisAddrTags/address/getTagsByRoadName.do?road_name=" + road_name;//正式库

        //信息完整
        if(alley_begin.trim().length() != 0 &&doorplate_begin.trim().length() != 0){

            url += "&alley_begin=" + alley_begin+"&alley_end="+alley_end+"&doorplate_begin="+doorplate_begin+"&doorplate_end="+doorplate_end;

        }else if(alley_begin.trim().length() != 0 &&doorplate_begin.trim().length() == 0){

            url += "&alley_begin=" + alley_begin+"&alley_end="+alley_end;

        }else if(alley_begin.trim().length() == 0 &&doorplate_begin.trim().length() != 0){

            url += "&doorplate_begin="+doorplate_begin+"&doorplate_end="+doorplate_end;
        }else {

            return  " 弄和号不能都为空！！ ";
        }

        String jsonRes = null;
        Object  tagCode=null;      //得到tagCode
        Object  address_id=null;   //获取ID

        long startTime=System.currentTimeMillis();

        try {
            jsonRes = HttpClientUtil.get(url);  //GET请求URL获取内容
        } catch (Exception e1) {
            String msg="SmartMatch的getStandard_Address方法调用接口失败,原地址为=("
                    +road_name+")";

            e1.printStackTrace();
        }

        if (jsonRes.contains("tags")) {

            //常见的java代码转换成json
            JSONObject resJson = JSONObject.fromObject(jsonRes);
            //进入addrs
            JSONArray dataone = resJson.getJSONArray("addrs");
            JSONObject jsObj = new JSONObject();
            for(Object obj : dataone){

                JSONObject dataRes = JSONObject.fromObject(obj);
                address_id=dataRes.get("address_id");
                writer.append(address_id+":");
                JSONArray standtagss = dataRes.getJSONArray("tags");
                for(Object jsO : standtagss){
                    JSONObject dataRe = JSONObject.fromObject(jsO);
                    tagCode=dataRe.get("tagCode");
                    writer.append(tagCode+"--");
                }
            }
        }else {
            String msg="SmartMatch的getStandard_Address方法未查询到数据,原地址为=("
                    +road_name+")";
        }
    return "SUCCESS!!";
    }
}
