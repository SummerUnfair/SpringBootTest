package com.InterfaceTest;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Information sources：数据库表
 * Output source：文本 FTP服务器
 * function:数据表中得到数据集写入TXT中，上传至FTP服务器
 * Created by FH on 2019/09/10.
 */

public class HandleOfFtpWriter_initial_version {

    static  Connection conn = getConnection();
    static  Statement statement;

    {
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String sqlString;
    private static String path;

    public static String url;
    public static String user;
    public static String password;

    public static String ftpServer;
    public static String ftpUserName;
    public static String ftpPassword;
    public static String ftpPath;

    String datefm="";
    String dateNow="";

    public static void main(String[] args) {

        String sqlpath="select * from addr_tags a where a.building_tag is not null or a.government_enterprise_tag is not null";

        writeToTxt(sqlpath);

        deal();

    }

    public static void deal() {

        HandleOfFtpWriter_initial_version addrInfoWriteToTxt = new HandleOfFtpWriter_initial_version();
        addrInfoWriteToTxt.initConfig();

        addrInfoWriteToTxt.uploadToFtp();      //2.上传到ftp
        // addrInfoWriteToTxt.uploadToFtpByEndFile();      //2.上传到ftp
    }

    public void uploadToFtp(){
        FTPClient ftp = new FTPClient();
        try {
            //连接ftp服务器，即ftp://10.7.74.90/
            ftp.connect("10.7.6.118");
            ftp.login("u_gis", "eda-dh99");

            //获得ftp返回的代码
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.out.println("连接失败");
                return;
            }

            System.out.println("ftp连接成功");

            String localpath = "C:\\Users\\user\\Desktop\\"+"gis_tags_addr_inc_"+datefm+".txt";
            FileInputStream fis= new FileInputStream(localpath);
            int index= ftpPath.lastIndexOf("//");//获取路径的最后一个斜杠位置
            //
            ///////////////把上传的文件名改为原文件名//////////////
            int  filenameplace= localpath.lastIndexOf(".");//获取上传文件类型中'.'的位置
            String filename= localpath.substring(localpath.lastIndexOf("\\")+1,filenameplace);//获取原路径的文件名
            String fileType= localpath.substring(filenameplace);//获取源文件的类型
            ////////////////////////
            if (-1 != index){//
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);//设置上传文件的类型

                ftp.changeWorkingDirectory(ftpPath.substring(0,index));//获取上传的路径地址
                System.out.println("路径"+ftpPath.substring(0,index));
                //修改上传的文件名,保持上传后文件名一致
                boolean res=ftp.storeFile(filename+fileType,fis);
                System.out.println(res);
                if(res){
                    System.out.println("上传成功");
                }
            }
            fis.close();//关闭流

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected())
                try {
                    ftp.disconnect();
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }
        }
    }

    public void uploadToFtpByEndFile(){
        FTPClient ftp = new FTPClient();
        try {
            //连接ftp服务器，即ftp://10.7.74.90/
            ftp.connect("10.7.6.118");
            ftp.login("u_gis","eda-dh99");

            //获得ftp返回的代码
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.out.println("连接失败");
                return;
            }

            System.out.println("ftp连接成功");

            String localpath = "C:\\Users\\user\\Desktop\\"+"gis_tags_addr_inc_"+datefm+".txt";
            FileInputStream fis= new FileInputStream(localpath);
            int index= ftpPath.lastIndexOf("/");//获取路径的最后一个斜杠位置

            //源文件，文件名，文件类型
            int  filenameplace= localpath.lastIndexOf(".");//获取上传文件类型中'.'的位置
            String filename= localpath.substring(localpath.lastIndexOf("/")+1,filenameplace);//获取原路径的文件名
            String fileType= localpath.substring(filenameplace);//获取源文件的类型
            ////////////////////////
            if (-1 != index){//
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);//设置上传文件的类型

                ftp.changeWorkingDirectory(ftpPath.substring(0,index));//获取上传的路径地址
                System.out.println("路径"+ftpPath.substring(0,index));

                //修改上传的文件名,保持上传后文件名一致
                boolean res=ftp.storeFile(filename+fileType,fis);
                System.out.println(res);
                if(res){
                    System.out.println("上传成功");
                }
            }
            fis.close();//关闭流

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected())
                try {
                    ftp.disconnect();
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }
        }
    }

    public  void initConfig(){

        //获取key对应的value值
        sqlString ="select * from addr_tags a where a.building_tag is not null or a.government_enterprise_tag is not null";
        path = "C:\\Users\\user\\Desktop\\";

        url = "jdbc:oracle:thin:@10.7.100.15:1521:gis";
        user = "GIS";
        password = "4WY_Md1BE28";

        ftpServer ="10.7.6.118";
        ftpUserName ="u_gis";
        ftpPassword = "eda-dh99";
        ftpPath = "//ftpdata//receive/eda_in//in_1410//";

        datefm=getNowTime();
        dateNow=getNowTime();

        System.out.println("datefm:"+datefm);
        System.out.println("dateNow:"+dateNow);

    }
    //获取当月
    public static String getNowTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        String lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }
    //获取下个月
    public static String getPreMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, 1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        String preMonth = dft.format(cal.getTime());
        return preMonth;
    }
    //获取上个月
    public static String getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, -1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        String lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }


    public static  void writeToTxt(String sqlString) {

        //设置日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        String datefm = sdf.format( new Date());

        System.out.println(datefm);

        try (

                Writer writer=new FileWriter(new File("C:\\Users\\user\\Desktop\\\"+\"gis_tags_addr_inc_\"+datefm+\".txt"));

                PrintWriter out = new PrintWriter(writer);

                ResultSet rs = statement.executeQuery(sqlString)
        ) {
            //out.write("我会写入文件啦2\r\n"); // \r\n即为换行
            int count = 0;
            while (rs.next()) {

                count++;
                int ADDRESS_ID=rs.getInt("ADDRESS_ID");
                String building_tag=rs.getString("building_tag");
                String government_enterprise_tag = rs.getString("government_enterprise_tag");

                int index1=-1;
                int index2=-1;
                if(building_tag!=null) {
                    index1=1;
                }else {
                    index1=0;
                }if(government_enterprise_tag!=null) {
                    index2=1;
                }else {
                    index2=0;
                }

                out.write(ADDRESS_ID+"|@|"+index1+"|@|"+index2+"\r\n");

                if(count%1000==0){

                    // 把缓存区内容压入文件
                    out.flush();
                    System.out.println("正在写入："+count+"条");

                }
            }

            System.out.println("总写入数量："+count);
            System.out.println( new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(Calendar.getInstance().getTime()));

            // 把缓存区内容压入文件
            out.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取数据库连接
     */
    private static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@10.7.100.15:1521:gis", "GIS", "4WY_Md1BE28");
            conn.setAutoCommit(false);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return conn;
        }
    }
}
