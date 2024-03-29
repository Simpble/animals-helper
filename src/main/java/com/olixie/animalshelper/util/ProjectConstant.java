package com.olixie.animalshelper.util;

public class ProjectConstant {
    //代表文章相关评论
    public static final Integer ARTICLE_COMMENT = 0;
    //代表回复的相关评论
    public static final Integer REPLY_COMMENT = 1;


    public static final Integer PET_STATUS_NOT_ADOPT = 1;
    public static final Integer PET_STATUS_ADOPT = 2;
    /*代表未救助*/
    public static final Integer PET_STATUS_NOT_HELP = 3;
    /*代表已救助*/
    public static final Integer PET_STATUS_HELP = 4;
    /*代表正在寻找*/
    public static final Integer PET_STATUS_FINDING = 5;
    /*代表已找到*/
    public static final Integer PET_STATUS_FIND = 5;

    public static final String ONLINE_USER = "online_user";
    //分别存储点赞和收藏的用户，后续需加上文章id以进行判断
    public static final String NEW_LIKE_SET_KEY = "new_like_set_";
    public static final String NEW_COLLECT_SET_KEY = "new_collect_set_";
    public static final String KNOWLEDGE_LIKE_SET_KEY = "knowledge_like_set_";
    public static final String KNOWLEDGE_COLLECT_SET_KEY = "knowledge_collect_set_";



}
