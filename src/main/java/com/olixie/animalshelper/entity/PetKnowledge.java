package com.olixie.animalshelper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PetKnowledge {
    private Integer id;
    private Integer type;  // 文章类型
    private String title;  // 文章标题
    private String contentExcerpt; // 文章摘要
    private String content;
    private String cover;  // 用户可指定文章封面，保存的为文件地址
    private String publisher;  // 用户可指定文章封面，保存的为文件地址
    private LocalDateTime publishDate;  // 用户可指定文章封面，保存的为文件地址
}
