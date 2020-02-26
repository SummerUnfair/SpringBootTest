package com.InterfaceTest;

import com.Util.JdbcUtil;
import com.Util.SmartMatch;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
* 清洗文本数据获取地址，调用数据库更新表数据
* */
public class TI_UpdateInfo {

    private static Writer writer;
    private static Connection conn ;
    private static PreparedStatement ps ;
    private static List<String> list;

    public static void main(String[] args) throws IOException, SQLException {
        //String path1="C:"+File.separator+"Users"+File.separator+"user"+File.separator+"Desktop"+File.separator+"政企低资管控.txt";
        //String path2="C:"+File.separator+"sers"+File.separator+"user"+File.separator+"Desktop"+File.separator+"无法查询.txt";
        //split( path1,path2);
        initConfig();
        cleanInformation( args[0],args[1]);
        System.out.println("clean success!");
        updateMassage();
        System.out.println("update success!");
    }

    public static void initConfig(){
        conn= JdbcUtil.getTestConnection();
        list = new ArrayList<String>();
        try {
            ps=conn.prepareStatement("merge into ADDR_TAGS_TEST T1 " +
                    "using(select ? as address_id, '{\"tagCode\":10002,\"tagLevel\":2,\"tagValue\":\"政企低资费管控套餐\",\"tag_type_id\":\"100\",\"tag_type_value\":\"面向前端相关标签\"}'as GOVERNMENT_ENTERPRISE_TAG from dual) T2 on(T1.address_id=T2.address_id) " +
                    "when matched then update set T1.GOVERNMENT_ENTERPRISE_TAG=T2.GOVERNMENT_ENTERPRISE_TAG " +
                    "when not matched then insert (T1.ADDRESS_ID,T1.GOVERNMENT_ENTERPRISE_TAG) values (T2.address_id,T2.GOVERNMENT_ENTERPRISE_TAG)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 清洗文本获取地址及进一步获取Id
     * @param path1
     * @param path2
     * @throws IOException
     * @throws SQLException
     */
    public static void cleanInformation (String path1,String path2) throws IOException, SQLException {
        Reader in = new FileReader(new File(path1));
        BufferedReader reader = new BufferedReader(in);
        writer = new FileWriter(new File(path2));

        String s ;
        int count =0; //计数 到5000

        while((s = reader.readLine()) != null){
            //去除前后空格
            if (s.trim().length()==0) continue;
            //分隔字符串为给定正则形式-> |%|
            String addressId=getAddressId(s.split("\\|%\\|")[1]);
            if(addressId==null||addressId.trim().length()==0) {
                writer.append(s);
                writer.flush();
                continue;
            }else {
                list.add(addressId);
            }
        }
        reader.close();
        writer.close();
        System.out.println("SUCCESS!");
    }

    /**
     * 调用企业提供工具包获取标准地址Id
     * @param addr ：清洗后的地址名称
     * @return   ： 查询得到的Id
     */
    public static String getAddressId(String addr){
        String id="";
        try{
            //企业提供工具包
            id=SmartMatch.getStandard_id(addr);
        } catch (Exception e) {
            System.out.println(addr+"Failed to get information !");
        }
        return id;
    }

    /**
     * 更新库表中数据
     * @throws SQLException
     */
    public static  void updateMassage() throws SQLException {

        for (String addrIds : list){
            int id_num=Integer.parseInt(addrIds);
            ps.setInt(1,id_num);
            ps.addBatch();
            //批处理
            ps.executeBatch();
            conn.commit();
            ps.clearBatch();//清空一下
        }
    }
}



