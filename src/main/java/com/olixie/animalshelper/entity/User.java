package com.olixie.animalshelper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //使用TableId注解定义表的主键
    @TableId
    private Integer uid;
    private String username;
    private String password;
    private String nickName;
    private String email;
    private String photo;
    private String birthday;
    private Integer sex;
    private String phone;
    private String categoryId;
    private String salt;
    private String address;
    private Integer isDelete;
    @TableField(exist = false)
    private String token;

    public User(String username, String password, String nickName, String email, String address) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.address = address;
        this.photo = "male.png";
    }

    public User(String username, String password, String nickName, String email, String address, Integer sex) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.address = address;
        this.sex = sex;
        this.photo = "male.png";
    }
}
