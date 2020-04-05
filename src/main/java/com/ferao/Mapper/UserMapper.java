package com.ferao.Mapper;

import com.ferao.Pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户持久层接口
 */
@Mapper
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

    /**
     * 更新用户
     */
    void updateUser(User user);

    /**
     * 根据id删除用户
     */
    void deleteUser(Integer userId);
    /**
     * 根据id查询用户信息
     */
    User findById(Integer userId);

    /**
     * 根据名称模糊查询用户信息
     */
    List<User> findByName(String username);
}
