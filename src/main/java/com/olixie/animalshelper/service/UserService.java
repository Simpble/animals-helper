package com.olixie.animalshelper.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.olixie.animalshelper.entity.User;
import com.olixie.animalshelper.vto.UserVto;

public interface UserService extends IService<User> {
    Boolean register(UserVto userVto);
}
