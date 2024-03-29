package com.olixie.animalshelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.olixie.animalshelper.entity.UserPet;
import com.olixie.animalshelper.mapper.UserPetMapper;
import com.olixie.animalshelper.service.UserPetService;
import org.springframework.stereotype.Service;

@Service
public class UserPetServiceImpl extends ServiceImpl<UserPetMapper, UserPet> implements UserPetService {
}
