SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `wx_act_scratchcards`;
CREATE TABLE `wx_act_scratchcards` (
  `id` varchar(100) NOT NULL COMMENT 'ID',
  `title` varchar(400) default NULL COMMENT '活动名称',
  `description` text COMMENT '活动描述',
  `starttime` datetime default NULL COMMENT '开始时间',
  `endtime` datetime default NULL COMMENT '结束时间',
  `banner` varchar(100) default NULL COMMENT '背景图',
  `count` int(10) default NULL COMMENT '抽奖次数',
  `hdurl` varchar(200) default NULL COMMENT '入口地址',
  `foucs_user_can_join` varchar(1) default '0' COMMENT '是否关注可参加（0否 1是）',
  `binding_mobile_can_join` varchar(1) default '0' COMMENT '是否绑定手机可参加（0否 1是）',
  `num_per_day` int(11) default '0' COMMENT '每日抽奖次数',
  `prize_status` varchar(1) default '0' COMMENT '是否中奖可参与 0：中奖可继续参与 1:中奖不可参与',
  `jwid` varchar(64) default NULL COMMENT '公众号原始id',
  `project_code` varchar(32) default NULL COMMENT '活动编码',
  `create_by` varchar(50) default NULL COMMENT '创建人',
  `create_time` datetime default NULL COMMENT '创建时间',
  `short_url` varchar(255) default NULL COMMENT '短链接',
  `share_status` varchar(1) default NULL COMMENT '是否分享加抽奖次数 0：否 1：是',
  `template_code` varchar(255) default '' COMMENT '主题',
  PRIMARY KEY  (`id`),
  KEY `idx_createby` (`create_by`),
  KEY `idx_jwid` (`jwid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='刮刮乐活动表';

INSERT INTO `wx_act_scratchcards` VALUES ('4028588156a1c4160156a1cc02de0002', '刮刮乐', '<p><strong><p><strong>一、活动时间：</strong><br/>2016年1月1日至2016年12月31日<br/><strong>二、抽奖方式：</strong><br/>每人每天可抽3次。<br/><strong>三、兑奖须知：</strong><br/>1、用户中奖后请点击领奖，填写姓名、电话、地址，或进入“我的奖品”页面点击兑奖填写。<br/>2、奖品将在10日内送出，到货以实际时间为准。<br/>3、本活动由捷微H5平台提供技术支持，最终解释权归活动主办方所有。<br/></p></strong></p>', '2016-08-19 00:00:00', '2019-09-18 23:59:59', null, '1000', 'http://www.h5huodong.com/linksucai/link.do?linkid=ff8080815606c4b3015607104c6e0027&actId=4028588156a1c4160156a1cc02de0002&jwid=gh_f268aa85d1c7', '0', '0', '10', '0', 'gh_f268aa85d1c7', 'scratchcards', 'admin', '2016-10-12 19:28:56', null, '0', 'newTemplet');

DROP TABLE IF EXISTS `wx_act_scratchcards_awards`;
CREATE TABLE `wx_act_scratchcards_awards` (
  `id` varchar(36) NOT NULL default '' COMMENT 'ID',
  `awards_name` varchar(200) default NULL COMMENT '奖项名称',
  `jwid` varchar(64) default NULL COMMENT '公众号原始ID',
  `awards_value` int(11) default NULL COMMENT '奖项值',
  `create_by` varchar(50) default NULL COMMENT '创建人',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `uniq_jwid_creteby_value` (`jwid`,`awards_value`,`create_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='刮刮乐奖项表';

INSERT INTO `wx_act_scratchcards_awards` VALUES ('402880ec57fa52a40157fa8517af0009', '一等奖', 'gh_f268aa85d1c7', '20', 'admin');
INSERT INTO `wx_act_scratchcards_awards` VALUES ('402880ec58afbd900158aff46668000d', '二等奖', 'gh_f268aa85d1c7', '30', 'admin');
INSERT INTO `wx_act_scratchcards_awards` VALUES ('402880ec58afbd900158aff488b2000e', '三等奖', 'gh_f268aa85d1c7', '50', 'admin');
INSERT INTO `wx_act_scratchcards_awards` VALUES ('402880ec58afbd900158aff4a346000f', '四等奖', 'gh_f268aa85d1c7', '80', 'admin');
INSERT INTO `wx_act_scratchcards_awards` VALUES ('402880ec58afbd900158aff4be630010', '五等奖', 'gh_f268aa85d1c7', '100', 'admin');

DROP TABLE IF EXISTS `wx_act_scratchcards_prizes`;
CREATE TABLE `wx_act_scratchcards_prizes` (
  `id` varchar(36) NOT NULL default '',
  `name` varchar(200) default NULL COMMENT '奖品名称',
  `img` varchar(50) default NULL COMMENT '奖品图片',
  `jwid` varchar(64) default NULL COMMENT '微信原始id',
  `create_by` varchar(50) default NULL COMMENT '创建人',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='刮刮乐奖品表';

INSERT INTO `wx_act_scratchcards_prizes` VALUES ('402880ec57fa52a40157fa84f1a90008', '电脑', '/content/scratchcards/img/default_image.png', 'gh_f268aa85d1c7', 'admin');
INSERT INTO `wx_act_scratchcards_prizes` VALUES ('402880ec58afbd900158aff4eb6f0011', '手表', '/content/scratchcards/img/default_image.png', 'gh_f268aa85d1c7', 'admin');

DROP TABLE IF EXISTS `wx_act_scratchcards_record`;
CREATE TABLE `wx_act_scratchcards_record` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `act_id` varchar(32) default NULL COMMENT '活动ID',
  `openid` varchar(100) NOT NULL COMMENT '抽奖人openid',
  `nickname` varchar(200) default NULL COMMENT '昵称',
  `headImg` varchar(255) default NULL COMMENT '头像',
  `create_time` datetime NOT NULL COMMENT '中奖时间',
  `awards_id` varchar(36) default NULL COMMENT '奖项ID',
  `realname` varchar(30) default NULL COMMENT '收货人姓名',
  `phone` varchar(15) default NULL COMMENT '收货人手机号',
  `address` varchar(1000) default NULL COMMENT '收货地址',
  `seq` int(10) default NULL COMMENT '抽奖序号',
  `jwid` varchar(64) NOT NULL COMMENT '公众号原始id',
  `prizes_name` varchar(200) default NULL COMMENT '奖品名称',
  `awards_name` varchar(200) default NULL COMMENT '奖项名称',
  `prizes_state` varchar(32) default NULL COMMENT '中奖状态(0未中奖，1中奖)',
  `code` varchar(100) default NULL COMMENT '中奖码',
  `recieve_status` varchar(1) default NULL COMMENT '领奖状态  0：未领奖 1：已领奖',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `uniq_actid_seq` (`act_id`,`seq`,`openid`),
  KEY `idx_openidactid` (`openid`,`act_id`),
  KEY `idx_creattime` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='刮刮乐抽奖记录表';

DROP TABLE IF EXISTS `wx_act_scratchcards_registration`;
CREATE TABLE `wx_act_scratchcards_registration` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `act_id` varchar(32) NOT NULL COMMENT '活动id',
  `openid` varchar(100) NOT NULL COMMENT '参与人openid',
  `nickname` varchar(200) default NULL COMMENT '昵称',
  `awards_num` int(10) default '0' COMMENT '抽奖次数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` varchar(32) default NULL COMMENT '更新日期',
  `awards_status` varchar(2) default '0' COMMENT '抽奖状态0:未抽奖;1:已抽奖;',
  `jwid` varchar(64) default NULL COMMENT '公众号原始ID',
  `remain_num_day` int(11) default NULL COMMENT '每天的剩余次数',
  PRIMARY KEY  (`id`),
  KEY `idx_creattime` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='刮刮乐参与人员表';

DROP TABLE IF EXISTS `wx_act_scratchcards_relation`;
CREATE TABLE `wx_act_scratchcards_relation` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `act_id` varchar(36) default NULL COMMENT '活动ID',
  `prize_id` varchar(36) default NULL COMMENT '奖品ID',
  `award_id` varchar(36) default NULL COMMENT '奖项ID',
  `jwid` varchar(64) default NULL COMMENT '公众号原始ID',
  `amount` int(10) default NULL COMMENT '数量',
  `remain_num` int(10) default NULL COMMENT '剩余数量',
  `probability` decimal(10,6) default '0.000000' COMMENT '中奖概率',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='刮刮乐奖品配置表';

INSERT INTO `wx_act_scratchcards_relation` VALUES ('402880ec58afbd900158aff8d6f30012', '4028588156a1c4160156a1cc02de0002', '402880ec57fa52a40157fa84f1a90008', '402880ec57fa52a40157fa8517af0009', 'gh_f268aa85d1c7', '10', '10', '0.100000');
INSERT INTO `wx_act_scratchcards_relation` VALUES ('402880ec58afbd900158aff8d6f60013', '4028588156a1c4160156a1cc02de0002', '402880ec58afbd900158aff4eb6f0011', '402880ec58afbd900158aff46668000d', 'gh_f268aa85d1c7', '10', '10', '0.100000');

DROP TABLE IF EXISTS `wx_act_scratchcards_share_record`;
CREATE TABLE `wx_act_scratchcards_share_record` (
  `id` varchar(32) NOT NULL,
  `act_id` varchar(32) default NULL COMMENT '活动id',
  `openid` varchar(64) default NULL COMMENT 'openid',
  `share_date` date default NULL COMMENT '分享日期',
  `type` varchar(1) default NULL COMMENT '分享类型  0：朋友，1：朋友圈',
  `create_time` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`),
  KEY `idx_actid` (`act_id`),
  KEY `idx_openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='刮刮乐分享记录表';

-- 活动文本
delete from jw_system_act_txt where act_code='4028588156a1c4160156a1cc02de0002';
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('ff8080815693178d015693178f281003', 'statistics', 'hidden', 'var _hmt = _hmt || [];\n(function() {\n  var hm = document.createElement(\"script\");\n  hm.src = \"//hm.baidu.com/hm.js?8e37bf5be2b9827bc5af38b321f6bb38\";\n  var s = document.getElementsByTagName(\"script\")[0]; \n  s.parentNode.insertBefore(hm, s);\n})();', '统计脚本', '4028588156a1c4160156a1cc02de0002', NULL, NULL, '2016-08-16 19:23:29', NULL, '2016-08-19 16:13:55');
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('ff8080815693178d015693178f2b1005', 'tip.shareFriendCircle', '请选择', '轻轻松松刮大奖，积攒多年的人品终于有用了，你也赶紧来抽奖吧！', '分享给朋友圈的标题', '4028588156a1c4160156a1cc02de0002', NULL, NULL, '2016-08-16 19:23:30', NULL, '2016-10-25 13:30:26');
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('ff8080815693178d015693178f2c1006', 'tip.shareFriendTile', '请选择', '轻轻松松刮大奖！', '分享朋友标题', '4028588156a1c4160156a1cc02de0002', NULL, NULL, '2016-08-16 19:23:30', NULL, '2016-10-25 13:30:38');
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('ff8080815693178d015693178f2d1007', 'tip.shareFriendDesc', '请选择', '积攒多年的人品终于有用了，你也赶紧来抽奖吧！', '分享朋友的内容', '4028588156a1c4160156a1cc02de0002', NULL, NULL, '2016-08-16 19:23:30', NULL, '2016-10-25 13:30:49');
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('ff8080815693178d015693178f2f1008', 'img.share', 'img', 'http://www.h5huodong.com/content/scratchcards/img/fx1.jpg', '分享图片', '4028588156a1c4160156a1cc02de0002', NULL, NULL, '2016-08-16 19:23:30', NULL, '2016-07-20 16:12:06');

-- 项目管理
DELETE FROM jw_system_project WHERE code='scratchcards';
INSERT INTO `jw_system_project` (`id`, `code`, `name`, `logo_img`, `img`, `discribe`, `bjurl`, `hdurl`, `gif_url`, `hdzs_url`, `sc_type`, `detail`, `entrance`, `type`, `sort`, `project_classify_id`, `application_type`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('ff8080815606c4b3015607104c6e0027', 'scratchcards', '刮刮乐', '', 'c33a2fcc0d2a06d3ddc1c5e45efc7439.jpg', '刮刮乐是经典的抽奖活动形式 在H5活动平台中商家通过简单配置奖品，中奖概率及奖品的券码即可发布活动，用户参与活动后，商家可在后台查看抽奖和中奖记录。', 'scratchcards/back/wxActScratchcards/toAdd.do', 'http://www.h5huodong.com/linksucai/link.do?linkid=ff8080815606c4b3015607104c6e0027', 'c33a2fcc0d2a06d3ddc1c5e45efc7439.jpg', 'scratchcards/toIndex.do?appid=${appid}&openid=${openid}', '2', '<p>&nbsp;&nbsp;&nbsp;<span style=\"font-size:18px\">用户进入活动后，在刮奖区，进行刮奖，中奖需填写个人领奖的基本信息。可查看中奖记录和排行榜</span></p><p><img src=\"http://h5huodong.com/upload/20160823/71021471915578912.png\" style=\"width:50%;height:50%；float:left\"/><img src=\"http://h5huodong.com/upload/20160823/16371471915582083.png\" style=\"width:50%;height:50%；float:left\"/><img src=\"http://h5huodong.com/upload/20160823/87511471915591011.png\" style=\"width:50%;height:50%；float:left\"/></p><p>&nbsp; &nbsp;</p><p></p><p>\n	</p><p>\n	</p><p>\n	</p><p>\n	</p>', '', '1', '06', 'ff808081566df0a601566df0a69a0000', '', NULL, '2016-07-20 14:48:43', NULL, NULL);

-- 菜单管理
INSERT INTO `jw_system_auth` (`id`, `auth_id`, `auth_name`, `is_logs`, `auth_type`, `auth_desc`, `auth_contr`, `parent_auth_id`, `sort_no`, `biz_level`, `leaf_ind`, `del_stat`, `icon_type`) VALUES ('1533779947', 'c8a2af119b7711e8bfd93ca0672e915d', '刮刮乐', NULL, '0', NULL, '/scratchcards/back/wxActScratchcards/list.do', 'aa735943eb4410368c0028dacd75e20f', '11', '2', NULL, '0', '');

-- 将主表信息插入微信jwid表中
INSERT INTO `weixin_huodong_biz_jwid` (`id`, `table_name`, `table_remake`, `table_type`) VALUES ('ff80808152a2abd20153592394830016', 'wx_act_scratchcards', '刮刮乐活动主表', '2');
INSERT INTO `weixin_huodong_biz_jwid` (`id`, `table_name`, `table_remake`, `table_type`) VALUES ('ff80808152a2abd20153592394830036', 'wx_act_scratchcards_awards', '刮刮乐奖项表', '1');
INSERT INTO `weixin_huodong_biz_jwid` (`id`, `table_name`, `table_remake`, `table_type`) VALUES ('ff80808152a2abd20153592394830037', 'wx_act_scratchcards_prizes', '刮刮乐奖品表', '1');


-- author:zhaofei date:20190822 for：修改wx_act_scratchcards表的hdurl
UPDATE `wx_act_scratchcards` SET `hdurl`='${domain}/linksucai/link.do?linkid=ff8080815606c4b3015607104c6e0027&actId=4028588156a1c4160156a1cc02de0002&jwid=gh_f268aa85d1c7' WHERE (`id`='4028588156a1c4160156a1cc02de0002');
-- author:zhaofei date:20190822 for：修改wx_act_scratchcards表的hdurl

-- author:zhaofei date:20190822 for：修改jw_system_project表的hdurl
UPDATE `jw_system_project` SET `hdurl`='${domain}/linksucai/link.do?linkid=ff8080815606c4b3015607104c6e0027' WHERE (`id`='ff8080815606c4b3015607104c6e0027');
-- author:zhaofei date:20190822 for：修改jw_system_project表的hdurl

-- author:zhaofei date:20191009 for：扩展图片路径长度
ALTER TABLE `wx_act_scratchcards_prizes`
MODIFY COLUMN `img` varchar(200)  NULL DEFAULT NULL COMMENT '奖品图片' AFTER `name`;
-- author:zhaofei date:20191009 for：扩展图片路径长度

-- author:zhaofei date:20191009 for：更新项目默认图片
UPDATE `jw_system_project` SET  `logo_img`='http://static.h5huodong.com/upload/img/system/80a9a0ab59514bc9b61dff61bce6a47d.jpg', `img`='http://static.h5huodong.com/upload/img/system/31d23fb6f55442a2857409388156da9b.jpg', `gif_url`='http://static.h5huodong.com/upload/img/system/31d23fb6f55442a2857409388156da9b.jpg' WHERE (`id`='ff8080815606c4b3015607104c6e0027');
-- author:zhaofei date:20191009 for：更新项目默认图片

-- author:zhaofei date:20191009 for：修改默认分享图地址
UPDATE `jw_system_act_txt` SET  `content` = 'http://static.h5huodong.com/upload/common/scratchcards_fximg.jpg' WHERE (`id`='ff8080815693178d015693178f2f1008');
-- author:zhaofei date:20191009 for：修改默认分享图地址



