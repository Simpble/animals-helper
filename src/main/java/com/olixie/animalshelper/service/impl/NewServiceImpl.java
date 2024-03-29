package com.olixie.animalshelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.olixie.animalshelper.entity.New;
import com.olixie.animalshelper.mapper.NewMapper;
import com.olixie.animalshelper.service.NewService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewServiceImpl extends ServiceImpl<NewMapper,New> implements NewService {
    @Resource
    private NewMapper newMapper;

    public List<New> getNews(){
        /*后续需要在此根据时间获取最新的新闻消息,目前实现的功能仅为获取20条新闻消息*/
        return newMapper.selectList(null).
                stream().limit(20).collect(Collectors.toList());
    }
}
