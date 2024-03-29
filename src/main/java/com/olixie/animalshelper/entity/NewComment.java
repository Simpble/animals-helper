package com.olixie.animalshelper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NewComment {

    private Integer id;
    private Integer targetId;
    private Integer commentType;
    private Integer uid;
    private String nickName;
    private String photo;
    private String content;
    private String likeCount;
    private LocalDateTime publishDate; //评论发表时间，默认为当前评论插入时间

    public NewComment() {
    }

    public NewComment(Integer id, Integer targetId, Integer type, Integer uid, String nickName, String photo, String content, String likeCount) {
        this.id = id;
        this.targetId = targetId;
        this.commentType = type;
        this.uid = uid;
        this.nickName = nickName;
        this.photo = photo;
        this.content = content;
        this.likeCount = likeCount;
    }

    /*无点赞数量传入的构造方法*/
    public NewComment(Integer id, Integer targetId, Integer type, Integer uid, String nickName, String photo, String content) {
        this.id = id;
        this.targetId = targetId;
        this.commentType = type;
        this.uid = uid;
        this.nickName = nickName;
        this.photo = photo;
        this.content = content;
    }

}
