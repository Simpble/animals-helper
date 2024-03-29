package com.olixie.animalshelper.websocket;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.olixie.animalshelper.config.WebSocketConfig;
import com.olixie.animalshelper.entity.ChatList;
import com.olixie.animalshelper.entity.ChatMessage;
import com.olixie.animalshelper.entity.ChatUserLink;
import com.olixie.animalshelper.entity.SocketReceivedMessage;
import com.olixie.animalshelper.service.ChatListService;
import com.olixie.animalshelper.service.ChatMessageService;
import com.olixie.animalshelper.service.ChatUserLinkService;
import com.olixie.animalshelper.util.ApplicationContextUtils;
import com.olixie.animalshelper.util.RedisUtil;
import com.olixie.animalshelper.util.TokenHolder;
import jakarta.annotation.Resource;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*WebSocket对话处理器*/
@Component
@Slf4j
@ServerEndpoint(value = "/myWebSocket/{uid}")
public class DialogueHandler {

    private ChatMessageService chatMessageService;
    private ChatUserLinkService chatUserLinkService;

    private final static Map<Integer, Session> onlineUser = new ConcurrentHashMap<>();

    /**
     * @param uid 发送消息者，也就是当前连接的创立者
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("uid") Integer uid) {
        onlineUser.put(uid, session);
    }

    @OnMessage
    public void onMsg(Session session, String message, @PathParam("uid") Integer from) {
        log.info("message:{}", message);
        /*首先,判断双方是否为第一次聊天。若为第一次聊天，往双方的聊天列表插入数据*/
        SocketReceivedMessage socketReceivedMessage = JSONUtil.toBean(message, SocketReceivedMessage.class);
        Integer to = socketReceivedMessage.getUid();

        chatUserLinkService = ApplicationContextUtils.getBean(ChatUserLinkService.class);
        chatMessageService = ApplicationContextUtils.getBean(ChatMessageService.class);
        ChatUserLink chatUserLink = chatUserLinkService.getUserLink(from, to);
        if (chatUserLink == null) {
            /*该方法会建立双方的连接及会话列表*/
            chatUserLink = chatUserLinkService.init(from, to);
            /*将消息保存至数据库，以便后续获取聊天记录*/
        }
        /*更新最新消息*/
        if (onlineUser.containsKey(to)) {
            /*在当前的map中表示对方在线*/
            Session toSession = onlineUser.get(to);
            if (!from.equals(to)) {
                //当前发送的对象不是自己通过websocket发送消息
                toSession.getAsyncRemote().sendText(socketReceivedMessage.getContent());
            }
            /*向对方发送消息*/

        } else {
            /*对方不在线，当前列表未读数加一*/
            chatUserLinkService.doNotOnLine(chatUserLink.getLinkId(), to);
        }
        /*保存消息至数据库当中,关于聊天记录保存的后续优化为保存在客户端的机器上，
         * 目前来看需要在前端进行文件相关的操作*/
        chatMessageService.updateLastMessage(chatUserLink.getLinkId());
        chatMessageService.save(new ChatMessage(chatUserLink.getLinkId(), from, to, socketReceivedMessage.getContent()));

    }

    @OnClose
    public void onClose(Session session, @PathParam("uid") Integer uid) {
        /*
            do something for onClose
            与当前客户端连接关闭时
         */
        onlineUser.remove(uid);
    }


}
