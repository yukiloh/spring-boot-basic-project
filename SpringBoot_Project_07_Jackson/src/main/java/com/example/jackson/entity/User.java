package com.example.jackson.entity;

import com.fasterxml.jackson.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/13 20:46
 *
 */
@JsonPropertyOrder({"my-id","password","username","time"})     //定义序列化后属性的顺序
public class User {

    @JsonProperty("my-id")     //序列化时json中的key的别名
    private Integer id;
    private String username;
    private String password;

    //序列化时进行格式转换.比如↓设定时区,设定时间格式.也可以作用于方法上
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date time;

    //用于储存未匹配的属性
    private Map<String,Object> other = new HashMap<>();

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", time=" + time +
                ", other=" + other +
                '}';
    }

    /**
     * 用于给构造函数序列化时使用
     * 貌似使用后,每个参数都需要添加@JsonProperty("xxx"),否则会找不到argument
     */
    @JsonCreator
    public User(
            @JsonProperty("my-id") Integer id
            , @JsonProperty("username")String username
            , @JsonProperty("password") String password
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.time = new Date();
    }

    /**
     * @JsonAnyGetter 获取所有未序列化的属性.和原来的get一样
     */
    @JsonAnyGetter
    public Map<String, Object> getOther() {
        return other;
    }

    /**
     * @JsonAnySetter 设置未反序列化的属性名和值作为键值存储到map中
     * 需要手动进行修改
     */
    @JsonAnySetter
    public void setOther(String key, Object value) {
        this.other.put(key,value);
    }

    public Integer getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
