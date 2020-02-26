package com.worm;

import com.Mapper.UserMapper;
import com.Pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisJunitTest {

    InputStream inputStream;
    SqlSession session;
    UserMapper mapper;

    @Before
    public void init(){
        try {
            inputStream = Resources.getResourceAsStream("Mybatis/MybatisConfig.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session = sqlSessionFactory.openSession();
            mapper = session.getMapper(UserMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @After
    public void destroy(){
        try {
            //提交事务
            session.commit();
            session.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试查询所有操作
     */
    @Test
    public void findAllTest(){
        List<User> users=mapper.findAll();
        for (User user : users){
            System.out.println(user);
        }
    }
    /**
     * 测试插入操作
     */
    @Test
    public void saveTest(){
        User user = new User(4,"丽丽");
        //执行保存方法
        mapper.saveUser(user);
        System.out.println("success");
    }
    /**
     * 测试更新操作
     */
    @Test
    public void updateTest(){
        User user = new User(1,"丽丽");
        //执行保存方法
        mapper.updateUser(user);
        System.out.println("success");
    }
    /**
     * 测试删除操作
     */
    @Test
    public void deleteTest(){
        User user = new User();
        mapper.deleteUser(4);
        System.out.println("success");
    }

    /**
     * 测试id查询操作
     */
    @Test
    public void findByIdTest(){
        User user = mapper.findById(3);
        System.out.println(user);
        System.out.println("success");
    }
}
