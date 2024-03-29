package com.olixie.animalshelper.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.olixie.animalshelper.entity.KnowledgeComment;
import com.olixie.animalshelper.entity.PetKnowledge;
import com.olixie.animalshelper.service.KnowledgeCommentService;
import com.olixie.animalshelper.service.PetKnowledgeService;
import com.olixie.animalshelper.util.ProjectConstant;
import com.olixie.animalshelper.util.RedisUtil;
import com.olixie.animalshelper.util.TokenHolder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/pet-knowledge")
@RestController
@Slf4j
public class PetKnowledgeController {

    @Resource
    private PetKnowledgeService petKnowledgeService;

    @Resource
    private KnowledgeCommentService knowledgeCommentService;

    @Resource
    private RedisUtil redisUtil;

    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody PetKnowledge petKnowledge) {
        log.info(petKnowledge.toString());
        return ResponseEntity.ok(petKnowledgeService.save(petKnowledge));
    }

    @GetMapping("/list")
    public ResponseEntity<List<PetKnowledge>> list() {
        return ResponseEntity.ok(petKnowledgeService.list());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PetKnowledge> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(petKnowledgeService.getById(id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteKnowledge(Integer id) {
        return ResponseEntity.ok(petKnowledgeService.removeById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addKnowledge(@RequestBody PetKnowledge petKnowledge) {
        return ResponseEntity.ok(petKnowledgeService.save(petKnowledge));
    }


    @GetMapping("/like/{id}")
    /*执行点赞相关方法，核心思路为根据新闻id,添加用户名至set当中*/
    public ResponseEntity<Boolean> like(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(redisUtil.
                //存储的key为当前文章的id,value为当前用户的id
                        sadd(ProjectConstant.KNOWLEDGE_LIKE_SET_KEY + id,
                        TokenHolder.parseToken().toString()
                )
        );
    }

    @GetMapping("/deleteLike/{id}")
    /*取消点赞*/
    public ResponseEntity<Boolean> deleteLike(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(redisUtil.
                //存储的key为当前文章的id,value为当前用户的id
                        sremove(ProjectConstant.KNOWLEDGE_LIKE_SET_KEY + id,
                        TokenHolder.parseToken().toString()
                )
        );
    }

    @GetMapping("/collect/{id}")
    /*收藏*/
    public ResponseEntity<Boolean> collect(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(redisUtil.
                //存储的key为当前文章的id,value为当前用户的id
                        sadd(ProjectConstant.KNOWLEDGE_COLLECT_SET_KEY + id,
                        TokenHolder.parseToken().toString()
                )
        );
    }

    @GetMapping("/deleteCollect/{id}")
    /*取消收藏*/
    public ResponseEntity<Boolean> deleteCollect(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(redisUtil.
                //存储的key为当前文章的id,value为当前用户的id
                        sremove(ProjectConstant.KNOWLEDGE_COLLECT_SET_KEY + id,
                        TokenHolder.parseToken().toString()
                )
        );
    }

    /*判断当前用户是否点赞*/
    @GetMapping("isLike/{id}")
    public ResponseEntity<Boolean> isLike(@PathVariable("id") Integer id) {

        return ResponseEntity.ok(redisUtil.
                //存储的key为当前文章的id,value为当前用户的id
                        isMember(ProjectConstant.KNOWLEDGE_LIKE_SET_KEY + id,
                        TokenHolder.parseToken().toString()
                )
        );
    }

    /*判断当前用户是否收藏*/
    @GetMapping("isCollect/{id}")
    public ResponseEntity<Boolean> isCollect(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(redisUtil.
                //存储的key为当前文章的id,value为当前用户的id
                        isMember(ProjectConstant.KNOWLEDGE_COLLECT_SET_KEY + id,
                        TokenHolder.parseToken().toString()
                )
        );
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<List<KnowledgeComment>> getByKnowledgeId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(knowledgeCommentService.list(
                new LambdaQueryWrapper<KnowledgeComment>()
                        /*根据文章id获取评论，当一篇文章评论量过大时后续需进行限制*/
                        .eq(KnowledgeComment::getTargetId,id)
                        .eq(KnowledgeComment::getCommentType,ProjectConstant.ARTICLE_COMMENT)
                        .orderByDesc(KnowledgeComment::getPublishDate)
        ));
    }
    @GetMapping("/reply-comment/{id}")
    public ResponseEntity<List<KnowledgeComment>> getByKnowledgeCommentId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(knowledgeCommentService.list(
                new LambdaQueryWrapper<KnowledgeComment>()
                        /*根据文章id获取评论，当一篇文章评论量过大时后续需进行限制*/
                        .eq(KnowledgeComment::getTargetId,id)
                        .eq(KnowledgeComment::getCommentType,ProjectConstant.REPLY_COMMENT)
                        .orderByDesc(KnowledgeComment::getPublishDate)
        ));
    }

    /*评论存储*/
    @PostMapping("/comment/save")
    public ResponseEntity<KnowledgeComment> saveComment(@RequestBody KnowledgeComment knowledgeComment){
        return ResponseEntity.ok(knowledgeCommentService.saveKnowledgeComment(knowledgeComment));
    }
}
