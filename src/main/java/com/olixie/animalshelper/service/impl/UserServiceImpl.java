package com.olixie.animalshelper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.olixie.animalshelper.entity.User;
import com.olixie.animalshelper.mapper.UserMapper;
import com.olixie.animalshelper.service.UserService;
import com.olixie.animalshelper.util.*;
import com.olixie.animalshelper.vto.UserVto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${props.upload-folder}")
    private String UPLOAD_FOLDER;

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil redisUtil;

    /*用户进入登录流程*/
    public User passwordLogin(User user) {
        if (user == null || user.getUsername() == null) {
            return null;
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        //根据用户名从数据库查询，若数据库当中存在进行下一步，即用户名校验
        User dbUser = userMapper.selectOne(queryWrapper);
        if (dbUser == null) {
            return null;
        }
        /*将用户传给我的密码经过md5加密而后进行比较*/
        String userPassword = Md5Util.md5Hex(user.getPassword(),dbUser.getSalt());
        /*密码校验*/
        if (dbUser.getPassword().equals(userPassword)) {
            //登录成功后需返回token给前端
            String token = JwtUtil.getTokenOfUid(dbUser.getUid());
            dbUser.setToken(token);
            return dbUser;
        }
        return null;
    }

    public User getUserInfo() {
        /*丛redis当中获取信息，redis当中不存在丛数据库获取*/
//        String jsonUser = redisUtil.get(TokenHolder.getToken());
//        if(jsonUser == null){
//            return null;
//        }
//        if(jsonUser.equals("updated")){
//            String username = TokenHolder.parseToken();
//            User user = userMapper.selectOne(new LambdaQueryWrapper<User>().
//                    eq(User::getUsername, username));
//            if (user == null){
//                log.error("出现错误：获取未存在的用户信息!");
//                return null;
//            }
//            redisUtil.set(TokenHolder.getToken(),JSONUtil.toJsonStr(user));
//            return user;
//        }
//        return JSONUtil.toBean(jsonUser,User.class);

        Integer uid = TokenHolder.parseToken();
        User user = userMapper.selectById(uid);
        return user;
    }

    public boolean updateUserInfo(User user) {
        /*为了避免图片存储位置的数据膨胀，每当用户更新头像时需要将旧的头像存储地址删除。*/
        if (user.getPhoto() != null) {
            //更新的数据为头像，需要将旧的图片删除。更新头像的特殊处理
            String photoUrl = userMapper.selectOne(
                            new LambdaQueryWrapper<User>().
                                    eq(User::getUid, user.getUid())).
                    getPhoto();
            File file = new File(UPLOAD_FOLDER + photoUrl);
            //如果文件存在，将旧文件删除
            if (file.exists()) {
                file.delete();
            }

        }
        if(user.getEmail() != null){
            //进行修改邮箱的逻辑
        }
        int effects = userMapper.updateById(user);
        if (effects > 0) {
            redisUtil.set(TokenHolder.getToken(), "updated");
            return true;
        }
        return false;
    }

    public boolean signOut() {
//        redisUtil.sremove(ProjectConstant.ONLINE_USER,TO)
        return redisUtil.delete(TokenHolder.getToken());
    }

    @Override
    public Boolean register(UserVto userVto) {
        /*需要验证缓存中的验证码和用户提交验证码是否一致*/
        if (userVto == null) {
            return false;
        }
        User user = userVto.getUser();
        String code = redisUtil.get(user.getEmail());
        if (code.equals(userVto.getCode())) {
            String salt = Md5Util.generateSalt();
            String password = Md5Util.md5Hex(user.getPassword(), salt);
            user.setSalt(salt);
            user.setPassword(password);
            return this.save(userVto.getUser());
        }
        return false;
    }
}
