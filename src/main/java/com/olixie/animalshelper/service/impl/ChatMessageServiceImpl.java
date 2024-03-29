package com.olixie.animalshelper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.olixie.animalshelper.entity.ChatMessage;
import com.olixie.animalshelper.mapper.ChatMessageMapper;
import com.olixie.animalshelper.service.ChatMessageService;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements ChatMessageService {
    @Override
    public void updateLastMessage(Integer linkId) {
        ChatMessage lastMessage = this.getOne(new LambdaQueryWrapper<ChatMessage>()
                .eq(ChatMessage::getLinkId, linkId)
                .eq(ChatMessage::getIsLatest, true)
        );
        /*双方为第一次通信，没有聊天记录*/
        if(lastMessage == null){
            return;
        }
        lastMessage.setIsLatest(false);
        /*更新最后一条Message信息*/
        this.updateById(lastMessage);
    }
}
