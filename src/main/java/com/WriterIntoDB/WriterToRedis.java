package com.WriterIntoDB;

import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class WriterToRedis {
    private Map<String,String> map = new HashMap<String,String>();

    public static void main(String[] args) {

        WriterToRedis writerToRedis = new WriterToRedis();

        //WriterToRedis.getNewPFCommunityAliasData();
        writerToRedis.getNewPCommunityAliasData();
        writerToRedis.getCommunityAliasData();
        writerToRedis.getBuildingAliasData();
        writerToRedis.getStreetTownData();

        writerToRedis.writeToTestRedis();
    }


//    public void writeToDepartRedis(){
//        //连接本地的 Redis 服务
//        Jedis jedis = new Jedis("192.168.74.189",6379);
//        jedis.auth("gis1001A");
//        jedis.select(6);
//
//        System.out.println("连接成功");
//        //查看服务是否运行
//        System.out.println("服务正在运行: "+jedis.ping());
//
//        jedis.hmset("addr_lib", map);
//        System.out.println("写入成功");
//    }
//    public void writeToProdRedis(){
//        //连接本地的 Redis 服务
//        Jedis jedis = new Jedis("10.7.100.16",6379);
//        jedis.auth("1qsdfzc123afthafgyj");
//        jedis.select(6);
//
//        System.out.println("连接成功");
//        //查看服务是否运行
//        System.out.println("服务正在运行: "+jedis.ping());
//
//        jedis.hmset("addr_lib", map);
//        System.out.println("写入成功");
//    }

//    public void writeToTestRedis(){
//        //连接本地的 Redis 服务
//        Jedis jedis = new Jedis("10.145.206.21",6379);
//        jedis.auth("Isetapasswordin20190425");
//        jedis.select(6);
//
//        System.out.println("连接成功");
//        //查看服务是否运行
//        System.out.println("服务正在运行: "+jedis.ping());
//
//        jedis.hmset("addr_lib", map);
//        System.out.println("写入成功");
//    }
    public void writeToTestRedis(){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("10.6.246.32",6379);
        jedis.auth("123456");
        jedis.select(6);

        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        System.out.println(map.size());
        jedis.hmset("addr_lib", map);
        System.out.println("写入成功");
    }

//    public void writeToTestRedis(){
//        //连接本地的 Redis 服务
//        Jedis jedis = new Jedis("127.0.0.1",6379);
//        jedis.auth("123");
//        jedis.select(6);
//
//        System.out.println("连接成功");
//        //查看服务是否运行
//        System.out.println("服务正在运行: "+jedis.ping());
//        System.out.println(map.size());
//        jedis.hmset("addr_lib", map);
//        System.out.println("写入成功");
//    }
    /**
     * 获取需要的数据，并调用接口
     */
    public  void getNewPCommunityAliasData() {
        Connection conn = getTestConnection();
        //获取新增nz数据
        String sqlString = "select distinct segment from  x_thesaurus  where  speech_part='nz' ";
        ResultSet rs = null;
        Statement statement=null;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(sqlString);

            //设置 redis 字符串数据
            while (rs.next()) {

                String segment = rs.getString("segment");

                //System.out.println("."+segment+","+"POI");
                map.put(segment,"POI");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    //关闭连接
                    rs.close();
                    statement.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 获取需要的数据，并调用接口
     */
    public  void getNewPFCommunityAliasData() {
        Connection conn = getTestConnection();
        //获取新增nz数据
        String sqlString = "select distinct segment from  x_thesaurus  where  speech_part='nis'  ";
        ResultSet rs = null;
        Statement statement=null;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(sqlString);

            //设置 redis 字符串数据
            while (rs.next()) {

                String segment = rs.getString("segment");

                //System.out.println("."+segment+","+"PFX");
                map.put(segment,"PFX");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    //关闭连接
                    rs.close();
                    statement.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取需要的数据，并调用接口
     */
    public  void getCommunityAliasData() {
        Connection conn = getTestConnection();
        //城地小区别名
        String sqlString = "select distinct * from postfix_type";
        ResultSet rs = null;
        Statement statement=null;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(sqlString);

            //设置 redis 字符串数据
            while (rs.next()) {

                String name = rs.getString(1);
                String type = rs.getString(2);
                //System.out.println("."+name+","+type);
                map.put(name,type);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    //关闭连接
                    rs.close();
                    statement.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 获取需要的数据，并调用接口
     */
    public  void getBuildingAliasData() {
        Connection conn = getTestConnection();
        //楼宇网格别名
        String sqlString = "select distinct name,'ALIAS' from tab_name_addrid";
        ResultSet rs = null;
        Statement statement=null;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(sqlString);

            //设置 redis 字符串数据
            while (rs.next()) {

                String name = rs.getString(1);
                String type = rs.getString(2);
               // System.out.println("."+name+","+type);
                map.put(name,type);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    //关闭连接
                    rs.close();
                    statement.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取需要的数据，并调用接口
     */
    public  void getStreetTownData() {
        Connection conn = getTestConnection();
        //行政区，街道，镇
        String sqlString = "select  distinct d.addr_name,d.type  from dict_addr_type d";
        ResultSet rs = null;
        Statement statement=null;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(sqlString);

            //设置 redis 字符串数据
            while (rs.next()) {

                String name = rs.getString(1);
                String type = rs.getString(2);
              //  System.out.println("."+name+","+type);
                map.put(name,type);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    //关闭连接
                    rs.close();
                    statement.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取数据库连接
     * 部门
     */
//    private static Connection getDepartConnection() {
//        Connection conn = null;
//        String url = "jdbc:oracle:thin:@192.168.74.185:1521:gis";
//        String user = "GIS_COLLECT";
//        String password = "GIS_COLLECT";
//
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection(url, user, password);
//            conn.setAutoCommit(false);
//            return conn;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return conn;
//        }
//    }


    /**
     * 获取数据库连接
     * 测试
     */
    private static Connection getTestConnection() {
        Connection conn = null;
        String url = "jdbc:oracle:thin:@10.145.206.20:1521:gis";
        String user = "GIS";
        String password = "M3fXy_TFaUt";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return conn;
        }
    }


    /**
     * 获取数据库连接
     * 生产
     */

//    private static Connection getProdConnection() {
//        Connection conn = null;
//        String url = "jdbc:oracle:thin:@10.7.100.15:1521:gis";
//        String user = "gis";
//        String password = "4WY_Md1BE28";
//
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection(url, user, password);
//            conn.setAutoCommit(false);
//            return conn;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return conn;
//        }
//    }
}
