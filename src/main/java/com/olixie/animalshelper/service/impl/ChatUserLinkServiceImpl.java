package com.olixie.animalshelper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.olixie.animalshelper.entity.ChatList;
import com.olixie.animalshelper.entity.ChatMessage;
import com.olixie.animalshelper.entity.ChatUserLink;
import com.olixie.animalshelper.mapper.ChatUserLinkMapper;
import com.olixie.animalshelper.service.ChatListService;
import com.olixie.animalshelper.service.ChatMessageService;
import com.olixie.animalshelper.service.ChatUserLinkService;
import com.olixie.animalshelper.service.UserService;
import com.olixie.animalshelper.vto.ChatListVto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatUserLinkServiceImpl extends ServiceImpl<ChatUserLinkMapper, ChatUserLink> implements ChatUserLinkService {
    @Resource
    private ChatListService chatListService;

    @Resource
    private ChatMessageService chatMessageService;

    @Resource
    private UserService userService;

    @Override
    public ChatUserLink getUserLink(Integer id,Integer otherId){
        ChatUserLink one = this.getOne(new LambdaQueryWrapper<ChatUserLink>().
                eq(ChatUserLink::getId, id).
                eq(ChatUserLink::getOtherId, otherId)
        );
        if (one != null){
            return one;
        }
        one = this.getOne(new LambdaQueryWrapper<ChatUserLink>().
                eq(ChatUserLink::getId, otherId).
                eq(ChatUserLink::getOtherId, id)
        );
        return one;
    }

    @Override
    /*双方不是第一次进行对话，对方不在线时执行的操作*/
    public void doNotOnLine(Integer linkId,Integer to) {
        ChatList toList = chatListService.getOne(new LambdaQueryWrapper<ChatList>()
                .eq(ChatList::getLinkId, linkId)
                .eq(ChatList::getFromUser, to));
        if (toList == null){
            throw new RuntimeException("数据出现错误");
        }
        toList.setUnread(toList.getUnread() + 1);
        if(!chatListService.updateById(toList)){
            throw new RuntimeException(toList.getFromUser() + "未读数未更新");
        }
    }

    @Override
    /*判断用户是否为首次进行会话*/
    public Boolean isFirst(Integer from, Integer to) {
        /*返回true,表示双方为第一次建立通信*/
        return getUserLink(from,to) == null;
    }

    @Override
    /*首次会话，向数据库插入必要数据，完成初始化*/
    public ChatUserLink init(Integer from, Integer to) {
        /*link表示双方是否存在联系*/
        ChatUserLink link = new ChatUserLink(from, to);
        this.save(link);
        ChatList fromList = new ChatList();
        ChatList toList = new ChatList();

        /*消息发送方的消息列表初始化*/
        fromList.setLinkId(link.getLinkId());
        fromList.setFromUser(from);
        fromList.setToUser(to);

        /*消息接受方的消息列表初始化*/
        toList.setLinkId(link.getLinkId());
        toList.setFromUser(to);
        toList.setToUser(from);
        // 设置未读消息数量
        toList.setUnread(1);

        chatListService.save(fromList);
        chatListService.save(toList);
        return link;
    }

    @Override
    /*获取 id 的消息列表*/
    public List<ChatListVto> getChatList(Integer id) {
        LambdaQueryWrapper<ChatList> chatListLambdaQueryWrapper = new LambdaQueryWrapper<>();
        chatListLambdaQueryWrapper.eq(ChatList::getFromUser,id);
        List<ChatList> list = chatListService.list(chatListLambdaQueryWrapper);
        return chatListToVtos(list);
    }

    /*将chatList类型的对象转换为chatListVto对象，方便前端获取数据*/
    public List<ChatListVto> chatListToVtos(List<ChatList> list){
        List<ChatListVto> res = new ArrayList<>();
        for (ChatList chatList : list) {
            ChatListVto chatListVto = new ChatListVto();
            chatListVto.setChatList(chatList);
            /*寻找最后一条消息*/
            ChatMessage message = chatMessageService.getOne(new LambdaQueryWrapper<ChatMessage>()
                    .eq(ChatMessage::getLinkId,chatList.getLinkId())
                    .eq(ChatMessage::getIsLatest, true)
            );
            chatListVto.setLastMessage(message);
            chatListVto.setToUser(userService.getById(chatList.getToUser()));
            res.add(chatListVto);
        }
        return res;
    }

    @Override
    /*获取两者的历史聊天记录*/
    public List<ChatMessage> getOldMessageRecord(Integer id, Integer otherId) {
        ChatUserLink userLink = getUserLink(id, otherId);
        //当前两人无历史聊天记录
        if(userLink == null){
            return null;
        }
        /*获取聊天记录*/
        return chatMessageService.list(new LambdaQueryWrapper<ChatMessage>().
                eq(ChatMessage::getLinkId,userLink.getLinkId()).
                orderByAsc(ChatMessage::getSendTime)
        );
    }

}
