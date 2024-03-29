package com.olixie.animalshelper.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.olixie.animalshelper.entity.Pet;
import com.olixie.animalshelper.entity.PetAdopt;
import com.olixie.animalshelper.vto.ApplyAdoptVto;

public interface PetAdoptService extends IService<PetAdopt> {
    boolean applyAdopt(PetAdopt petAdopt);
}
