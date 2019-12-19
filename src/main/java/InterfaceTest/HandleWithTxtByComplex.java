package InterfaceTest;

import Util.SmartMatch;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HandleWithTxtByComplex {

     static Connection conn=getConnection();
     static PreparedStatement ps=null;
    static{

        try {

            ps=conn.prepareStatement("merge into ADDR_TAGS_TEST T1 " +
                    "using(select ? as address_id, '{\"tagCode\":10002,\"tagLevel\":2,\"tagValue\":\"政企低资费管控套餐\",\"tag_type_id\":\"100\",\"tag_type_value\":\"面向前端相关标签\"}'as GOVERNMENT_ENTERPRISE_TAG from dual) T2 on(T1.address_id=T2.address_id) " +
                    "when matched then update set T1.GOVERNMENT_ENTERPRISE_TAG=T2.GOVERNMENT_ENTERPRISE_TAG " +
                    "when not matched then insert (T1.ADDRESS_ID,T1.GOVERNMENT_ENTERPRISE_TAG) values (T2.address_id,T2.GOVERNMENT_ENTERPRISE_TAG)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, SQLException {

//        String path1="C:\\Users\\user\\Desktop\\政企低资管控.txt";
//        String path2="C:\\Users\\user\\Desktop\\无法查询.txt";
//        split( path1,path2);

        split( args[0],args[1]);

    }

    //分离出具有Y特性及其中地址全称
    public static void split(String path1,String path2) throws IOException, SQLException {

        Reader in = new FileReader(new File(path1));
        BufferedReader reader = new BufferedReader(in);

        Writer writer = new FileWriter(new File(path2));

        String s = "";
        String []arr;

        int count =0; //计数 到5000
        String [] addressIdArr=null; //用来存5000 个id

        while((s = reader.readLine()) != null){

            if (s.trim().length()==0) continue;
            if (addressIdArr==null) {
                addressIdArr=new String[10];
            }
            arr= s.split("\\|%\\|");

            //获取地址全称
            String addressName=arr[1];

            //获取地址ID
            String addressId=getAddressId(addressName);

            if(addressId==null||addressId.trim().length()==0) {

                writer.append(addressName + "\r\n");
                writer.flush();
                continue;
            }else {
                addressIdArr[count++]=addressId;//查到id就放数组里
            }

            //通过Id对数据库表进行操作
            if (count==10) {
                insertDate(addressIdArr);
                addressIdArr=null;//清空
                count=0;//计数归零
            }

        }

        //有可能没到5000个id就 上面循环结束了 所以还需再次提交剩下的内容
        if (addressIdArr!=null) {
            insertDate(addressIdArr);
        }

        //writer.close();
        reader.close();
        writer.close();
        System.out.println("SUCCESS!!!");
    }

    //获取标准地址ID
    public static String getAddressId(String addr){

        //获取标准地址
        String id="";
        try{
            id=SmartMatch.getStandard_id(addr);
        } catch (Exception e) {

        }
        return id;
    }

    public static  void insertDate(String []addressIdArr) throws SQLException {

        for (int i = 0; i < addressIdArr.length; i++) {
            if (addressIdArr[i]==null) {
                continue;
            }
            int id_num=Integer.parseInt(addressIdArr[i]);
            ps.setInt(1,id_num);
            ps.addBatch();
        }
        //批处理
        ps.executeBatch();

        conn.commit();
        ps.clearBatch();//清空一下

    }

    /**
     * 获取数据库连接
     */
    private  static  Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@10.145.206.20:1521:gis", "GIS", "M3fXy_TFaUt");
            conn.setAutoCommit(false);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return conn;
        }
    }
}



