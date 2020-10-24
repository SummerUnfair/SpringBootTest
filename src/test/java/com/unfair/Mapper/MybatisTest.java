//package com.unfair.Mapper;
//
//import com.unfair.Pojo.User;
//import com.SpringBootTestApplication;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = SpringBootTestApplication.class)
//public class MybatisTest {
//
//    @Autowired
//    DataSource date;
//    @Resource
//    private UserMapper userMapper;
//    @Test
//    public void test(){
//        System.out.println(date);
//        List<User> users=userMapper.findAll();
//        for (User user : users){
//            System.out.println(user);
//        }
//    }
//
//    /**
//     * 测试模糊查询操作
//     */
//    @Test
//    public void testFindByName(){
//
//        List<User> users=userMapper.findByName("%丽");  // "%丽%"
//
//        System.out.println(users);
//    }
//}
