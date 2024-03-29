package com.olixie.animalshelper.vto;

import com.olixie.animalshelper.entity.Pet;
import com.olixie.animalshelper.entity.PetAdopt;
import com.olixie.animalshelper.entity.User;
import lombok.Data;

@Data
/*用户提交申请时，使用该对象接受数据*/
public class ApplyAdoptVto {
    private Integer pid;
    private Integer masterId;
    private String nickName;
    private String applyName;
}
