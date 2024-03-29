package com.olixie.animalshelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.olixie.animalshelper.entity.ChatList;
import com.olixie.animalshelper.entity.New;
import com.olixie.animalshelper.mapper.ChatListMapper;
import com.olixie.animalshelper.mapper.NewMapper;
import com.olixie.animalshelper.service.ChatListService;
import com.olixie.animalshelper.service.NewService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatListServiceImpl extends ServiceImpl<ChatListMapper, ChatList> implements ChatListService {
}
