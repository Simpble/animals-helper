package com.olixie.animalshelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.olixie.animalshelper.entity.PetKnowledge;
import com.olixie.animalshelper.mapper.PetKnowledgeMapper;
import com.olixie.animalshelper.service.PetKnowledgeService;
import org.springframework.stereotype.Service;

@Service
public class PetKnowledgeServiceImpl extends ServiceImpl<PetKnowledgeMapper, PetKnowledge> implements PetKnowledgeService {
}
