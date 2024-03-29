package com.olixie.animalshelper.controller;

import com.olixie.animalshelper.entity.User;
import com.olixie.animalshelper.service.impl.UserServiceImpl;
import com.olixie.animalshelper.vto.UserVto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserServiceImpl userService;


    @PostMapping("/password-login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User token = userService.passwordLogin(user);
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(token);
    }


    @GetMapping("/getUserInfo")
    public ResponseEntity<User> getUserInfo() {
        User user = userService.getUserInfo();
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/signOut")
    public ResponseEntity<Boolean> signOut() {
       return ResponseEntity.ok(userService.signOut());
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUserInfo(@RequestBody User user) {
        log.info("user:{}", user);
        boolean flag = userService.updateUserInfo(user);
        if (flag) {
            return ResponseEntity.ok("更新成功");
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{uid}")
    public ResponseEntity<User> getById(@PathVariable("uid") Integer id){
        return ResponseEntity.ok(
                this.userService.getById(id)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody UserVto userVto){
        log.info("user:{}",userVto.toString());
        return ResponseEntity.ok(userService.register(userVto));
    }
}
