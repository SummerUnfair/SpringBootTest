package Mapper;

import Pojo.User;

import java.util.List;

/**
 * 用户持久层接口
 */
public interface UserMapper {

    /**
     * 查找所有用户
     * @return
     */
    List<User> findAll();

    /**
     * 保存用户
     */
    void saveUser(User user);

}
