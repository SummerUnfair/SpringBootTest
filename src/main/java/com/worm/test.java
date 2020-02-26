//package com.worm;
//
//import com.Util.HttpClientUtil;
//
//import java.sql.*;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
//public class test {
//
//    public  static ConcurrentLinkedQueue<Map<String, Object>> datalist = new ConcurrentLinkedQueue<>();
//
//    /**
//     * 获取数据库连接
//     */
//    private static Connection getConnection() {
//        Connection conn = null;
//        String url = "jdbc:oracle:thin:@10.145.206.20:1521:gis";
//        String user = "GIS";// 用户名,系统默认的账户名
//        String password = "M3fXy_TFaUt";// 你安装时选设置的密码
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
//
//    //获取地址
//    public ConcurrentLinkedQueue<Map<String, Object>> getaddr(int min, int max) {
//        ConcurrentLinkedQueue<Map<String, Object>> data = datalist;
//        PreparedStatement pre = null;
//        ResultSet resultSet = null;
//        String sql = "select * from TMP_DEVICE_ADDRESS where INT_ID between   "+min+" and "+max;
//        Connection conn = getConnection();
//        try {
//            pre = conn.prepareStatement(sql);
//            resultSet = pre.executeQuery();
//            while (resultSet.next()) {
//                // 当结果集不为空时
//                Map map = new HashMap<>();
//                String full_name = resultSet.getString("full_name");
//                map.put("FULL_NAME", full_name);
//                map.put("ID",   resultSet.getString("id"));
////                System.out.println(full_name);
//                datalist.add(map);
//            }
//            resultSet.close();
//            pre.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (conn != null) {
//                try {
//                    //关闭连接
//                    conn.close();
//
//                } catch (Exception e) {
//                    System.out.println("resultSet close failed !!!");
//                    e.printStackTrace();
//                }
//            }
//        }
//        return datalist;
//    }
//
//    public static void main(String[] args) {
//        int min = 0;
//        int max =1000;// 1;//4123526;
//        test t = new test();
//        long start = System.currentTimeMillis();
//        int count =0;
//        while (min < max) {
//            //生产
//            t.getaddr(min, min + 1000);
//            Thread[] threads = new Thread[12];
//       Connection    conn = getConnection();
//            PreparedStatement     statement = null;
//            try {
//                statement = conn.prepareStatement("insert into TMP_DEVICE_ADDRESS_new values (?,?,?)");
//
//            //消费
//            if (!datalist.isEmpty()) {
//                for (int i = 0; i < 12; ++i) {
//                    //新建一个线程
//                    ChangeThread ct =
//                            new ChangeThread(datalist,conn,statement,count);
//                    //把线程放进线程池
//                    threads[i] = ct;
//                    ct.start();
//                }
//                for (Thread thread : threads) {
//                    try {
//                        thread.join();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//                statement.executeBatch();
//                conn.commit();
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }finally {
//
//                try {
//                    conn.close();
//                    statement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//            min += 10000;
//            System.out.println(min/10000);
//
//        }
//        long end = System.currentTimeMillis();
//        long l = end - start;
//        System.out.println(l / 1000);
//    }
//
//    static class ChangeThread extends Thread {
//        //创建线程时，会初始化该变量
//        ConcurrentLinkedQueue<Map<String, Object>> data;
//        Connection conn = null;
//        PreparedStatement preparedStatement = null;
//        int count = 0;
//        public ChangeThread(ConcurrentLinkedQueue<Map<String, Object>> addrs,Connection conn,PreparedStatement preparedStatement,int count) {
//            this.data = addrs;
//            this.count = count;
//            this.conn = conn;
//            this.preparedStatement = preparedStatement;
//        }
//
//        @Override
//        public void run() {
//            String ex = "";
//            String full_name="";
//            String area_name="";
//            String url_fname = "";
//
//            try {
//
//            while (!data.isEmpty()){
//                Map<String, Object> poll = this.data.poll();
//                System.out.println(data.size());
//                full_name = (String) poll.get("FULL_NAME");
//                url_fname = formatname(full_name);
//
//
//                    String url = "http://10.145.206.21:8089/addrselection/addressIndex/queryAreaByAddress.do?addr_full=" +url_fname;
////                    System.out.println(url);
////                    System.out.println(full_name + "  " + area_name);
//                    ex = HttpClientUtil.get(url);
//
//                area_name = getarea(ex);
//                String sql = "update TMP_DEVICE_ADDRESS set area = '"+area_name+"' where full_name = '"+full_name+"'";
//
//               // PreparedStatement  statement = conn.prepareStatement(sql);
//               if(area_name!=null &&!area_name.equals(""))
//                into(preparedStatement,area_name,poll);
//               count++;
//               if(count%1000==0){
//                   preparedStatement.executeBatch();
//                   conn.commit();
//               }
////                writein(id, full_name, area_name);
////                System.out.println(full_name + "  " + area_name);
//
//            }
//            } catch (Exception e3) {
//                e3.printStackTrace();
//            }finally {
////                try {
////                     conn.close();
////                      statement.close();
////                } catch (SQLException e) {
////                    e.printStackTrace();
////                }
//            }
//        }
//
//        //full_name 格式化
//        public String formatname(String full_name){
//            full_name =full_name.trim();
//            full_name = full_name.replaceAll("　", "");
//            full_name = full_name.replaceAll(" ", "");
//            full_name = full_name.replaceAll("\n", "");
//            full_name = full_name.replaceAll("#","%23");
//            full_name = full_name.replaceAll("&","%26");
//            full_name = full_name.replaceAll("\\(","%28");
//            full_name = full_name.replaceAll("\\)","%29");
//            full_name = full_name.replace("    ","");
//            full_name = full_name.replace("\\\"","%22");
//            return full_name;
//        }
//
//        //获取area_name
//        private String getarea(String ex) {
//            String area_name = (String) JsonToString.parseJSON2Map(ex).get("AREA_NAME");
//            return area_name;
//        }
//
//        //写入地址+区
//        public void into( PreparedStatement statement,String area,Map  poll){
//          //  Connection conn = null;
//           // PreparedStatement statement = null;
//            try {
//               // conn = getConnection();
//               // String sql = "update TMP_DEVICE_ADDRESS set area = '"+area+"' where full_name = '"+full_name+"'";
//              //  statement = conn.prepareStatement(sql);
//                statement.setString(1, (String) poll.get("ID"));
//                statement.setString(2, (String) poll.get("FULL_NAME"));
//                statement.setString(3, area);
//                statement.addBatch();
//                //statement.executeUpdate(sql);
//               // System.out.println(area+" "+full_name);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//
////        //写入txt
////        public void writein(long id, String full_name, String area_name) {
////
////            try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
////                /* 写入Txt文件 */
////                FileOutputStream fs = null;
////                File file = new File("C:\\Users\\ASUS\\Desktop\\getarea.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
////                String s = id + " " + full_name + " " + area_name;
////                if(!file.exists()){
////                    file.createNewFile(); // 创建新文件
////                    fs = new FileOutputStream(file);
////                }else {
////                    fs = new FileOutputStream(file,true);
////                }
////                OutputStreamWriter osw = new OutputStreamWriter(fs, "UTF-8");//指定以UTF-8格式写入文件
////
////                osw.write(s+"\r\n");
////                osw.close(); // 最后记得关闭文件
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
//    }
//
//}