package com.olixie.animalshelper.controller;

import com.olixie.animalshelper.entity.NewComment;
import com.olixie.animalshelper.service.NewCommentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    @Resource
    private NewCommentService newCommentService;

    /**
     * 此处传入的参数为评论id,和需要的点赞数量
     * */
    @PutMapping("/behavior")
    public ResponseEntity<Boolean> commentBehavior(@RequestBody NewComment newComment){
        return ResponseEntity.ok(newCommentService.updateById(newComment));
    }

}
