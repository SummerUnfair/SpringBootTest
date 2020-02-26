package com.GetSimpleData;

import java.io.*;

/**
 * Information sources：地址文本
 * Output source：文本
 * function:分离文本符号|%| 得到中间地址
 * Created by FH on 20190910.
 */
public class Get_AddressName_end_version {

    public static void main(String[] args) throws IOException {

        String path1="C:\\Users\\user\\Desktop\\政企低资管控.txt";
        String path2="C:\\Users\\user\\Desktop\\地址全称.txt";

        split(path1, path2);
    }

    //分离|%|中间地址
    public static void split(String path1, String path2) throws IOException {

        Reader in = new FileReader(new File(path1));
        //为了提供读的效率而设计的
        BufferedReader reader = new BufferedReader(in);

        String s = "";

        Writer writer = new FileWriter(new File(path2));

        String []arr;
        while((s = reader.readLine()) != null){
            if (s.trim().length()==0) continue;

            arr= s.split("\\|%\\|");
            String addressName=arr[1];

            writer.append(addressName + "\r\n");

        }
        writer.close();
        reader.close();
        System.out.println("SUCCESS!!!");
    }

}
