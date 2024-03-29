package com.olixie.animalshelper.vto;

import com.olixie.animalshelper.entity.UserPet;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// 暂不使用
public class UserPetVto {
    private UserPet userPet;
    private Boolean online;
}
