package com.GetSimpleData;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Information sources：无
 * Output source：无
 * function:得到标准时间格式
 * Created by FH on 20190910.
 */
public class Get_Standard_Time {

    public static void main(String[] args) {

        //localhost_access_log.2019-09-01.txt
        String s="localhost_access_log.2019-09-01.txt";

        int doindex=s.indexOf(".");
        int lastindex=s.lastIndexOf(".");
        String a=s.substring(doindex+1,lastindex);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String b=df.format(new Date());

        a.equals(b);

        System.out.println(b);// new Date()为获取当前系统时间
        System.out.println(a);
        System.out.println(a.equals(b));

    }
}

