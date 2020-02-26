package com.Mapper;

import com.Pojo.User;
import com.SpringBootTestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestApplication.class)
public class MybatisTest {

    @Resource
    private UserMapper userMapper;
    @Test
    public void test(){
        List<User> users=userMapper.findAll();
        for (User user : users){
            System.out.println(user);
        }
    }
}
