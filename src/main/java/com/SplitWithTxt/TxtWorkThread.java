package com.SplitWithTxt;

import com.Util.SmartMatch;

import java.io.*;
import java.sql.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 处理文本信息（Y）(N)刷入标签表
 * @fenghao
 */
public class TxtWorkThread {

    public static String path1 = "C:\\Users\\user\\Desktop\\政企低资管控.txt";
    public static String path2 = "C:\\Users\\user\\Desktop\\无法查询.txt";
    public static ConcurrentLinkedQueue<String>  out_queue = new ConcurrentLinkedQueue<String>();
    public static Writer writer;

    static {

        try {

            writer = new FileWriter(new File(path2));

        } catch (IOException w) {

            w.printStackTrace();

        }

    }

    public static void main(String[] args) {

        TxtWorkThread t = new TxtWorkThread();

        try {

            t.activate();

        }

        catch(IOException x) {

            x.printStackTrace();

        }

    }

    public  void activate() throws IOException
    {
        //地址全称存入队列
        this.inQueue(path1);

        RestoreQueueThread[] thds = new RestoreQueueThread[10];
        for(int i = 0; i< 10;i++)
        {
            thds[i] = new RestoreQueueThread();
            thds[i].start();
        }

        try
        {
            for(int i = 0; i< 10;i++)
            {
                thds[i].join();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把文件读进队列的函数
     * 对文件信息进行处理
     * @param path1
     */
    public void inQueue(String path1) throws IOException {

        //传入指定的文件
        Reader in = new FileReader(new File(path1));
        //缓冲区就是内存里的一块区域，把数据先存内存里，然后一次性写入
        BufferedReader reader = new BufferedReader(in);
        String dealaddress = "";

        String[] arr;
        String addressName = null;

        while ((dealaddress = reader.readLine()) != null) {
            if (dealaddress.trim().length() == 0) continue;

            if(dealaddress.lastIndexOf("Y")==dealaddress.length()-1 ) {
                arr = dealaddress.split("\\|%\\|");

                //分离得到地址全称
                addressName = arr[1];

                out_queue.add(addressName);
            }

        }
        reader.close();
    }

    //对队列中的信息取出操作
    private class RestoreQueueThread extends Thread {

//        public RestoreQueueThread() {
//            //out_queue.size();
//
//        }

        Connection  conn = getConnection();
        PreparedStatement  ps;

        //只有{...}的是叫做构造块,只要建立一个对象，构造代码块都会执行一次。
        {
            try {
                ps = conn.prepareStatement(" merge into ADDR_TAGS_TEST T1 " +
                        "      using(select ? as address_id, '{\"tagCode\":10002,\"tagLevel\":2,\"tagValue\":\"政企低资费管控套餐\",\"tag_type_id\":\"100\",\"tag_type_value\":\"面向前端相关标签\"}'as GOVERNMENT_ENTERPRISE_TAG from dual) T2 on(T1.address_id=T2.address_id)  " +
                        "      when matched then update set T1.GOVERNMENT_ENTERPRISE_TAG=T2.GOVERNMENT_ENTERPRISE_TAG  " +
                        "      when not matched then insert (T1.ADDRESS_ID,T1.GOVERNMENT_ENTERPRISE_TAG) values (T2.address_id,T2.GOVERNMENT_ENTERPRISE_TAG)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void run() {

            int count =0; //计数 到1000

            //从队列里循环取
            while (!out_queue.isEmpty()) {
                count++;
                String adrName = out_queue.poll();
                System.out.println("长度："+out_queue.size());

                if(adrName==null&&adrName.trim().length()==0){
                    continue;
                }

                //获得标准地址id
                String id = "";
                try {
                    id = SmartMatch.getStandard_id(adrName);
                }catch (Exception e){

                }

                if (id == null || id.trim().length() == 0) {

                    try {
                        writer.append(adrName + "\r\n");
                        //FileWriter的flush()方法是从OutputStreamWriter中继承来的,其作用就是清空缓冲区并完成文件写入操作的。
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                        continue;

                }
                if (id!=null){
                    int id_num = Integer.parseInt(id);
                    try {
                        ps.setInt(1, id_num);
                        ps.addBatch();

                        if(count%1000==0) {
                            ps.executeBatch();
                            conn.commit();
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                ps.executeBatch();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        /**
         * 获取数据库连接
         */
        private  Connection getConnection() {
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
}