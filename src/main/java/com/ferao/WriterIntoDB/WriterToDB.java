package com.ferao.WriterIntoDB;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WriterToDB {

    static List<String> list ;
    static Connection  conn= getConnection();
    static Statement ps;
    static Statement st;
    static String path_name=null;
    static Writer writer = null;

    public static void main(String[] args) throws SQLException, IOException {

        //path_name="C:\\Users\\user\\Desktop";
        path_name=args[0];
        get_Type();

    }

    public static  void get_Type() throws SQLException, IOException {

        String  get_Tyep_sql="select distinct speech_part from x_thesaurus ";

        st=conn.createStatement();

        ResultSet rs = st.executeQuery(get_Tyep_sql);

        list=new ArrayList<String>();
        while (rs.next()){

            list.add(rs.getString("speech_part"));   ;

        }

        //System.out.println(list.size());

        for(String a: list){

            try {

                writer =new FileWriter(new File(path_name+"\\"+"sh_"+a+".txt"));

                System.out.println("文本创建成功！");
            } catch (IOException e) {

                System.out.println("创建失败！");
            }
            System.out.println("写出"+a+"词性");
            Write_To_Txt(a);

        }

    }

    public static  void Write_To_Txt(String a) throws SQLException, IOException {

        String  S="select * from x_thesaurus where SPEECH_PART="+"'"+a+"'";

        ps=conn.createStatement();

        ResultSet rs =ps.executeQuery(S);

        int  count =0;
        while(rs.next()) {

            count++;
            String SEGMENT=rs.getString("SEGMENT");
            String SPEECH_PART=rs.getString("SPEECH_PART");
            String FREQUENCY=rs.getString("FREQUENCY");

            //System.out.println("获取成功："+SEGMENT+"\t"+SPEECH_PART+"\t"+FREQUENCY+"");

            try {

                writer.append(SEGMENT + " " + SPEECH_PART + " " + FREQUENCY + "\n");
            }catch (Exception e){
                System.out.println("未获取到值！");
            }
            if (count%5000==0){

                System.out.println(">>写出5000条");

            }

        }
        System.out.println(">>总写出："+count+"条");
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
