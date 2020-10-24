package com.unfair.FileHandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
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
                ReturnT returnT = baseHandler.readToTxt(inPath, null);
                System.out.println(returnT.getMessage());
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
    public ReturnT readToTxt(String in, String out) {

        if (StringUtils.isEmpty(in)){
            logger.info("读取路径不能为null");
            return ReturnT.builder()
                    .message("读取路径不能为null")
                    .returnData(in)
                    .build();
        }

        if (!StringUtils.endsWith(in,".txt")){
            logger.info("文件格式不正确");
            return ReturnT.builder()
                    .message("文件格式不正确")
                    .returnData(in)
                    .build();
        }

        logger.info("读取文件：{} | 写入文件：{}", in, out);
        long startTime = 0;
        long endTime = 0;
        long useTime = 0;
        FileReader reader = null;
        BufferedReader br = null;
        try {
            File file = new File(in);
            if (!file.exists()) {
                logger.info("当前路径下未找到文件:{}", in);
                return ReturnT.builder()
                        .message("未找到文件:"+in)
                        .returnData(in)
                        .build();
            }
            reader = new FileReader(file);
            br = new BufferedReader(reader);

            String entity;
            startTime = System.currentTimeMillis();
            while ((entity = br.readLine()) != null) {
//                System.out.println(entity);
            }

        } catch (IOException e) {
            logger.info("文件读取中异常");
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
            if (startTime !=0){
                useTime = endTime - startTime;
                logger.info("任务耗时:{}毫秒", useTime);
                return ReturnT.builder()
                        .message("SUCCESS")
                        .returnData("任务耗时:{}毫秒"+useTime)
                        .build();
            }
            return ReturnT.builder()
                    .message("SUCCESS")
                    .returnData(null)
                    .build();
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

    public class ReadToSpecificException extends Exception {
        private String errCode;
        private String errMsg;

        public ReadToSpecificException() {
            super();
        }
        public ReadToSpecificException(String message, Throwable cause) {
            super(message, cause);
        }

        public ReadToSpecificException(String message) {
            super(message);
        }
        public ReadToSpecificException(Throwable cause) {
            super(cause);
        }

        public ReadToSpecificException(String errCode, String errMsg) {
            super(errCode + ":" + errMsg);
            this.errCode = errCode;
            this.errMsg = errMsg;
        }

        public String getErrCode() {
            return this.errCode;
        }

        public String getErrMsg() {
            return this.errMsg;
        }
    }

}
@Data
@AllArgsConstructor
@Builder
class ReturnT<T>{
    private T returnData;
    private String message;
}
