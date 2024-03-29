package com.olixie.animalshelper.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.olixie.animalshelper.entity.ChatList;
import com.olixie.animalshelper.entity.ChatMessage;
import com.olixie.animalshelper.entity.ChatUserLink;
import com.olixie.animalshelper.entity.SocketReceivedMessage;
import com.olixie.animalshelper.vto.ChatListVto;

import java.util.List;

public interface ChatUserLinkService extends IService<ChatUserLink> {

    Boolean isFirst(Integer from,Integer to);

    ChatUserLink init(Integer from,Integer to);

    List<ChatListVto> getChatList(Integer id);

    List<ChatMessage> getOldMessageRecord(Integer id,Integer otherId);

    ChatUserLink getUserLink(Integer from,Integer to);

    void doNotOnLine(Integer linkId,Integer to);

//    void saveMessage(Integer linkId, SocketReceivedMessage message);
}
