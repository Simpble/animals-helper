package com.olixie.animalshelper.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.olixie.animalshelper.entity.Pet;
import com.olixie.animalshelper.service.PetService;
import com.olixie.animalshelper.util.ProjectConstant;
import com.olixie.animalshelper.vto.UserPetVto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pet")
@Slf4j
public class PetController {

    @Resource
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<Pet>> get(){
        /*目前该方法完成的功能： 获取未收养的宠物*/
        LambdaQueryWrapper<Pet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Pet::getStatus, ProjectConstant.PET_STATUS_NOT_ADOPT);
        List<Pet> pets = petService.list(queryWrapper).stream().limit(20).collect(Collectors.toList());
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/getPets/{masterId}")
    public ResponseEntity<List<Pet>> getPetsByMasterId(@PathVariable("masterId") Integer id){
        return ResponseEntity.ok(petService.getPetsByMasterId(id));
    }

    @GetMapping("/{pid}")
    public ResponseEntity<Pet> getPetInfoById(@PathVariable Integer pid){
        Pet pet = petService.getById(pid);
        if (pet == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pet);
    }


    @GetMapping("getMaster/{pid}")
    public ResponseEntity<Integer> getMaster(@PathVariable Integer pid){
        return ResponseEntity.ok(petService.getPetMaster(pid));
    }



    @GetMapping("examine")
    public ResponseEntity<String> examine(@PathVariable Integer uid){
        /*此处为向rocketmq发送消息的代码*/
        return ResponseEntity.ok("success");
    }

    @PutMapping("/updatePetInfo")
    public ResponseEntity<Boolean> update(@RequestBody Pet pet){
        return ResponseEntity.ok(petService.updateById(pet));
    }
}
