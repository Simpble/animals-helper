package com.olixie.animalshelper.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.olixie.animalshelper.entity.KnowledgeComment;
import com.olixie.animalshelper.entity.NewComment;

public interface KnowledgeCommentService extends IService<KnowledgeComment> {
    public KnowledgeComment saveKnowledgeComment(KnowledgeComment knowledgeComment);
}
