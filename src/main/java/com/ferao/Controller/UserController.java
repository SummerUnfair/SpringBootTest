package com.ferao.Controller;

import com.ferao.Mapper.UserMapper;
import com.ferao.Pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping
public class UserController {

    @Resource
    private UserMapper userMapper;

    @ResponseBody
    @RequestMapping("/hello")
    public List helloTest() {
        //Map<String,List<User>> map = new HashMap<>();
        List<User> users = userMapper.findAll();
        for (User user : users) {
            System.out.println(user);
        }
        //map.put("msg",users);
        return users;
    }
}