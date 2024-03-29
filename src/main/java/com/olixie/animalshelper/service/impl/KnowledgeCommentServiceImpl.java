package com.olixie.animalshelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.olixie.animalshelper.entity.KnowledgeComment;
import com.olixie.animalshelper.entity.NewComment;
import com.olixie.animalshelper.entity.PetKnowledge;
import com.olixie.animalshelper.mapper.KnowledgeCommentMapper;
import com.olixie.animalshelper.mapper.NewCommentMapper;
import com.olixie.animalshelper.service.KnowledgeCommentService;
import com.olixie.animalshelper.service.NewCommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KnowledgeCommentServiceImpl extends ServiceImpl<KnowledgeCommentMapper, KnowledgeComment> implements KnowledgeCommentService {

    @Override
    /**
     * 保存回复 评论内容 的评论*/
    public KnowledgeComment saveKnowledgeComment(KnowledgeComment knowledgeComment) {
        if (knowledgeComment == null){
            return null;
        }
        if (knowledgeComment.getCommentType() == 1) {
            Integer targetId = knowledgeComment.getTargetId();
            //更新主评论的回复数量,回复评论的targetId对应为主评论的id
            KnowledgeComment mainComment = this.getById(targetId);
            mainComment.setReplyCount(mainComment.getReplyCount() + 1);
            this.updateById(mainComment);
        }
        this.save(knowledgeComment);
        knowledgeComment.setPublishDate(LocalDateTime.now());
        return knowledgeComment;
    }
}
