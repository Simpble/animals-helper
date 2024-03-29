package com.olixie.animalshelper.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Pet {
    @TableId
    private Integer pid;
    /*宠物种类，提供给用户根据类型查询*/
    private Integer type;
    private String name;
    private String description;
    private String sex;
    private Integer age;
    /*宠物照片*/
    private String photo;
    /*宠物状态，包含是否被收养等状态*/
    private Integer status;

}
