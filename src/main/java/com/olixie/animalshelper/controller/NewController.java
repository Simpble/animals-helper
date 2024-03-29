package com.olixie.animalshelper.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.olixie.animalshelper.entity.KnowledgeComment;
import com.olixie.animalshelper.entity.New;
import com.olixie.animalshelper.entity.NewComment;
import com.olixie.animalshelper.service.NewCommentService;
import com.olixie.animalshelper.service.NewService;
import com.olixie.animalshelper.util.ProjectConstant;
import com.olixie.animalshelper.util.RedisUtil;
import com.olixie.animalshelper.util.TokenHolder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/*关于新闻的控制类*/
@RequestMapping("/new")
@RestController
@Slf4j
public class NewController {
    @Resource
    private NewService newService;

    @Resource
    private NewCommentService newCommentService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 获取新闻功能的实现
     * 当前方法需要实现的功能为：获取数据库中的新闻进行展示,同时需要确保新闻的时效性。
     * 最初版不考虑新闻的时效性，仅完成新闻的获取。后续需要根据时间获取最新的消息
     *
     * @return 固定返回二十条数据，在前端通过定时器进行轮播
     */
    @GetMapping("/getNews")
    public ResponseEntity<List<New>> getNews() {
        return ResponseEntity.ok(newService.getNews());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<New> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(newService.getById(id));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteNew(Integer id) {
        return ResponseEntity.ok(newService.removeById(id
        ));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addNew(@RequestBody New newEntity) {
        return ResponseEntity.ok(newService.save(newEntity));
    }


    @GetMapping("/like/{id}")
    /*执行点赞相关方法，核心思路为根据新闻id,添加用户名至set当中*/
    public ResponseEntity<Boolean> like(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(redisUtil.
                //存储的key为当前文章的id,value为当前用户的id
                sadd(ProjectConstant.NEW_LIKE_SET_KEY + id,
                        TokenHolder.parseToken().toString()
                )
        );
    }

    @GetMapping("/deleteLike/{id}")
    /*取消点赞*/
    public ResponseEntity<Boolean> deleteLike(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(redisUtil.
                //存储的key为当前文章的id,value为当前用户的id
                        sremove(ProjectConstant.NEW_LIKE_SET_KEY + id,
                        TokenHolder.parseToken().toString()
                )
        );
    }

    @GetMapping("/collect/{id}")
    /*收藏*/
    public ResponseEntity<Boolean> collect(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(redisUtil.
                //存储的key为当前文章的id,value为当前用户的id
                        sadd(ProjectConstant.NEW_COLLECT_SET_KEY + id,
                        TokenHolder.parseToken().toString()
                )
        );
    }

    @GetMapping("/deleteCollect/{id}")
    /*取消收藏*/
    public ResponseEntity<Boolean> deleteCollect(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(redisUtil.
                //存储的key为当前文章的id,value为当前用户的id
                        sremove(ProjectConstant.NEW_COLLECT_SET_KEY + id,
                        TokenHolder.parseToken().toString()
                )
        );
    }

    /*判断当前用户是否点赞*/
    @GetMapping("isLike/{id}")
    public ResponseEntity<Boolean> isLike(@PathVariable("id") Integer id) {

        return ResponseEntity.ok(redisUtil.
                //存储的key为当前文章的id,value为当前用户的id
                        isMember(ProjectConstant.NEW_LIKE_SET_KEY + id,
                        TokenHolder.parseToken().toString()
                )
        );
    }
    /*判断当前用户是否收藏*/
    @GetMapping("isCollect/{id}")
    public ResponseEntity<Boolean> isCollect(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(redisUtil.
                //存储的key为当前文章的id,value为当前用户的id
                        isMember(ProjectConstant.NEW_COLLECT_SET_KEY + id,
                        TokenHolder.parseToken().toString()
                )
        );
    }


    /*评论存储*/
    @PostMapping("/comment/save")
    public ResponseEntity<NewComment> saveComment(@RequestBody NewComment newComment){
        newCommentService.save(newComment);
        newComment.setPublishDate(LocalDateTime.now());
        return ResponseEntity.ok(newComment);
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<List<NewComment>> getByNewId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(newCommentService.list(
                new LambdaQueryWrapper<NewComment>()
                        /*根据文章id获取评论，当一篇文章评论量过大时后续需进行限制*/
                        .eq(NewComment::getTargetId,id)
                        .eq(NewComment::getCommentType,ProjectConstant.ARTICLE_COMMENT)
                        .orderByDesc(NewComment::getPublishDate)
        ));
    }

    @GetMapping("/reply-comment/{id}")
    public ResponseEntity<List<NewComment>> getByNewCommentId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(newCommentService.list(
                new LambdaQueryWrapper<NewComment>()
                        /*根据文章id获取评论，当一篇文章评论量过大时后续需进行限制*/
                        .eq(NewComment::getTargetId,id)
                        .eq(NewComment::getCommentType,ProjectConstant.REPLY_COMMENT)
                        .orderByDesc(NewComment::getPublishDate)
        ));
    }
}
