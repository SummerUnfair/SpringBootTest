package com.ferao.InterfaceTest;

import java.io.*;
import java.util.*;

/**
 * Information sources：日志文本
 * Output source：文本
 * function:获取各接口数量及各接口所属各状态码数量
 * Created by FH on 20190910.
 */
public class HandleOfLog_initial_version {

    private static class Node implements Comparable<Node>{
        String name; //接口名称
        double count;  //当前接口总调用次数
        int code200 ,code500; //各个状态码出现次数
        int other;

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
            //return (int) (o.count-this.count);//从大到小
            if (this.count<o.count) {
                return 1;
            }
            if (this.count>o.count) {
                return -1;
            }

            return 0;
        }
    }


    public static void main(String[] args) throws Exception {

        String path1="C:\\Users\\user\\Desktop\\日志测试文件.txt";
        String path2="C:\\Users\\user\\Desktop\\log2.txt";
        getLongCount(path1, path2);

    }

    /**
     *
     * @param path1 日志路径
     * @param path2 保存路径
     * @throws Exception
     */
    public static void getLongCount(String path1,String path2) throws Exception {

        //设置打印路径
		PrintStream out=new PrintStream(new File(path2));
		System.setOut(out);

        //读取日志
        Reader in = new FileReader(new File(path1));
        BufferedReader reader = new BufferedReader(in);
        String s = "";

        int headindex , doindex;

        String str=null,str2=null;
        Map<String,Node> map =new HashMap<String, Node>();

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

        long sum=0;//统计.do
        Node n=null;
        for (String key : map.keySet()) {
            n=map.get(key);
            list.add(n);
            sum+=n.count;
        }
        Collections.sort(list);

        System.out.println("接口名称\t:\t调用次数\r\n");

        for (Node nn : list) {
            System.out.println("接口名称: "+nn.name+"\r\n");
            System.out.println("200 出现次数: "+nn.code200+"\r\n");
            System.out.println("500 出现次数: "+nn.code500+"\r\n");
            System.out.println("调用此接口的失败率为: "+String.format("%.3f", nn.code500/nn.count*100)+" % \r\n");
            System.out.println("其他状态码次数: "+nn.other+"\r\n");
            System.out.println("总调用次数: "+(long)nn.count+"\r\n\r\n");

        }

        System.out.println("所有.do接口总计调用 : "+sum+"\r\n");

        reader.close();

        System.out.println("SUCCESS!!!");
    }

    //统计状态码
    private static void codeCount(Node node,String s){

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
