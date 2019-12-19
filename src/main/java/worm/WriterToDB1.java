package worm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.*;

public class WriterToDB1 {

    static Connection  conn= getConnection();
    static Statement ps;
    static Writer writer;
    {
        try {
            writer = new FileWriter(new File("C:\\Users\\user\\Desktop\\词表"));
        } catch (IOException e) {
            System.out.println("导出报错！！");
        }
    }

    public static void main(String[] args) throws SQLException, IOException {

        //词性
        //String a = args[0];
        String a="nz";

        Write_To_Txt(a);

    }

    public static  void Write_To_Txt(String args) throws SQLException, IOException {

        String  S="select * from x_thesaurus where SPEECH_PART="+"'"+args+"'";
        System.out.println(S);
        ps=conn.createStatement();

        ResultSet rs =ps.executeQuery(S);

        int  count =0;
        while(rs.next()) {

            count++;
            String SEGMENT=rs.getString("SEGMENT");
            String SPEECH_PART=rs.getString("SPEECH_PART");
            String FREQUENCY=rs.getString("FREQUENCY");

            System.out.println("获取成功："+SEGMENT+"\t"+SPEECH_PART+"\t"+FREQUENCY+"");

            try {
                writer.append(SEGMENT+"\t"+SPEECH_PART+"\t"+FREQUENCY+"\t\n");
            } catch (Exception e) {

                System.out.println("错误行数："+(count-1));
                return;
            }


            if (count%1000==0){

                System.out.println(">>写出1000条");

            }

        }
        writer.close();
    }

    private static Connection getConnection() {
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
