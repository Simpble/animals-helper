package com.olixie.animalshelper.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PetAdopt {
    private Integer id;
    //主人id
    private Integer uid;
    //主人网名
    private String nickName;
    //宠物id
    private Integer pid;
    //申请者
    private Integer applyUid;
    //申请人的网名
    private String applyName;
    //申请的状态
    private Integer petAdoptStatus;
    //创建时间
    private LocalDateTime createTime;
}
