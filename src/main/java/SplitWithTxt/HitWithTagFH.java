package SplitWithTxt;

import java.sql.*;

/**
 * 横表批量打标签（可改纵表）
 */
public class HitWithTagFH {

    static Connection conn = getConnection();

    static PreparedStatement ps;

    static {
        try {
            ps = conn.prepareStatement("select * from addr_tags");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {

        TXTConnection();

    }

    public static  void TXTConnection() throws SQLException {

        int count = 0;
        long startTime=System.currentTimeMillis();

        ResultSet rs = ps.executeQuery();

        PreparedStatement pst=conn.prepareStatement("merge into ADDR_TAGS_TEST T1 " +
                "   using(select ? as address_id, '{\"tagCode\":10002,\"tagLevel\":2,\"tagValue\":\"政企低资费管控套餐\",\"tag_type_id\":\"100\",\"tag_type_value\":\"面向前端相关标签\"}'as GOVERNMENT_ENTERPRISE_TAG from dual) T2 on(T1.address_id=T2.address_id)  " +
                "   when matched then update set T1.GOVERNMENT_ENTERPRISE_TAG=T2.GOVERNMENT_ENTERPRISE_TAG   "  +
                "   when not matched then insert (T1.ADDRESS_ID,T1.GOVERNMENT_ENTERPRISE_TAG) values (T2.address_id,T2.GOVERNMENT_ENTERPRISE_TAG)");

        while (rs.next()) {
            count++;

            String STAND_ID=rs.getString("address_id");

            if(STAND_ID==null&&STAND_ID.trim().length()==0){
                continue;
            }

            pst.setString(1,STAND_ID);

            pst.addBatch();

            if(count%5000==0) {
                System.out.println("5000次已处理！！");
                pst.executeBatch();
                conn.commit();
            }

        }
        pst.executeBatch();
        conn.commit();
        System.out.println("SUCESS !!");
        long endTime1=System.currentTimeMillis();
        System.out.println("方法1执行时间："+(endTime1-startTime)+"ms");
    }

    /**
     * 获取数据库连接
     */
    private static  Connection getConnection() {
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
