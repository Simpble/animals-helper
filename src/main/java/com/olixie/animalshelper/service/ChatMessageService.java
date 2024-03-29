package com.olixie.animalshelper.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.olixie.animalshelper.entity.ChatMessage;

public interface ChatMessageService extends IService<ChatMessage> {

    void updateLastMessage(Integer linkId);
}
