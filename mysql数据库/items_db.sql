/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50620
Source Host           : localhost:3306
Source Database       : items_db

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2024-05-10 01:16:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_goods`
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `goodsId` int(11) NOT NULL AUTO_INCREMENT COMMENT '物品id',
  `goodTypeObj` int(11) NOT NULL COMMENT '物品类型',
  `goodsName` varchar(20) NOT NULL COMMENT '物品名称',
  `goodsRfid` varchar(20) NOT NULL COMMENT '物品rfid',
  `goodPrice` float NOT NULL COMMENT '物品价格',
  `goodsDescribe` varchar(500) NOT NULL COMMENT '物品描述',
  `goodIntoTIme` varchar(20) DEFAULT NULL COMMENT '物品存入时间',
  `goodsNumb` int(11) NOT NULL COMMENT '仓库数量',
  `goodsUserObj` varchar(30) NOT NULL COMMENT '所属用户',
  PRIMARY KEY (`goodsId`),
  KEY `goodTypeObj` (`goodTypeObj`),
  KEY `goodsUserObj` (`goodsUserObj`),
  CONSTRAINT `t_goods_ibfk_1` FOREIGN KEY (`goodTypeObj`) REFERENCES `t_goodstype` (`goodTypeId`),
  CONSTRAINT `t_goods_ibfk_2` FOREIGN KEY (`goodsUserObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES ('4', '1', '某某香烟', '455', '2', '产品批次：1批\r\n供应商厂家：xx厂家\r\n生产地：成都\r\n产品具体成分：尼古丁、焦油', '2024-05-09 19:16:58', '5', 'user2');
INSERT INTO `t_goods` VALUES ('7', '1', '某某香烟', '33A6601A', '2', '11', '2024-05-09 22:40:27', '1', 'user1');
INSERT INTO `t_goods` VALUES ('8', '1', 'hjkh', '539FC30C', '1', '批次：2\r\n生产场所', '2024-05-10 00:29:47', '1', 'user1');

-- ----------------------------
-- Table structure for `t_goodsinto`
-- ----------------------------
DROP TABLE IF EXISTS `t_goodsinto`;
CREATE TABLE `t_goodsinto` (
  `goodsintoId` int(11) NOT NULL AUTO_INCREMENT COMMENT '物品入库id',
  `goodsIntoObj` int(11) NOT NULL COMMENT '入库物品',
  `intoNumb` int(11) NOT NULL COMMENT '入库数量',
  `intoTIme` varchar(20) DEFAULT NULL COMMENT '入库时间',
  `goodsRfid` varchar(20) NOT NULL COMMENT '物品rfid',
  PRIMARY KEY (`goodsintoId`),
  KEY `goodsIntoObj` (`goodsIntoObj`),
  CONSTRAINT `t_goodsinto_ibfk_1` FOREIGN KEY (`goodsIntoObj`) REFERENCES `t_goods` (`goodsId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_goodsinto
-- ----------------------------
INSERT INTO `t_goodsinto` VALUES ('1', '4', '3', '2024-04-29 16:36:22', '455');
INSERT INTO `t_goodsinto` VALUES ('2', '4', '8', '2024-04-29 17:27:36', '455');

-- ----------------------------
-- Table structure for `t_goodsout`
-- ----------------------------
DROP TABLE IF EXISTS `t_goodsout`;
CREATE TABLE `t_goodsout` (
  `outId` int(11) NOT NULL AUTO_INCREMENT COMMENT '出库id',
  `outGoodsRfid` int(11) NOT NULL COMMENT '物品出库id',
  `outNumb` int(11) NOT NULL COMMENT '出库数量',
  `outTime` varchar(20) DEFAULT NULL COMMENT '出库时间',
  `goodsRfid` varchar(20) NOT NULL COMMENT '物品rfid',
  PRIMARY KEY (`outId`),
  KEY `outGoodsRfid` (`outGoodsRfid`),
  CONSTRAINT `t_goodsout_ibfk_1` FOREIGN KEY (`outGoodsRfid`) REFERENCES `t_goods` (`goodsId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_goodsout
-- ----------------------------
INSERT INTO `t_goodsout` VALUES ('1', '4', '3', '2024-04-29 17:27:59', '455');

-- ----------------------------
-- Table structure for `t_goodstype`
-- ----------------------------
DROP TABLE IF EXISTS `t_goodstype`;
CREATE TABLE `t_goodstype` (
  `goodTypeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '物品类型id',
  `goodTypeName` varchar(20) NOT NULL COMMENT '类型名称',
  PRIMARY KEY (`goodTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_goodstype
-- ----------------------------
INSERT INTO `t_goodstype` VALUES ('1', '高档烟21');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) DEFAULT NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '用户照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) DEFAULT NULL COMMENT '家庭地址',
  `regTime` varchar(20) DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('user1', '123', 'a', '男', '2024-04-09', 'upload/NoImage.jpg', '13511112222', '44@126.com', '四川成都', '2024-04-29 12:32:17');
INSERT INTO `t_userinfo` VALUES ('user2', '123', 'bb', '女', '2024-04-10', 'upload/NoImage.jpg', '13522223333', '44@126.com', '四川成都', '2024-04-29 13:15:38');
