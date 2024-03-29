package com.olixie.animalshelper.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.olixie.animalshelper.entity.Pet;
import com.olixie.animalshelper.vto.UserPetVto;

import java.util.List;

public interface PetService extends IService<Pet> {
    Integer getPetMaster(Integer pid);

    List<Pet> getPetsByMasterId(Integer uid);
}
