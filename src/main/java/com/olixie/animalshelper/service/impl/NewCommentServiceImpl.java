package com.olixie.animalshelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.olixie.animalshelper.entity.NewComment;
import com.olixie.animalshelper.mapper.NewCommentMapper;
import com.olixie.animalshelper.service.NewCommentService;
import org.springframework.stereotype.Service;

@Service
public class NewCommentServiceImpl extends ServiceImpl<NewCommentMapper, NewComment> implements NewCommentService {
}
