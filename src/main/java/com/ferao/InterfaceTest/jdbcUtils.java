package com.ferao.InterfaceTest;/*
 * @author Ferao
 * @date
 * @discription
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class jdbcUtils {

    private static PreparedStatement ps ;
    private static Connection conn ;

    /**
     *  批处理sql
     * @param list
     * @throws SQLException
     */
    public static  void updateMassage(List<String> list) throws SQLException {
        ps=conn.prepareStatement("select * from user where id=?");
        int i = 0;
        for (String ids : list){
            i++;
            int id=Integer.parseInt(ids);
            ps.setInt(1,id);
            ps.addBatch();
            if (i%5000==0){
                //返回int[] 第一条sql影响数据库几行，第二条sql影响数据库几行
                ps.executeBatch();
                //发给数据库之后，就要清理批处理
                ps.clearBatch();
                conn.commit();
            }

        }
    }
}
