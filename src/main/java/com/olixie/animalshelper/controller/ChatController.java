package com.olixie.animalshelper.controller;

import com.olixie.animalshelper.entity.ChatList;
import com.olixie.animalshelper.entity.ChatMessage;
import com.olixie.animalshelper.service.ChatListService;
import com.olixie.animalshelper.service.ChatUserLinkService;
import com.olixie.animalshelper.util.TokenHolder;
import com.olixie.animalshelper.vto.ChatListVto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {
    @Resource
    private ChatUserLinkService chatUserLinkService;

    @Resource
    private ChatListService chatListService;

    @GetMapping("/list")
    /*获取聊天列表*/
    public ResponseEntity<List<ChatListVto>> getChatList(){
        return ResponseEntity.ok(
                chatUserLinkService.getChatList(TokenHolder.parseToken())
        );
    }

    @PutMapping("/unread")
    public ResponseEntity<Boolean> updateUnRead(@RequestBody ChatList chatList){
        log.info("chatList:{}",chatList.toString());
        return ResponseEntity.ok(
                chatListService.updateById(chatList)
        );
    }

    /**
     * 获取历史聊天记录
     * @param otherId 另一位用户Id
     * */

    @GetMapping("/oldRecord/{uid}")
    public ResponseEntity<List<ChatMessage>> getOldChatMessage(@PathVariable("uid") Integer otherId){
        return ResponseEntity.ok(
                chatUserLinkService.getOldMessageRecord(TokenHolder.parseToken(),otherId)
        );
    }
}
