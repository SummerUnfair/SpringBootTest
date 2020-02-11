package ThreadTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectionTest {
    
    public static void main (String[] args) throws Exception {

        //导入驱动Jar包
        //注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //获取数据库连接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db3","root","root");
        //定义sql
        String sql = "update accout set balance =2000 where id =1";
        //获取执行sql的对象 Statement
        Statement stmt =conn.createStatement();
        //执行sql
        int count =stmt.executeUpdate(sql);
        //处理结果
        System.out.println(count);
        //释放资源
        stmt.close();
        conn.close();
    }
    
}
