package Mapper;

import Pojo.User;

import java.util.List;

public interface UserMapper {

    /**
     * 查找所有用户
     * @return
     */
    List<User> findAll();
}
