package com.olixie.animalshelper.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class New {
    private Integer id;
    private String title;
    private String contentFrom; // 新闻来源
    private String content;
    private LocalDateTime publishDate; //新闻发布日期
    private LocalDateTime createTime; //新闻发布日期
    private Integer deleteStatus;
}
