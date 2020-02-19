package Pojo;

import java.io.Serializable;

public class User implements  Comparable<User>, Serializable {

    private int id ;
    private String username;

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
