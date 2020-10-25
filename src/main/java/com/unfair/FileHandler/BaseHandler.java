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
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
        ModelEnum modelEnum = ModelEnum.TO_DATABASE;
        logger.info("执行任务当前日期:{}", baseHandler.getThisToday());
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

    @Test
    public void UseTest() {

        System.out.println(File.separator);
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

        if (StringUtils.isEmpty(in)) {
            logger.info("读取路径不能为null");
            return ReturnT.builder()
                    .message("读取路径不能为null")
                    .returnData(in)
                    .build();
        }

        if (!StringUtils.endsWith(in, ".txt")) {
            logger.info("文件格式不正确");
            return ReturnT.builder()
                    .message("文件格式不正确")
                    .returnData(in)
                    .build();
        }

        String baseStrPath = in.substring(0, in.lastIndexOf(File.separator));
        File basePath = new File(baseStrPath);
        if (!basePath.exists()) {
            logger.info("当前路径不存在:{}", baseStrPath);
            return ReturnT.builder()
                    .message("当前路径不存在:" + baseStrPath)
                    .returnData(in)
                    .build();
        }
        logger.info("读取文件：{} | 写入文件：{}", in, out);

        File[] listFile = basePath.listFiles();
        List<File> files = Arrays.asList(listFile);
        long count = files.stream().filter(file -> StringUtils.endsWith(file.toString(), ".txt")).count();
        logger.info("同级文本数(txt)：{} ", count);

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
                        .message("未找到文件:" + in)
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
            if (startTime != 0) {
                useTime = endTime - startTime;
                logger.info("任务耗时:{}毫秒", useTime);
                return ReturnT.builder()
                        .message("SUCCESS")
                        .returnData("任务耗时:{}毫秒" + useTime)
                        .build();
            }
            return ReturnT.builder()
                    .message("SUCCESS")
                    .returnData(null)
                    .build();
        }
    }

    public void readToDataBase(String in) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select *from usr where username = ?");
            ps.setString(1, "丽丽");
            rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String username = rs.getString("username");
                System.out.println(id + ":" + username);
            }
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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

    public String getThisToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        String start = sdf.format(cal.getTime());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        String end = sdf.format(cal.getTime());
        return start + "|" + end;
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
class ReturnT<T> {
    private T returnData;
    private String message;
}
