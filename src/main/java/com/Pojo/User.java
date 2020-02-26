package com.Pojo;

import java.io.Serializable;
import java.util.Objects;

public class User implements  Comparable<User>, Serializable {

    private int id ;
    private String username;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    public User() {
    }

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //重写排序的规则
    @Override
    public int compareTo(User o) {
        //return 0;  //认为元素都是相同的
        //自定义比较的规则，比较两个人的年龄（this,参数User）
        //return o.getId() - this.getId();   //年龄降序
        return this.getId() - o.getId();     //年龄升序
    }
}
