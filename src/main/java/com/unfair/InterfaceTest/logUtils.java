package com.unfair.InterfaceTest;/*
 * @author Ferao
 * @date
 * @discription
 */

public class logUtils {

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
}
