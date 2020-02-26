package com.InterfaceTest;

import com.Util.JdbcUtil;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

/**
 * Information sources：日志文本
 * Output source：database
 * function:获取各接口数量及各接口所属各状态码数量写入数据库
 * Created by FH on 20190910.
 */
public class HandleOfLog_end_version {

    static Connection  conn= JdbcUtil.getTestConnection();
    static PreparedStatement  stmt;
    static String SERVICE_REDHAT;

    static {
        try {
            stmt = conn.prepareStatement("insert into access_log_fh(interface_name,number_200,number_500,number_fail,number_sum,createdate,SERVICE_REDHAT) values (?,?,?,?,?,to_char(sysdate,'yyyy-mm-dd'),?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static class Node implements Comparable<Node>{
        String name;
        double count;
        int code200 ,code500; //各个状态码出现次数
        int other;
        public Node(String name,Long count) {
            this.count=count;
            this.name=name;
        }
        public Node(String name, double count, int code200, int code500,int other) {

            this.name = name;
            this.count = count;
            this.code200 = code200;
            this.code500 = code500;
            this.other = other;
        }

        @Override
        public int compareTo(Node o) {
            //return (int) (this.count-o.count);//从小到大
            return (int) (o.count-this.count);//从大到小
        }
    }

    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();

        InputStream inStream = HandleOfLog_end_version.class.getClassLoader().getResourceAsStream("initResource.properties");
        prop.load(inStream);

        String path1=(String)prop.get("path1");
        SERVICE_REDHAT=(String)prop.get("SERVICE_REDHAT");

        getApiUseCountByDirectory(path1);

    }

    private static void getApiUseCountByDirectory(String path1) throws Exception{
        //localhost_access_log.2019-09-01.txt
        File file =new File(path1);
        File[] listFiles = file.listFiles();
        for (File file2 : listFiles) {
            if (file2.getName().endsWith(".txt")) {
               // System.out.println(file2.getName());
                String fileAllName=file2.getName();
                int firstIndex=fileAllName.indexOf(".");
                int lastIndex=fileAllName.lastIndexOf(".");
                String finallyName=fileAllName.substring(firstIndex+1,lastIndex);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                String systemTime=df.format(new Date());

                if (finallyName.equals(systemTime)){
                    //System.out.println(file2.getName());
                    getLongCount(file2);   //200次数 500次数  接口名称
                }

            }
        }
    }


    public static void   getLongCount(File file2) throws Exception {

        //读取日志
        Reader in = new FileReader(file2);
        BufferedReader reader = new BufferedReader(in);

        String s = "";

        int headindex , doindex;

        String str=null,str2=null;
        Map<String, Node> map =new HashMap<String, Node>();

        while ((s = reader.readLine()) != null) {

            doindex=s.indexOf(".do");
            if (doindex==-1) {
                continue; //如果不是.do地址就跳出
            }

            if ((headindex=s.indexOf("POST")) != -1) {
                str=s.substring(headindex+5,doindex);
                Node node=null;
                if (map.containsKey(str)) {
                    node = map.get(str);
                }else {
                    node=new Node(str, 0, 0,0, 0);
                }
                node.count++;
                //统计状态码
                codeCount(node, s);
                map.put(str, node);

            } else if ((headindex=s.indexOf("GET")) != -1) {
                str=s.substring(headindex+4,doindex);
                Node node=null;
                if (map.containsKey(str)) {
                    node = map.get(str);
                }else {
                    node=new Node(str, 0, 0, 0, 0);
                }
                node.count++;
                //统计状态码
                codeCount(node, s);
                map.put(str, node);

            }

        }
        List<Node> list=new ArrayList<Node>();//存储不同接口的各个数据

        Node n=null;
        for (String key : map.keySet()) {
            n=map.get(key);
            list.add(n);
        }

        for (Node nn : list) {

            String interface_name=nn.name;
            String number_200=""+ nn.code200;
            String number_500=""+ nn.code500;
            String number_fail=String.format("%.2f", nn.code500/nn.count*100);
            //String number_fail=""+nn.code500/nn.count*100;
            String number_sum=""+nn.count;

            stmt.setString(1,interface_name);
                stmt.setString(2,number_200);
                stmt.setString(3,number_500);
                stmt.setString(4,number_fail);
                stmt.setString(5,number_sum);
                stmt.setString(6,SERVICE_REDHAT);
                stmt.executeUpdate();
                conn.commit();
        }

        reader.close();
    }
    //统计状态码
    private static void codeCount(Node node, String s){

        int statusCodeIndex=s.lastIndexOf("HTTP");
        String str2 = s.substring(statusCodeIndex);
        if (str2.contains("200")) {
            node.code200++;
        }else if (str2.contains("500")) {
            node.code500++;
        }else {
            node.other++;
        }
    }

}
