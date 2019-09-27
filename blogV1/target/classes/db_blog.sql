/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : db_blog

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-11-09 07:21:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL COMMENT '博客题目',
  `summary` varchar(400) DEFAULT NULL COMMENT '博客摘要',
  `releaseDate` datetime DEFAULT NULL COMMENT '发布日期',
  `clickHit` int(11) DEFAULT NULL COMMENT '评论次数',
  `replyHit` int(11) DEFAULT NULL COMMENT '回复次数',
  `content` text COMMENT '博客内容',
  `keyWord` varchar(200) DEFAULT NULL COMMENT '关键字',
  `type_id` int(11) DEFAULT NULL COMMENT '外键关联博客类别',
  `user_id` int(11) DEFAULT NULL COMMENT '外键关联博客用户',
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  KEY `blog_ibfk_2` (`user_id`),
  CONSTRAINT `blog_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `blogtype` (`id`),
  CONSTRAINT `blog_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `blogger` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='博客';

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES ('1', '一元拍', '没那么难把你密码', '2018-10-26 16:02:03', '0', '6', '<p>没那么难把你密码</p>', '后即可很快', '2', '1');
INSERT INTO `blog` VALUES ('3', '安抚阿斯顿发', '阿斯顿发阿斯顿发斯蒂芬', '2018-10-26 13:33:03', '0', '1', '<p>阿斯顿发阿斯顿发斯蒂芬</p>', '阿道夫', '2', '1');
INSERT INTO `blog` VALUES ('4', '测试图片', '阿萨德  阿萨德埃尔法违反', '2018-11-02 13:11:50', '0', '0', '<p>阿萨德 &nbsp;阿萨德<img src=\"/ueditor/jsp/upload/image/20181102/1541135467892017385.png\" title=\"1541135467892017385.png\" alt=\"u3.png\"/>埃尔法违反</p>', '按时  阿萨德', '5', '2');
INSERT INTO `blog` VALUES ('5', '23223', '阿道夫', '2018-11-02 14:34:42', '0', '1', '<p>阿道夫<img src=\"/ueditor/jsp/upload/image/20181102/1541140469673036953.png\" title=\"1541140469673036953.png\" alt=\"u3.png\"/></p>', '2', '6', '2');
INSERT INTO `blog` VALUES ('6', '阿斯顿发送到', '阿萨德法师打发斯蒂芬 阿萨德', '2018-11-06 16:32:53', '0', '0', '<p>阿萨德法师打发斯蒂芬 阿萨德</p>', '阿萨德 给对方的股份', '4', '1');
INSERT INTO `blog` VALUES ('7', '是反过来是否客观上的', '阿萨德 饿饿特然', '2018-11-07 14:27:41', '0', '0', '<p>阿萨德 饿饿特然<br/></p>', '', '2', '1');
INSERT INTO `blog` VALUES ('8', '测试测试', '阿斯顿发拉几十块的发上来的alsjdlkl', '2018-11-08 15:42:31', '0', '0', '<p>阿斯顿发拉几十块的发上来的</p><p>alsjdlkl</p>', '', '2', '1');
INSERT INTO `blog` VALUES ('9', '测试2', '', '2018-11-09 07:19:16', '0', '0', '<p><img src=\"/ueditor/jsp/upload/image/20181109/1541719129379087148.png\" title=\"1541719129379087148.png\" alt=\"u3.png\"/></p>', '爱上了得发', '4', '1');

-- ----------------------------
-- Table structure for blogger
-- ----------------------------
DROP TABLE IF EXISTS `blogger`;
CREATE TABLE `blogger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '博主姓名',
  `password` varchar(100) NOT NULL COMMENT '博主密码',
  `profile` text COMMENT '博主信息',
  `nickname` varchar(50) DEFAULT NULL COMMENT '博主昵称',
  `sign` varchar(100) DEFAULT NULL COMMENT '博主签名',
  `imagename` varchar(100) DEFAULT NULL COMMENT '博主头像路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='博主';

-- ----------------------------
-- Records of blogger
-- ----------------------------
INSERT INTO `blogger` VALUES ('1', 'sa', 'sa', '', 'sa', '努力学习web开发', 'u1.png');
INSERT INTO `blogger` VALUES ('2', 'bb', 'bb', '', '呵呵', '天下无bug', 'u2.png');

-- ----------------------------
-- Table structure for blogtype
-- ----------------------------
DROP TABLE IF EXISTS `blogtype`;
CREATE TABLE `blogtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(30) DEFAULT NULL COMMENT '博客类别',
  `orderNum` int(11) DEFAULT NULL COMMENT '博客排序',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `blogtype_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `blogger` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='博客类型';

-- ----------------------------
-- Records of blogtype
-- ----------------------------
INSERT INTO `blogtype` VALUES ('2', '大数据', '1', '1');
INSERT INTO `blogtype` VALUES ('4', '通天塔,', '33', '1');
INSERT INTO `blogtype` VALUES ('5', '吃饭', '0', '2');
INSERT INTO `blogtype` VALUES ('6', '写代码', '0', '2');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userIp` varchar(50) DEFAULT NULL COMMENT '评论者的ip',
  `content` varchar(1000) DEFAULT NULL COMMENT '评论内容',
  `commentDate` datetime DEFAULT NULL COMMENT '评论日期',
  `state` int(11) DEFAULT NULL COMMENT '审核状态',
  `blog_id` int(11) DEFAULT NULL COMMENT '外键关联具体博客',
  PRIMARY KEY (`id`),
  KEY `blog_id` (`blog_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='评论';

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '0:0:0:0:0:0:0:1', '阿斯顿发', '2018-10-30 16:55:36', '1', '1');
INSERT INTO `comment` VALUES ('2', '0:0:0:0:0:0:0:1', '沃尔沃无', '2018-11-02 08:23:46', '1', '1');
INSERT INTO `comment` VALUES ('3', '0:0:0:0:0:0:0:1', '5555', '2018-11-02 08:27:07', '1', '3');
INSERT INTO `comment` VALUES ('4', '0:0:0:0:0:0:0:1', 'aasdf', '2018-11-08 10:11:57', '1', '1');
INSERT INTO `comment` VALUES ('5', '0:0:0:0:0:0:0:1', '999', '2018-11-08 10:12:16', '1', '1');
INSERT INTO `comment` VALUES ('6', '0:0:0:0:0:0:0:1', '阿斯顿发 地方', '2018-11-08 10:15:54', '1', '1');
INSERT INTO `comment` VALUES ('7', '0:0:0:0:0:0:0:1', '哈啊哈', '2018-11-08 10:16:22', '1', '1');
INSERT INTO `comment` VALUES ('8', '0:0:0:0:0:0:0:1', 'asdfa', '2018-11-08 14:22:08', '1', '5');

-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS `link`;
CREATE TABLE `link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `linkname` varchar(100) DEFAULT NULL COMMENT '友情链接名',
  `linkurl` varchar(200) DEFAULT NULL COMMENT '友情链接url',
  `orderNum` int(11) DEFAULT NULL COMMENT '链接排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='友情链接';

-- ----------------------------
-- Records of link
-- ----------------------------
INSERT INTO `link` VALUES ('1', 'freemarker中文', 'http://freemarker.foofun.cn/', '0');
INSERT INTO `link` VALUES ('2', 'W3C中文', 'http://www.w3school.com.cn/', '1');
