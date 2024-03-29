package com.olixie.animalshelper.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
/*定义某人是否有聊天*/
public class ChatUserLink {
    @TableId
    private Integer linkId;
    private Integer id;
    private Integer otherId;
    private LocalDateTime createTime;
    public ChatUserLink(Integer id,Integer otherId){
        this.id = id;
        this.otherId = otherId;

    }
}
