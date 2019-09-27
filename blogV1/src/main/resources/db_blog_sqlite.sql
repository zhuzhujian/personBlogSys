/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : db_blog

Target Server Type    : SQLite
Target Server Version : 30000
File Encoding         : 65001

Date: 2018-11-09 07:23:23
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS "blog";
CREATE TABLE "blog" (
"id"  INTEGER(11) NOT NULL,
"title"  TEXT(200) NOT NULL,
"summary"  TEXT(400),
"releaseDate"  TEXT,
"clickHit"  INTEGER(11),
"replyHit"  INTEGER(11),
"content"  TEXT,
"keyWord"  TEXT(200),
"type_id"  INTEGER(11),
"user_id"  INTEGER(11),
PRIMARY KEY ("id"),
FOREIGN KEY ("type_id") REFERENCES "blogtype" ("id"),
FOREIGN KEY ("user_id") REFERENCES "blogger" ("id")
)

;

-- ----------------------------
-- Records of blog
-- ----------------------------
BEGIN;
INSERT INTO "blog" VALUES ('1', '一元拍', '没那么难把你密码', '2018-10-26 16:02:03', '0', '6', '<p>没那么难把你密码</p>', '后即可很快', '2', '1');
INSERT INTO "blog" VALUES ('3', '安抚阿斯顿发', '阿斯顿发阿斯顿发斯蒂芬', '2018-10-26 13:33:03', '0', '1', '<p>阿斯顿发阿斯顿发斯蒂芬</p>', '阿道夫', '2', '1');
INSERT INTO "blog" VALUES ('4', '测试图片', '阿萨德  阿萨德埃尔法违反', '2018-11-02 13:11:50', '0', '0', '<p>阿萨德 &nbsp;阿萨德<img src="/ueditor/jsp/upload/image/20181102/1541135467892017385.png" title="1541135467892017385.png" alt="u3.png"/>埃尔法违反</p>', '按时  阿萨德', '5', '2');
INSERT INTO "blog" VALUES ('5', '23223', '阿道夫', '2018-11-02 14:34:42', '0', '1', '<p>阿道夫<img src="/ueditor/jsp/upload/image/20181102/1541140469673036953.png" title="1541140469673036953.png" alt="u3.png"/></p>', '2', '6', '2');
INSERT INTO "blog" VALUES ('6', '阿斯顿发送到', '阿萨德法师打发斯蒂芬 阿萨德', '2018-11-06 16:32:53', '0', '0', '<p>阿萨德法师打发斯蒂芬 阿萨德</p>', '阿萨德 给对方的股份', '4', '1');
INSERT INTO "blog" VALUES ('7', '是反过来是否客观上的', '阿萨德 饿饿特然', '2018-11-07 14:27:41', '0', '0', '<p>阿萨德 饿饿特然<br/></p>', '', '2', '1');
INSERT INTO "blog" VALUES ('8', '测试测试', '阿斯顿发拉几十块的发上来的alsjdlkl', '2018-11-08 15:42:31', '0', '0', '<p>阿斯顿发拉几十块的发上来的</p><p>alsjdlkl</p>', '', '2', '1');
INSERT INTO "blog" VALUES ('9', '测试2', '', '2018-11-09 07:19:16', '0', '0', '<p><img src="/ueditor/jsp/upload/image/20181109/1541719129379087148.png" title="1541719129379087148.png" alt="u3.png"/></p>', '爱上了得发', '4', '1');
COMMIT;

-- ----------------------------
-- Table structure for blogger
-- ----------------------------
DROP TABLE IF EXISTS "blogger";
CREATE TABLE "blogger" (
"id"  INTEGER(11) NOT NULL,
"username"  TEXT(50) NOT NULL,
"password"  TEXT(100) NOT NULL,
"profile"  TEXT,
"nickname"  TEXT(50),
"sign"  TEXT(100),
"imagename"  TEXT(100),
PRIMARY KEY ("id")
)

;

-- ----------------------------
-- Records of blogger
-- ----------------------------
BEGIN;
INSERT INTO "blogger" VALUES ('1', 'sa', 'sa', '', 'sa', '努力学习web开发', 'u1.png');
INSERT INTO "blogger" VALUES ('2', 'bb', 'bb', '', '呵呵', '天下无bug', 'u2.png');
COMMIT;

-- ----------------------------
-- Table structure for blogtype
-- ----------------------------
DROP TABLE IF EXISTS "blogtype";
CREATE TABLE "blogtype" (
"id"  INTEGER(11) NOT NULL,
"typeName"  TEXT(30),
"orderNum"  INTEGER(11),
"user_id"  INTEGER(11),
PRIMARY KEY ("id"),
FOREIGN KEY ("user_id") REFERENCES "blogger" ("id")
)

;

-- ----------------------------
-- Records of blogtype
-- ----------------------------
BEGIN;
INSERT INTO "blogtype" VALUES ('2', '大数据', '1', '1');
INSERT INTO "blogtype" VALUES ('4', '通天塔,', '33', '1');
INSERT INTO "blogtype" VALUES ('5', '吃饭', '0', '2');
INSERT INTO "blogtype" VALUES ('6', '写代码', '0', '2');
COMMIT;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS "comment";
CREATE TABLE "comment" (
"id"  INTEGER(11) NOT NULL,
"userIp"  TEXT(50),
"content"  TEXT(1000),
"commentDate"  TEXT,
"state"  INTEGER(11),
"blog_id"  INTEGER(11),
PRIMARY KEY ("id"),
FOREIGN KEY ("blog_id") REFERENCES "blog" ("id")
)

;

-- ----------------------------
-- Records of comment
-- ----------------------------
BEGIN;
INSERT INTO "comment" VALUES ('1', '0:0:0:0:0:0:0:1', '阿斯顿发', '2018-10-30 16:55:36', '1', '1');
INSERT INTO "comment" VALUES ('2', '0:0:0:0:0:0:0:1', '沃尔沃无', '2018-11-02 08:23:46', '1', '1');
INSERT INTO "comment" VALUES ('3', '0:0:0:0:0:0:0:1', '5555', '2018-11-02 08:27:07', '1', '3');
INSERT INTO "comment" VALUES ('4', '0:0:0:0:0:0:0:1', 'aasdf', '2018-11-08 10:11:57', '1', '1');
INSERT INTO "comment" VALUES ('5', '0:0:0:0:0:0:0:1', '999', '2018-11-08 10:12:16', '1', '1');
INSERT INTO "comment" VALUES ('6', '0:0:0:0:0:0:0:1', '阿斯顿发 地方', '2018-11-08 10:15:54', '1', '1');
INSERT INTO "comment" VALUES ('7', '0:0:0:0:0:0:0:1', '哈啊哈', '2018-11-08 10:16:22', '1', '1');
INSERT INTO "comment" VALUES ('8', '0:0:0:0:0:0:0:1', 'asdfa', '2018-11-08 14:22:08', '1', '5');
COMMIT;

-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS "link";
CREATE TABLE "link" (
"id"  INTEGER(11) NOT NULL,
"linkname"  TEXT(100),
"linkurl"  TEXT(200),
"orderNum"  INTEGER(11),
PRIMARY KEY ("id")
)

;

-- ----------------------------
-- Records of link
-- ----------------------------
BEGIN;
INSERT INTO "link" VALUES ('1', 'freemarker中文', 'http://freemarker.foofun.cn/', '0');
INSERT INTO "link" VALUES ('2', 'W3C中文', 'http://www.w3school.com.cn/', '1');
COMMIT;

-- ----------------------------
-- Indexes structure for table blog
-- ----------------------------
CREATE INDEX "type_id"
ON "blog" ("type_id");
CREATE INDEX "blog_ibfk_2"
ON "blog" ("user_id");

-- ----------------------------
-- Indexes structure for table blogtype
-- ----------------------------
CREATE INDEX "user_id"
ON "blogtype" ("user_id");

-- ----------------------------
-- Indexes structure for table comment
-- ----------------------------
CREATE INDEX "blog_id"
ON "comment" ("blog_id");
