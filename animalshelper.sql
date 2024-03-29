/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50740 (5.7.40-log)
 Source Host           : localhost:3306
 Source Schema         : animalshelper

 Target Server Type    : MySQL
 Target Server Version : 50740 (5.7.40-log)
 File Encoding         : 65001

 Date: 29/03/2024 21:58:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chat_list
-- ----------------------------
DROP TABLE IF EXISTS `chat_list`;
CREATE TABLE `chat_list`  (
  `list_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '聊天列表主键',
  `link_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '聊天主表id',
  `from_user` int(11) NOT NULL COMMENT '发送者',
  `to_user` int(11) NOT NULL COMMENT '接收者',
  `from_window` tinyint(4) NULL DEFAULT 0 COMMENT '发送方是否在窗口',
  `to_window` tinyint(4) NULL DEFAULT 0 COMMENT '接收方是否在窗口',
  `unread` int(11) NULL DEFAULT 0 COMMENT '未读数',
  `delete_status` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`list_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for chat_message
-- ----------------------------
DROP TABLE IF EXISTS `chat_message`;
CREATE TABLE `chat_message`  (
  `message_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '聊天内容id',
  `link_id` int(11) NOT NULL COMMENT '聊天主表id',
  `from_user` int(11) NOT NULL COMMENT '发送者',
  `to_user` int(11) NOT NULL COMMENT '接收者',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '聊天内容',
  `send_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `is_latest` tinyint(1) NULL DEFAULT 1 COMMENT '是否为最后一条消息,TINYINT(0)对应为true',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1662586882 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for chat_user_link
-- ----------------------------
DROP TABLE IF EXISTS `chat_user_link`;
CREATE TABLE `chat_user_link`  (
  `link_id` int(11) NOT NULL AUTO_INCREMENT,
  `id` int(11) NOT NULL,
  `other_id` int(11) NOT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`link_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for knowledge_comment
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_comment`;
CREATE TABLE `knowledge_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `target_id` int(11) NOT NULL,
  `comment_type` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `like_count` int(11) NULL DEFAULT 0,
  `reply_count` int(11) NULL DEFAULT 0,
  `publish_date` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2010693634 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for new
-- ----------------------------
DROP TABLE IF EXISTS `new`;
CREATE TABLE `new`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content_from` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `publish_date` datetime NOT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `delete_status` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for new_comment
-- ----------------------------
DROP TABLE IF EXISTS `new_comment`;
CREATE TABLE `new_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `target_id` int(11) NOT NULL,
  `comment_type` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `like_count` int(11) NULL DEFAULT 0,
  `reply_count` int(11) NULL DEFAULT 0,
  `publish_date` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 299446274 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pet
-- ----------------------------
DROP TABLE IF EXISTS `pet`;
CREATE TABLE `pet`  (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` int(11) NOT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `photo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pet_adopt
-- ----------------------------
DROP TABLE IF EXISTS `pet_adopt`;
CREATE TABLE `pet_adopt`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pid` int(11) NOT NULL,
  `apply_uid` int(11) NOT NULL,
  `apply_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pet_adopt_status` tinyint(1) NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1742274563 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pet_category
-- ----------------------------
DROP TABLE IF EXISTS `pet_category`;
CREATE TABLE `pet_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pet_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `pet_knowledge`;
CREATE TABLE `pet_knowledge`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(1) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content_excerpt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `publisher` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `publish_date` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pet_status
-- ----------------------------
DROP TABLE IF EXISTS `pet_status`;
CREATE TABLE `pet_status`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `statu` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` tinyint(1) NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `category_id` tinyint(4) NOT NULL DEFAULT 2,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_delete` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_category
-- ----------------------------
DROP TABLE IF EXISTS `user_category`;
CREATE TABLE `user_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_pet
-- ----------------------------
DROP TABLE IF EXISTS `user_pet`;
CREATE TABLE `user_pet`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  `delete_status` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `pid`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
