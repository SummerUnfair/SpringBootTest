//package com.unfair.worm;
//
//import com.unfair.Pojo.User;
//import com.unfair.Util.JdbcUtils;
//import org.junit.Test;
//import redis.clients.jedis.Jedis;
//
//import java.io.*;
//import java.net.Socket;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * 单元测试
// * ctrl shift f10
// */
//public class JunitTest {
//
//
//    /**
//     * hashtable test
//     */
//    @Test
//    public void hashTableTest(){
//        HashMap<Object, Object> map = new HashMap<>();
//
//        map.put(null,"a");
//        map.put("b",null);
//        map.put(null,null);
//
//        System.out.println(map); //{null=null, b=null}
//
//        Hashtable<Object, Object> table = new Hashtable<>();
//
//        table.put(null,"a"); //NullPointerException
//        table.put("b",null);  //NullPointerException
//        table.put("b",null);  //NullPointerException
//
//        System.out.println(table);
//    }
//
//    /**
//     * 递归遍历目录结构
//     */
//    @Test
//    public void getAllFile(){
//
//        //对目录进行遍历
//        File dir = new File("D:\\JAVATesk");
//        File[] files = dir.listFiles();
//        for (File o : files){
//            if (o.isDirectory()){
//
//            }else{
//                System.out.println(o);
//            }
//        }
//    }
//
//
//    /**
//     * hashMap存储自定义类型键值：方式二
//     */
//    @Test
//    public void savaUserMap(){
//        HashMap<User,String> map = new HashMap<>();
//        map.put(new User(1,"莉莉"),"1");
//        map.put(new User(2,"丽丽"),"1");
//        map.put(new User(3,"李丽"),"1");
//        map.put(new User(1,"莉莉"),"1");
//
//        Set<User> set = map.keySet();
//        for (User o : set){
//            System.out.println(o.getId()+":"+o.getUsername()+"--"+map.get(o));
//        }
//    }
//
//
//    /**
//     * hashMap存储自定义类型键值：方式一
//     */
//    @Test
//    public void savaMap(){
//        HashMap<String,User> map = new HashMap<>();
//        map.put("1",new User(1,"丽丽"));
//        map.put("2",new User(1,"莉莉"));
//        map.put("3",new User(1,"李丽"));
//        map.put("1",new User(1,"莉莉"));
//
//        Set<String> set =map.keySet();
//        for (String o : set){
//
//            User usr = map.get(o);
//            System.out.println(o+":"+usr);
//        }
//    }
//
//
//    /**
//     * Map集合的遍历方式:方式二
//     */
//    @Test
//    public void mapEntrySetTest(){
//        Map<String,Integer> map = new HashMap<>();
//        map.put("李林",160);
//        map.put("丽丽",161);
//        map.put("莉莉",162);
//
//        Set<Map.Entry<String,Integer>> set = map.entrySet();
//        Iterator<Map.Entry<String,Integer>> it = set.iterator();
//        while(it.hasNext()){
//            Map.Entry<String,Integer> entry = it.next();
//            String key = entry.getKey();
//            Integer value = entry.getValue();
//            System.out.println(key+"="+value);
//        }
//    }
//
//
//    /**
//     * Map集合的遍历方式:方式一
//     */
//    @Test
//    public void mapKeySetTest(){
//        Map<String,Integer> map = new HashMap<>();
//        map.put("李林",160);
//        map.put("丽丽",161);
//        map.put("莉莉",162);
//        Set<String> set = map.keySet();
//        for (String o : set){
//            int a =  map.get(o);
//            System.out.println(o+" "+a);
//        }
//    }
//
//
//    /**
//     * Map集合ContainsKey(Object key);
//     */
//    @Test
//    public void  mapContainsTest(){
//        Map<String,Integer> map = new HashMap<>();
//        map.put("李林",160);
//        map.put("丽丽",161);
//        map.put("莉莉",162);
//        boolean bi= map.containsKey("莉莉");
//        System.out.println("bi:"+bi);
//    }
//
//
//    /**
//     * 操作redis SortedSet api
//     */
//    @Test
//    public void redisSortedSetTest(){
//        //获取连接
//        Jedis jedis = new Jedis();
//        jedis.auth("123456");
//        //操作
//        //存储
//        jedis.zadd("mysortedset",3,"亚瑟");
//        jedis.zadd("mysortedset",20,"后裔");
//        jedis.zadd("mysortedset",10,"孙悟空");
//        //获取
//        Set<String> mysortedset =jedis.zrange("mysortedset",0,-1);
//        System.out.println(mysortedset);
//        //关闭连接
//        jedis.close();
//    }
//
//    /**
//     * 操作redis List api
//     */
//    @Test
//    public void redisListTest(){
//        //获取连接 如果使用空参数构造，默认值"localhost",6379
//        Jedis jedis = new Jedis();
//        jedis.auth("123456");
//        //操作
//        //存储
//        jedis.lpush("mylist","a","b","c");  //从左边存
//        jedis.rpush("mylist","a","b","c"); //从右边存
//        //获取
//        List<String> mylist = jedis.lrange("mylist",0,-1);
//        System.out.println(mylist);
//        //list 弹出
//        String element1 = jedis.lpop("mylist"); //c
//        System.out.println(element1);
//        //list 弹出
//        String element2 = jedis.rpop("mylist"); //c
//        System.out.println(element1);
//        //获取
//        List<String> mylist2 = jedis.lrange("mylist",0,-1);
//        System.out.println(mylist2);
//        //关闭连接
//        jedis.close();
//    }
//
//    /**
//     * 操作redis Hash api
//     */
//    @Test
//    public void redisHashTest(){
//        //获取连接
//        Jedis jedis = new Jedis();
//        jedis.auth("123456");
//        //操作
//        //存储
//        jedis.hset("user","name","lisi");
//        jedis.hset("user","age","23");
//        jedis.hset("user","gender","male");
//        //获取
//        String username =jedis.hget("user","name");
//        System.out.println(username);
//
//        //获取hash的所有map中的数据
//        Map<String,String> user = jedis.hgetAll("user");
//        Set<String> keySet =user.keySet();
//        for (String key : keySet){
//            String value =user.get(key);
//            System.out.println(key+":"+value);
//        }
//        //关闭连接
//        jedis.close();
//    }
//
//
//    /**
//     * 操作redis String api
//     */
//    @Test
//    public void redisStrTest(){
//        //获取连接
//        Jedis jedis = new Jedis();
//        jedis.auth("123456");
//        //操作
//        //存储
//        jedis.set("username","zhangsan");
//        //获取
//        String username =jedis.get("username");
//        System.out.println(username);
//
//        //可以使用setex()方法可以指定存储有过期时间的key vlaue
//        //20秒后自动删除
//        jedis.setex("user",20,"hehe");
//        //关闭连接
//        jedis.close();
//    }
//
//
//    /**
//     * 迭代器取出单列集合元素
//     */
//    @Test
//    public void CollectionTest(){
//
//        Collection<String>   coll = new ArrayList<>();
//        System.out.println(coll);   //重写了toString方法   []
//        coll.add("黎明");
//        System.out.println(coll);
//        System.out.println(coll.contains("黎明"));   //true
//        coll.add("李明");
//        Iterator<String> it =  coll.iterator();
//        while(it.hasNext()){
//            String e = it.next();
//            System.out.println(e);
//        }
//    }
//
//    /**
//     * JdbcUtils工具类的使用
//     */
//    @Test
//    public void connectionTest(){
//        Connection conn =null;
//        PreparedStatement ps =null;
//        try {
//            conn = JdbcUtils.getConnection();
//            String sql = "insert into emp values (?,?,?)" ;
//            ps = conn.prepareStatement(sql);
//            ps.setString(1,"2");
//            ps.setString(2,"张三");
//            ps.setString(3,"男");
//            int count = ps.executeUpdate();
//            System.out.println(count);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            JdbcUtils.close(ps,conn);
//        }
//    }
//
//    /**
//     * 文本对比不同,进行输出
//     */
//    @Test
//    public void compareTxtTest(){
//
//        Map<String, Integer> map = new HashMap<String, Integer>();
//        try {
//            //主文本
//            Reader in = new FileReader(new File("C:\\Users\\user\\Desktop\\text2.txt"));
//            BufferedReader reader = new BufferedReader(in);
//            String s = "";
//            while ((s = reader.readLine()) != null) {
//                map.put(s, 1);
//            }
//            reader.close();
//            //对比文本
//            in = new FileReader(new File("C:\\Users\\user\\Desktop\\text1.txt"));
//            reader = new BufferedReader(in);
//            s = "";
//            while ((s = reader.readLine()) != null) {
//                //输出不同
//                if (!map.containsKey(s)) {
//                    System.out.println(s);
//                }
//            }
//            reader.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Java正则处理字符串
//     */
//    @Test
//    public void  PatternTest(){
//
//        String str = "(  ( GLDWH = '14000' )  )";
//        Pattern pattern = Pattern.compile("([\'\"])(.*?)\\1");
//        Matcher matcher = pattern.matcher(str );
//        if (matcher.find()) {
//            String collegeId = matcher.group(2);
//            System.out.println(collegeId);//14000
//        }
//    }
//
//    /**
//     * Java正则处理字符串
//     */
//    @Test
//    public void  PatternPro(){
//
//        Pattern p = Pattern.compile("\\w+");
//
//        Matcher m = p.matcher("asdfaw&&e124");
//
//        System.out.println(m.find());
//        System.out.println(m.group());
//        System.out.println(m.find());
//        System.out.println(m.group());
//        System.out.println(m.find());
//        System.out.println(m.find());
//
//    }
//    /**
//     * Properties配置文件读取
//     */
//    @Test
//    public void PropertiesTest(){
//
//        try {
//            Properties pr = new Properties();
//            InputStream inStream = JunitTest.class.getClassLoader().getResourceAsStream("initResource.properties");
//            //InputStream inStream = new FileInputStream("initResource.properties");
//            pr.load(inStream);
//            String URL = pr.getProperty("URL");
//            System.out.println(URL);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
////    public static boolean isNumber(String string) {
////        if (string == null)
////            return false;
////        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
////        return pattern.matcher(string).matches();
////    }
//
//    public static void main(String[] args) throws IOException {
//
//
//
//
////        System.out.println(String.class.getSimpleName());// String
////        // 得到类的全名称（包含所在的包名）
////        System.out.println(String.class.getName());// java.lang.String
//
////        String AddressName="12弄12支弄12号";
//////        int addressINDEX = AddressName.indexOf("弄");
//////        String N = AddressName.substring(0, addressINDEX); //获取弄级地址
//////        System.out.println(N);
//////        int addressINDEX0=AddressName.indexOf("支弄");
//////        String N1 = AddressName.substring(addressINDEX, addressINDEX0); //获取弄级地址
//////        System.out.println(N1);
////        int addressINDEX = AddressName.indexOf("弄");
////
////        String a=AddressName.substring(addressINDEX);
////        System.out.println(a);
//
//
//
//
////        String str = "(  ( GLDWH = '14000' )  )";
////
////        Pattern pattern = Pattern.compile("([\'\"])(.*?)\\1");
////        Matcher matcher = pattern.matcher(str );
////        if (matcher.find()) {
////            String collegeId = matcher.group(2);
////
////            System.out.println(collegeId);//14000
////        }
////        String str = "12弄12支弄12号";
////
////        Pattern pattern = Pattern.compile(".+弄(.+支弄)*(.+号)*(.+)*");
////        Matcher matcher = pattern.matcher(str);
////        if (matcher.find()) {
////            String collegeId = matcher.group(1);
////
////            System.out.println(collegeId);//14000
////        }
//
////// TODO Auto-generated method stub
////        String str = "Hello,World! in Java.";
////        Pattern pattern = Pattern.compile("W(or)(ld!)");
////        Matcher matcher = pattern.matcher(str);
////        while(matcher.find()) {
////            System.out.println("Group 0:" + matcher.group(0));//得到第0组——整个匹配
////            System.out.println("Group 1:" + matcher.group(1));//得到第一组匹配——与(or)匹配的
////            System.out.println("Group 2:" + matcher.group(2));//得到第二组匹配——与(ld!)匹配的，组也就是子表达式
////            System.out.println("Start 0:" + matcher.start(0) + " End 0:" + matcher.end(0));//总匹配的索引
////            System.out.println("Start 1:" + matcher.start(1) + " End 1:" + matcher.end(1));//第一组匹配的索引
////            System.out.println("Start 2:" + matcher.start(2) + " End 2:" + matcher.end(2));//第二组匹配的索引
////            System.out.println(str.substring(matcher.start(0), matcher.end(1)));//从总匹配开始索引到第1组匹配的结束索引之间子串——Wor
//
////        String Building="123弄";
////        boolean isMatch= Pattern.matches("\\d+(支弄[0-9]+号|[弄号]|[甲乙丙丁]号|识别号)",Building);
////        System.out.println(isMatch);
//
////        String budings="1019弄42号后门";
////        int a =budings.indexOf("号");
////        String name=budings.substring(0,a+1);
////        System.out.println(budings+"\t"+name);
//
//
////        String reBuilding=Building.replace("号","");
////       System.out.println(Building+"号");
//
//
//            //Pattern p=Pattern.compile("\\w+");
//            //p.pattern();//返回 \w+
//            // 按指定模式在字符串查找
//            //System.out.println(p.pattern());
//            //Matcher m= p.matcher(Building);
//
//            //第一个参数正则，第二个参数字符串，返回不布尔类型
//
//            System.out.println("=====================");
////        String line = "This order was placed for QT3000! OK?";
////        // 创建 Pattern 对象
////        Pattern r = Pattern.compile("(\\D*)(\\d+)(.*)");
////        // 现在创建 matcher 对象
////        Matcher m = r.matcher(line);
////        if (m.find()) {
////            System.out.println("Found value: " + m.group(0) );
////            System.out.println("Found value: " + m.group(1) );
////            System.out.println("Found value: " + m.group(2) );
////            System.out.println("Found value: " + m.group(3) );
////        } else {
////            System.out.println("NO MATCH");
////        }
////       String str="wer1389980000ty1234564uiod234345675f";
////       String reg="\\d{5,}";
////       String arr="#";
////       String a=str.replaceAll(reg,arr);
////
////       System.out.println(a);
//
////        String str = "erkktyqqquizzzzzo";
////        String reg = "(.)\\1+";
////        String[] arr = str.split(reg);
////        System.out.println("切割得到的长度是:" + arr.length);
////        for(final String s : arr) {
////            System.out.println(s);
////        }
//
//
////        System.out.println(isNumber("580"));
////        System.out.println(isNumber("5234254125424584"));
////        System.out.println(isNumber("dfg15s4df5sd1fds"));
//
////        count++;
////        System.out.println(count);
////
//
////        int type=1;
////
////        if (type==1){
////
////            System.out.println("1");
////        }
//
//
////        String c="4269";
////        Object a=4269;
////        String b= a+"";
////        if (b.equals(c)){
////
////            System.out.println("相同");
////
////        }
//
//
////        String addressName="SaddrTermSaddrTerm";
////
////        int len =addressName.split("addrTerm").length;
////
////        System.out.println(len);
////        String []a=addressName.split("addrTerm");
////        for (String b: a){
////            System.out.println("b:"+b);
////        }
//
//            //String addressName="\\|%\\|";
////        String name ="null";
////        String addressName="|@|"+name;
////        System.out.println(addressName);
//
////        int type=-1;
////        type=2;
////
////        if (type==1){
////            System.out.println("相同");
////        }else{
////            System.out.println("不相同");
////        }
//
//        }
//
//}
