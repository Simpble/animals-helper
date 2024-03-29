package com.olixie.animalshelper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.olixie.animalshelper.entity.Pet;
import com.olixie.animalshelper.entity.UserPet;
import com.olixie.animalshelper.mapper.PetMapper;
import com.olixie.animalshelper.service.PetService;
import com.olixie.animalshelper.service.UserPetService;
import com.olixie.animalshelper.util.ProjectConstant;
import com.olixie.animalshelper.util.RedisUtil;
import com.olixie.animalshelper.vto.UserPetVto;
import jakarta.annotation.Resource;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements PetService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserPetService userPetService;

    @Override
    public Integer getPetMaster(Integer pid) {
        UserPet userPet = userPetService.getOne(new LambdaQueryWrapper<UserPet>().eq(UserPet::getPid, pid));
        if (userPet == null) {
            return null;
        }
        return userPet.getUid();
    }

    @Override
    /*查看当前用户的宠物*/
    public List<Pet> getPetsByMasterId(Integer uid) {
        List<UserPet> list = userPetService.list(new LambdaQueryWrapper<UserPet>().eq(UserPet::getUid, uid));
        List<Pet> res = new ArrayList<>();
        for (UserPet userPet : list) {
            res.add(this.getById(userPet.getPid()));
        }
        return res;
    }
}
