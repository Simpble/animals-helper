package com.olixie.animalshelper.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.olixie.animalshelper.entity.New;

import java.util.List;

public interface NewService extends IService<New> {
    List<New> getNews();
}
