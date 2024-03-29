package com.olixie.animalshelper.vto;

import com.olixie.animalshelper.entity.ChatList;
import com.olixie.animalshelper.entity.ChatMessage;
import com.olixie.animalshelper.entity.User;
import lombok.Data;

@Data
/*用户获取消息列表时，将当前对象返回*/
public class ChatListVto {
    /*包含未读数的信息*/
    private ChatList chatList;
    /*展示在消息列表中的信息，对话者的头像即姓名*/
    private User toUser;
    /*最后一条信息的内容*/
    private ChatMessage lastMessage;
}
