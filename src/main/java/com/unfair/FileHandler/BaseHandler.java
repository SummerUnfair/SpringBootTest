package com.unfair.FileHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @author fenghao
 * @discription
 * @create 2020-10-24 15:37
 */
public class BaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(BaseHandler.class);

    private static String inPath;
    private static String outPath;

    public static void main(String[] args) {

        BaseHandler baseHandler = new BaseHandler();
        baseHandler.initData();
        ModelEnum modelEnum = ModelEnum.TO_TXT;
        switch (modelEnum) {
            case TO_TXT:
                baseHandler.readToTxt(inPath, null);
                break;
            case TO_DATABASE:
                baseHandler.readToDataBase(null);
                break;
        }
    }

    public void initData() {

        inPath = "C:\\Users\\Hasee\\Desktop\\待解决.txt";
        outPath = "/User/outTest.txt";

    }

    /**
     * 向文本中写
     *
     * @param in
     * @param out
     */
    public void readToTxt(String in, String out) {
        long startTime = 0;
        long endTime = 0;
        long useTime = 0;
        FileReader reader = null;
        BufferedReader br = null;
        try {
            File file = new File(in);
            if (!file.exists()) {
                logger.info("当前路径下未找到文件:{}", in);
            }
            reader = new FileReader(file);
            br = new BufferedReader(reader);

            String entity;
            startTime = System.currentTimeMillis();
            while ((entity = br.readLine()) != null) {
//                System.out.println(entity);
            }

        } catch (IOException e) {

        } finally {
            endTime = System.currentTimeMillis();
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                logger.info("BufferedReader关闭异常");
                e.printStackTrace();
            }
            useTime = endTime - startTime;
            logger.info("任务耗时:{}毫秒", useTime);
        }

    }

    public void readToDataBase(String in) {
//        Connection conn = getConnection();
//        PreparedStatement ps = conn.prepareStatement("select *from usr where name = ？");
//        ps.setString(1, "丽丽");
//        ps.executeUpdate();
//        conn.commit();

    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            /**
             * mysql ：com.mysql.jdbc.Driver
             * oracle：oracle.jdbc.driver.OracleDriver
             */
            Class.forName("com.mysql.jdbc.Driver");
            /**
             * mysql ：jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&useSSL=false
             * oracle：jdbc:oracle:thin:@10.145.206.20:1521:gis
             */
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1?useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "root");
            conn.setAutoCommit(false);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return conn;
        }
    }

    public enum ModelEnum {

        TO_TXT, TO_DATABASE;
    }
}
