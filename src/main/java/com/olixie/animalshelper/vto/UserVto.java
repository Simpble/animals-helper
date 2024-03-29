package com.olixie.animalshelper.vto;

import com.olixie.animalshelper.entity.User;
import lombok.Data;

@Data
/*用于用户注册时提交申请时接受数据*/
public class UserVto {
    private User user;
    private String code;
}
