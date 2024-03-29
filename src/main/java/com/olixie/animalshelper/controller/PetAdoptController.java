package com.olixie.animalshelper.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.olixie.animalshelper.entity.PetAdopt;
import com.olixie.animalshelper.service.impl.PetAdoptServiceImpl;
import com.olixie.animalshelper.util.TokenHolder;
import com.olixie.animalshelper.vto.ApplyAdoptVto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet_adopt")
@Slf4j
public class PetAdoptController {
    @Resource
    private PetAdoptServiceImpl petAdoptService;

    /*方法的作用：获取关于某个人的申请记录*/
    @GetMapping("list/{uid}")
    public ResponseEntity<List<PetAdopt>> showApplyListByUid(@PathVariable("uid") Integer uid) {
        return ResponseEntity.ok(
                petAdoptService.list(new LambdaQueryWrapper<PetAdopt>()
                        .eq(PetAdopt::getApplyUid, uid))
        );
    }

    @PostMapping("/applyAdopt")
    public ResponseEntity<Boolean> applyAdopt(@RequestBody PetAdopt petAdopt) {
        log.info("applyAdoptVto:" + petAdopt.toString());
        return ResponseEntity.ok(petAdoptService.applyAdopt(petAdopt));
    }

    @GetMapping("isApply/{pid}")
    public ResponseEntity<Boolean> isApply(@PathVariable("pid") Integer pid) {
        /*根据uid和applyUid查找唯一的PetAdopt*/
        return ResponseEntity.ok(
                petAdoptService.getOne(new LambdaQueryWrapper<PetAdopt>()
                        .eq(PetAdopt::getPid, pid)
                        .eq(PetAdopt::getApplyUid, TokenHolder.parseToken())) != null
        );
    }
}
