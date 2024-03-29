package com.olixie.animalshelper.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*聊天列表,记录当前用户与谁保持联系*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatList {
    @TableId
    private Integer listId;
    private Integer linkId;
    /*根据fromUser获取所有对话*/
    private Integer fromUser;
    private Integer toUser;
    private Integer fromWindow;
    /*接收方是否在窗口*/
    private Integer toWindow;
    private Integer unread;
    /*0表示当前会话存在，1表示会话已被删除，不显示在后续的消息列表*/
    private Integer deleteStatus;
}
