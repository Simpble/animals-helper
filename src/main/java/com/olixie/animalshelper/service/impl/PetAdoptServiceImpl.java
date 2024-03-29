package com.olixie.animalshelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.olixie.animalshelper.entity.PetAdopt;
import com.olixie.animalshelper.mapper.PetAdoptMapper;
import com.olixie.animalshelper.mapper.PetMapper;
import com.olixie.animalshelper.mapper.UserMapper;
import com.olixie.animalshelper.service.PetAdoptService;
import com.olixie.animalshelper.util.RedisUtil;
import com.olixie.animalshelper.util.TokenHolder;
import com.olixie.animalshelper.vto.ApplyAdoptVto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PetAdoptServiceImpl extends ServiceImpl<PetAdoptMapper,PetAdopt> implements PetAdoptService {
    @Resource
    private PetAdoptMapper petAdoptMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private PetMapper petMapper;
    @Resource
    private RedisUtil redisUtil;

//    public PetAdoptVto PetAdoptToVto(PetAdopt petAdopt) {
//        PetAdoptVto petAdoptVto = new PetAdoptVto();
//        /*获取宠物信息*/
//        Integer pid = petAdopt.getPid();
//        LambdaQueryWrapper<Pet> petLambdaQueryWrapper = new LambdaQueryWrapper<Pet>();
//        petLambdaQueryWrapper.eq(Pet::getPid, pid);
//        Pet pet = petMapper.selectOne(petLambdaQueryWrapper);
//        /*获取用户信息*/
//        Integer uid = petAdopt.getUid();
//        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>();
//        userLambdaQueryWrapper.eq(User::getUid, uid);
//        User user = userMapper.selectOne(userLambdaQueryWrapper);
//        /*封装相关信息*/
//        petAdoptVto.setPetAdopt(petAdopt);
//        petAdoptVto.setPet(pet);
//        petAdoptVto.setUser(user);
//
//        return petAdoptVto;
//    }
//
//    public List<PetAdoptVto> PetAdoptToVtos(List<PetAdopt> petAdopts) {
//        List<PetAdoptVto> res = new ArrayList<>();
//        for (PetAdopt petAdopt : petAdopts) {
//            res.add(PetAdoptToVto(petAdopt));
//        }
//        return res;
//    }
//
//    public List<PetAdoptVto> getAllPetAdopt() {
//        return PetAdoptToVtos(
//                petAdoptMapper.
//                        selectList(null)
//        );
//    }
//
//    /*每当用户调用当前函数时，返回最新的收养信息，返回给用户。
//     * 问题一：可能存在重复数据，如何处理
//     * 问题二：如何确定是否存在最新数据
//     *
//     * 当前数据获取逻辑为：第一次进入时，将最新的数据返回给客户端。之后每次客户刷新时携带上一份数据的最大时间戳。
//     * 根据最大时间戳进行查找，将查找到的数据进行返回。若数据足够时，每次仅返回二十条数据
//     * */
//    public List<PetAdoptVto> getAdoptList(LocalDateTime dateTime) {
//        /*假设当用户传入数据为null时，默认获取最新的数据。之后每次刷新根据上一次获取数据的最大时间戳完成下一次
//         * 数据获取*/
//        LambdaQueryWrapper<PetAdopt> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        List<PetAdopt> petAdopts = new ArrayList<>();
//        if (dateTime == null) {
//            log.info("getAdoptList参数：null");
//            lambdaQueryWrapper.orderByDesc(PetAdopt::getCreateTime);
//        } else {
//            log.info("getAdoptList参数：{}", dateTime.toString());
//            lambdaQueryWrapper.gt(PetAdopt::getCreateTime, dateTime);
//        }
//        petAdopts = petAdoptMapper.
//                selectList(lambdaQueryWrapper).stream().
//                limit(20).collect(Collectors.toList());
//        return PetAdoptToVtos(petAdopts);
//    }
//
//    /**
//     * 用于展示前端页面首页的宠物信息
//     * 用户可进行下滑及初始化加载时调用的方法
//     * 前端展示要求：
//     * 前端需要当前宠物的图片地址，及宠物名称和类型进行展示
//     *
//     * 问题一：如何确定首次调用及后续调用。选择通过参数进行判断，
//     * @param initialize 用于判断是否为第一次进入
//     * @return List<PetAdoptVto>,固定返回十条数据
//     * 问题二：如何确定当前用户上一次获取到的宠物及当前的宠物是否有重复
//     * 基于当前问题，在redis中根据当前用户id，缓存上一次获取到的最小时间戳，
//     * 本次查询根据上一次获取的最小时间戳进行查询，本次查询的结果创建时间均需小于上一次的最小时间戳。
//     * 时间戳越小，距离当前越远。时间戳越大，距离当前时间越近。
//     **/
//    public List<PetAdoptVto> home(boolean initialize) {
//        LambdaQueryWrapper<PetAdopt> queryWrapper = new LambdaQueryWrapper<>();
//        List<PetAdopt> petAdopts = null;
//        if (initialize) {
//            //首次进入时，选择距离当前创建时间最近的宠物进行返回即可
//            queryWrapper.orderByDesc(PetAdopt::getCreateTime);
//            petAdopts = petAdoptMapper.selectList(queryWrapper).
//                    stream().limit(10).
//                    collect(Collectors.toList());
//        }else{
//            String minCreateTime = redisUtil.get("key");
//            //不断获取之前尚未查询的内容返回给用户,要求数据库中的时间戳小于之前的时间戳,即获取时间更久远的数据
//            queryWrapper.lt(PetAdopt::getCreateTime,minCreateTime);
//            queryWrapper.orderByDesc(PetAdopt::getCreateTime);
//            petAdopts = petAdoptMapper.selectList(queryWrapper).
//                    stream().limit(10).
//                    collect(Collectors.toList());
//            //对当前用户进行延期的操作
//            redisUtil.expire("key",30L,TimeUnit.MINUTES);
//
//        }
//        //使用redis进行存储当前获取的宠物最小时间戳，由于petAdopts为降序排序，即当前list的最后一个元素
//        PetAdopt petAdopt = petAdopts.get(petAdopts.size() - 1);
//        //key应为当前用户的id,存储时间为30分钟。若30分钟之内不刷新，则与新进来的用户执行相同的逻辑
//        redisUtil.set("key",petAdopt.getCreateTime().toString(),30L, TimeUnit.MINUTES);
//        return PetAdoptToVtos(petAdopts);
//    }

    @Override
    /*申请领养的方法*/
    public boolean applyAdopt(PetAdopt petAdopt) {
        if (petAdopt == null){
            return false;
        }
        return this.save(petAdopt);
    }
}
