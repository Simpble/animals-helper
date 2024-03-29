package com.olixie.animalshelper.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
/*记录双方的聊天记录*/
public class ChatMessage {
    @TableId
    private Integer messageId;
    private Integer linkId;
    private Integer fromUser;
    private Integer toUser;
    private String content;
    // 消息发送时间，默认为当前数据的在数据库中进行记录的时间
    private LocalDateTime sendTime;
    // 是否两者之间的最后一条消息
    private Boolean isLatest;

    public ChatMessage(Integer linkId,Integer fromUser,Integer toUser,String content){
        this.linkId = linkId;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
    }

    public Boolean getIsLatest(){
        return this.isLatest;
    }
}
