SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `wx_act_shaketicket_award`;
CREATE TABLE `wx_act_shaketicket_award` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `is_card` varchar(2) DEFAULT NULL COMMENT '是否卡券0 是1 否',
  `awards_name` varchar(64) DEFAULT NULL COMMENT '奖品名称',
  `owner` varchar(64) DEFAULT NULL COMMENT '发奖公司',
  `card_id` varchar(64) DEFAULT NULL COMMENT '卡券id',
  `img` varchar(100) DEFAULT NULL COMMENT '奖品图片',
  `unit` varchar(10) DEFAULT NULL COMMENT '单位',
  `jwid` varchar(64) DEFAULT NULL COMMENT '公众号原始ID',
  `awards_describe` text COMMENT '奖品描述',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摇一摇奖项表';

INSERT INTO `wx_act_shaketicket_award` (`id`, `is_card`, `awards_name`, `owner`, `card_id`, `img`, `unit`, `jwid`, `awards_describe`, `create_by`) VALUES ('4028588158aec3890158af24df25000a', '0', 'U盘', NULL, '', 'content/shaketicket/default/img/defaultGoods.png', NULL, 'gh_f268aa85d1c7', '兑奖时请向工作人员出示兑奖码', 'admin');
INSERT INTO `wx_act_shaketicket_award` (`id`, `is_card`, `awards_name`, `owner`, `card_id`, `img`, `unit`, `jwid`, `awards_describe`, `create_by`) VALUES ('4028588158aec3890158af251763000b', '0', '手环', NULL, '', 'content/shaketicket/default/img/defaultGoods.png', NULL, 'gh_f268aa85d1c7', '兑奖时请向工作人员出示兑奖码', 'admin');
INSERT INTO `wx_act_shaketicket_award` (`id`, `is_card`, `awards_name`, `owner`, `card_id`, `img`, `unit`, `jwid`, `awards_describe`, `create_by`) VALUES ('4028588158aec3890158af255b37000c', '0', '蓝牙耳机', NULL, '', 'content/shaketicket/default/img/defaultGoods.png', NULL, 'gh_f268aa85d1c7', '兑奖时请向工作人员出示兑奖码', 'admin');

DROP TABLE IF EXISTS `wx_act_shaketicket_config`;
CREATE TABLE `wx_act_shaketicket_config` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `act_id` varchar(32) NOT NULL COMMENT '活动ID',
  `award_id` varchar(32) NOT NULL COMMENT '奖项ID',
  `probability` double(8,3) DEFAULT NULL COMMENT '中奖概率',
  `amount` int(10) DEFAULT NULL COMMENT '总数量',
  `remain_num` int(10) DEFAULT NULL COMMENT '剩余数量',
  `active` varchar(2) DEFAULT NULL COMMENT '启用状态0停用1启用',
  `jwid` varchar(64) DEFAULT NULL COMMENT '公众号原始ID',
  PRIMARY KEY (`id`),
  KEY `idx_actid` (`act_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摇一摇奖项配置表';

INSERT INTO `wx_act_shaketicket_config` (`id`, `act_id`, `award_id`, `probability`, `amount`, `remain_num`, `active`, `jwid`) VALUES ('4028588158aec3890158af287af2000d', '402880ee51cd37910151cd4702720001', '4028588158aec3890158af24df25000a', '0.100', '10', '10', '1', 'gh_f268aa85d1c7');
INSERT INTO `wx_act_shaketicket_config` (`id`, `act_id`, `award_id`, `probability`, `amount`, `remain_num`, `active`, `jwid`) VALUES ('4028588158aec3890158af287af7000e', '402880ee51cd37910151cd4702720001', '4028588158aec3890158af251763000b', '0.100', '10', '10', '1', 'gh_f268aa85d1c7');
INSERT INTO `wx_act_shaketicket_config` (`id`, `act_id`, `award_id`, `probability`, `amount`, `remain_num`, `active`, `jwid`) VALUES ('4028588158aec3890158af287afa000f', '402880ee51cd37910151cd4702720001', '4028588158aec3890158af255b37000c', '0.100', '10', '10', '1', 'gh_f268aa85d1c7');

DROP TABLE IF EXISTS `wx_act_shaketicket_coupon`;
CREATE TABLE `wx_act_shaketicket_coupon` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `act_id` varchar(32) NOT NULL COMMENT '活动ID',
  `awards_id` varchar(32) NOT NULL COMMENT '奖项ID',
  `card_psd` varchar(32) NOT NULL COMMENT '卡券密码',
  `status` varchar(2) DEFAULT '0' COMMENT '状态（0:未领取，1:已领取）',
  `jwid` varchar(64) DEFAULT NULL COMMENT '公众号原始id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_cpsd` (`card_psd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摇一摇卡券表';

DROP TABLE IF EXISTS `wx_act_shaketicket_home`;
CREATE TABLE `wx_act_shaketicket_home` (
  `id` varchar(100) NOT NULL COMMENT 'ID',
  `act_name` varchar(400) DEFAULT NULL COMMENT '活动名称',
  `active_flag` varchar(2) DEFAULT NULL COMMENT '启用状态（0：停用；1：激活）',
  `detail` text COMMENT '活动规则',
  `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `deadlinetime` datetime DEFAULT NULL COMMENT '兑奖截止日期',
  `template` varchar(100) DEFAULT NULL COMMENT '模版简称',
  `count` int(10) DEFAULT NULL COMMENT '抽奖次数',
  `num_per_day` int(11) DEFAULT '0' COMMENT '每日抽奖次数',
  `hdurl` varchar(2000) DEFAULT NULL COMMENT '活动链接',
  `short_url` varchar(255) DEFAULT NULL COMMENT '短链接',
  `foucs_user_can_join` varchar(1) DEFAULT NULL COMMENT '是否关注可参加 0否1是',
  `binding_mobile_can_join` varchar(1) DEFAULT '0' COMMENT '是否绑定手机可参加 0否1是',
  `win_can_join` varchar(1) DEFAULT '0' COMMENT '是否中奖可参与 0：中奖可继续参与 1:中奖不可参与',
  `jwid` varchar(64) NOT NULL COMMENT '公众号原始id',
  `project_code` varchar(32) NOT NULL COMMENT '所属项目编码',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摇一摇活动表';

INSERT INTO `wx_act_shaketicket_home` (`id`, `act_name`, `active_flag`, `detail`, `begin_time`, `end_time`, `deadlinetime`, `template`, `count`, `num_per_day`, `hdurl`, `short_url`, `foucs_user_can_join`, `binding_mobile_can_join`, `win_can_join`, `jwid`, `project_code`, `create_by`, `create_time`) VALUES ('402880ee51cd37910151cd4702720001', '摇一摇', '1', '<p><strong style=\"line-height: 22.8571px; white-space: normal;\">一、活动时间：</strong><br style=\"line-height: 22.8571px; white-space: normal;\"/><span style=\"line-height: 22.8571px; white-space: normal;\">2016年1月1日至2019年12月31日</span><br style=\"line-height: 22.8571px; white-space: normal;\"/><strong style=\"line-height: 22.8571px; white-space: normal;\">二、抽奖方式：</strong><br style=\"line-height: 22.8571px; white-space: normal;\"/><span style=\"line-height: 22.8571px; white-space: normal;\">每人每天可抽3次。</span><br style=\"line-height: 22.8571px; white-space: normal;\"/><strong style=\"line-height: 22.8571px; white-space: normal;\">三、兑奖须知：</strong><br style=\"line-height: 22.8571px; white-space: normal;\"/><span style=\"line-height: 22.8571px; white-space: normal;\">1、用户中奖后请点击领奖，填写姓名、电话、地址，或进入“我的奖品”页面点击兑奖填写。</span><br style=\"line-height: 22.8571px; white-space: normal;\"/><span style=\"line-height: 22.8571px; white-space: normal;\">2、奖品将在10日内送出，到货以实际时间为准。</span><br style=\"line-height: 22.8571px; white-space: normal;\"/><span style=\"line-height: 22.8571px; white-space: normal;\">3、本活动由捷微H5平台提供技术支持，最终解释权归活动主办方所有。</span></p>', '2016-11-01 00:00:00', '2017-12-31 23:59:59', '2019-10-04 14:03:21', 'default', '50', '3', 'http://h5.jeecg.com/jeewx/weixinLinkController.do?link&id=ff80808151d26aca0151d80bfebc044f&actId=402880ee51cd37910151cd4702720001&jwid=gh_f268aa85d1c7', NULL, '0', '0', '0', 'gh_f268aa85d1c7', 'shaketicket', 'admin', '2016-08-31 09:52:05');

DROP TABLE IF EXISTS `wx_act_shaketicket_record`;
CREATE TABLE `wx_act_shaketicket_record` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `act_id` varchar(32) NOT NULL COMMENT '活动ID',
  `award_id` varchar(64) DEFAULT NULL COMMENT '奖品ID',
  `openid` varchar(100) NOT NULL COMMENT '抽奖人openid',
  `awards_seq` int(11) NOT NULL COMMENT '抽奖序号',
  `draw_status` varchar(2) NOT NULL COMMENT '抽奖状态 0 未中奖 1已中奖',
  `receive_status` varchar(2) DEFAULT NULL COMMENT '领取状态 0 未领取 1已领取',
  `draw_time` datetime DEFAULT NULL COMMENT '抽奖时间',
  `receive_time` datetime DEFAULT NULL COMMENT '领取时间',
  `jwid` varchar(64) DEFAULT NULL COMMENT '公众号原始ID',
  `card_psd` varchar(255) DEFAULT NULL COMMENT '奖品券码',
  `mobile` varchar(255) DEFAULT NULL COMMENT '领奖人手机号',
  `address` varchar(255) DEFAULT NULL COMMENT '领奖人手机号',
  `rel_name` varchar(255) DEFAULT NULL COMMENT '领奖人姓名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_actid_awardsseq` (`act_id`,`awards_seq`),
  KEY `idx_openidactid` (`openid`,`act_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摇一摇抽奖记录表';

-- 活动文本
DELETE FROM jw_system_act_txt WHERE act_code='402880ee51cd37910151cd4702720001';
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('402880ee51d3506a0151d38209a8000c', 'controller.exception.nocount', 'hidden', '您的抽奖次数已经用完', '总次数用完', '402880ee51cd37910151cd4702720001', NULL, NULL, '2015-12-24 18:21:33', NULL, '2016-09-27 14:44:49');
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('402880ee51d3506a0151d3829cba000d', 'controller.exception.nownocount', 'hidden', '您今日抽奖次数已经用完', '今日抽奖次数用完', '402880ee51cd37910151cd4702720001', NULL, NULL, '2015-12-24 18:22:11', NULL, '2016-09-27 14:44:49');
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('402880ee51d3506a0151d3832f6c000e', 'index.title', NULL, '摇一摇', '标题', '402880ee51cd37910151cd4702720001', NULL, NULL, '2015-12-24 18:22:48', NULL, '2016-09-27 14:44:49');
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('402880ee51d3506a0151d3875d550010', 'index.tip.nobindingphone', 'hidden', '请在公众号回复手机号进行绑定', '没绑定手机号', '402880ee51cd37910151cd4702720001', NULL, NULL, '2015-12-24 18:27:22', NULL, '2016-09-27 14:44:49');
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('402880ee51d3506a0151d387e7320011', 'index.tip.nofocus', 'hidden', '关注才能参与哦~', '请先关注', '402880ee51cd37910151cd4702720001', NULL, NULL, '2015-12-24 18:27:57', NULL, '2016-09-27 14:44:49');
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('402880ee51d3506a0151d38c437c0012', 'controller.exception.winnotcanjoin', 'hidden', '中奖不可继续参与', '中奖不可继续参与', '402880ee51cd37910151cd4702720001', NULL, NULL, '2015-12-24 18:32:43', NULL, '2016-09-27 14:44:49');
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('402880ee51d7fd1f0151d82a2c900025', 'index.tip.sharefriendcircle', NULL, '摇动手机，大奖从天降！积攒多年的人品终于有用了，你也快来抽奖吧！', '分享到朋友圈的文案', '402880ee51cd37910151cd4702720001', NULL, NULL, '2015-12-25 16:03:41', NULL, '2016-09-27 14:44:49');
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('402880ee51d7fd1f0151d82ab77b0026', 'index.img.share', 'img', 'http://www.h5huodong.com/content/shaketicket/co/img/fx.png', '分享小图', '402880ee51cd37910151cd4702720001', NULL, NULL, '2015-12-25 16:04:16', NULL, '2016-09-27 14:44:49');
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('402880ee51d7fd1f0151d82ba0d20027', 'index.tip.sharefriend', NULL, '积攒多年的人品终于有用了，你也快来抽奖吧！', '分享给好友的文案', '402880ee51cd37910151cd4702720001', NULL, NULL, '2015-12-25 16:05:16', NULL, '2016-09-27 14:44:49');
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('c1bd24aa9b4611e6993528d2440d0560', 'index.tip.sharefriendtitle', NULL, '摇动手机，大奖从天降！', '分享好友的标题', '402880ee51cd37910151cd4702720001', NULL, NULL, '2015-12-25 16:04:16', NULL, '2016-09-27 14:44:49');
INSERT INTO `jw_system_act_txt` (`id`, `code`, `type`, `content`, `discribe`, `act_code`, `jwid`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('ff80808156a193f50156a1ec48c90018', 'statistics', 'hidden', 'var _hmt = _hmt || [];\n(function() {\n  var hm = document.createElement(\"script\");\n  hm.src = \"//hm.baidu.com/hm.js?8e37bf5be2b9827bc5af38b321f6bb38\";\n  var s = document.getElementsByTagName(\"script\")[0]; \n  s.parentNode.insertBefore(hm, s);\n})();', '统计代码', '402880ee51cd37910151cd4702720001', NULL, NULL, '2016-08-19 16:30:32', NULL, '2016-09-27 14:44:49');

-- 项目管理
DELETE FROM jw_system_project WHERE code='shaketicket';
INSERT INTO `jw_system_project` (`id`, `code`, `name`, `img`, `discribe`, `bjurl`, `hdurl`, `gif_url`, `hdzs_url`, `sc_type`, `detail`, `entrance`, `type`, `sort`, `project_classify_id`, `application_type`, `creat_name`, `creat_time`, `update_name`, `update_time`) VALUES ('402880ee51d7fd1f0151d80cd8610012', 'shaketicket', '摇一摇', '111.jpg', '摇一摇是经典的抽奖活动形式,通过摇手机或点击按钮模拟摇一摇即可抽奖,在H5活动平台中商家通过简单配置奖品，中奖概率及奖品的券码即可发布活动，用户参与活动后，商家可在后台查看中奖记录。', 'shaketicket/back/wxActShaketicketHome/toAdd.do', '/linksucai/link.do?linkid=402880e657bd3f9a0157bd67e91a0004', '111.jpg', 'shaketicket/toIndex.do?openid=${openid}&appid=${appid}', '1', '<p>&nbsp;&nbsp;&nbsp;<span style=\"font-size:18px\">用户进入活动，摇动手机，即可。中奖会以卡券的形式发放到微信的卡券中。</span></p><p><br/></p><p><img src=\"http://h5huodong.com/upload/20160822/99511471863419461.png\" style=\"height:50%;width:50%;float\"/><img src=\"http://h5huodong.com/upload/20160822/36881471863424011.png\" style=\"height:50%;width:50%;float\"/><img src=\"http://h5huodong.com/upload/20160822/35031471863427537.png\" style=\"height:50%;width:50%;float\"/></p><p>\n	</p><p>\n	</p><p>\n	</p><p>\n	</p>', 'https://w.url.cn/s/Ai5sFPi', '1', '07', 'ff808081566df0a601566df0a69a0000', '', NULL, '2015-12-25 15:31:39', NULL, NULL);

-- 菜单管理
INSERT INTO `jw_system_auth` (`id`, `auth_id`, `auth_name`, `is_logs`, `auth_type`, `auth_desc`, `auth_contr`, `parent_auth_id`, `sort_no`, `biz_level`, `leaf_ind`, `del_stat`, `icon_type`) VALUES ('1533778657', 'cc408aa89b7411e8bfd93ca0672e915d', '摇一摇', NULL, '0', NULL, '/shaketicket/back/wxActShaketicketHome/list.do', 'aa735943eb4410368c0028dacd75e20f', '10', '2', NULL, '0', '');

-- 将主表信息插入微信jwid中
INSERT INTO `weixin_huodong_biz_jwid` (`id`, `table_name`, `table_remake`, `table_type`) VALUES ('ff80808152a2abd20153592394830011', 'wx_act_shaketicket_home', '摇一摇活动表', '2');
INSERT INTO `weixin_huodong_biz_jwid` (`id`, `table_name`, `table_remake`, `table_type`) VALUES ('ff80808152a2abd20153592394830038', 'wx_act_shaketicket_award', '摇一摇奖项表', '1');


-- author: zhangweijian----------date:20181021--------for:添加摇一摇注册表
DROP TABLE IF EXISTS `wx_act_shaketicket_registration`;
CREATE TABLE `wx_act_shaketicket_registration` (
  `id` varchar(32) NOT NULL COMMENT '序号',
  `act_id` varchar(32) DEFAULT NULL COMMENT '活动id',
  `openid` varchar(50) DEFAULT NULL COMMENT 'openID',
  `nickname` varchar(200) DEFAULT NULL COMMENT '昵称',
  `headimg` varchar(500) DEFAULT NULL COMMENT '头像',
  `shake_count` int(11) DEFAULT '0' COMMENT '摇一摇总次数',
  `day_shake_num` int(11) DEFAULT '0' COMMENT '当天摇一摇次数',
  `last_shake_date` date DEFAULT NULL COMMENT '上次摇一摇日期',
  `award_status` varchar(2) DEFAULT NULL COMMENT '中奖状态：''1''：已中奖，''0''：未中奖',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_actid,openid` (`act_id`,`openid`),
  KEY `idx_actid` (`act_id`),
  KEY `idx_openid` (`openid`),
  KEY `idx_lastshakedate` (`last_shake_date`),
  KEY `idx_awardstatus` (`award_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摇一摇注册表';

ALTER TABLE `wx_act_shaketicket_home`
ADD COLUMN `extra_lucky_draw`  varchar(255) NULL COMMENT '是否有额外的抽奖机会，0：有，1没有' AFTER `short_url`;
-- author: zhangweijian----------date:20181021--------for:添加摇一摇注册表

-- author: zhangweijian----------date:20181022--------for:摇一摇记录表添加兑奖码字段
ALTER TABLE `wx_act_shaketicket_record`
ADD COLUMN `award_code`  varchar(64) NULL COMMENT '兑奖码' AFTER `draw_status`;

ALTER TABLE `wx_act_shaketicket_record`
ADD UNIQUE INDEX `uniq_awardcode` (`award_code`) ;
-- author: zhangweijian----------date:20181022--------for:摇一摇记录表添加兑奖码字段

-- author: zhangweijian----------date:20181023--------for:摇一摇新增分享记录表；修改概率字段长度
ALTER TABLE `wx_act_shaketicket_config`
MODIFY COLUMN `probability`  double(8,6) NULL DEFAULT NULL COMMENT '中奖概率' AFTER `award_id`;

CREATE TABLE `wx_act_shaketicket_share_record` (
  `id` varchar(32) NOT NULL COMMENT '序号',
  `act_id` varchar(32) DEFAULT NULL COMMENT '活动id',
  `openid` varchar(50) DEFAULT NULL COMMENT 'openid',
  `share_date` date DEFAULT NULL COMMENT '分享日期',
  `type` varchar(255) DEFAULT NULL COMMENT '分享类型：0朋友，1朋友圈',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_actid` (`act_id`),
  KEY `idx_openid` (`openid`),
  KEY `idx_sharedate` (`share_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摇一摇分享记录表';
-- author: zhangweijian----------date:20181023--------for:摇一摇新增分享记录表；修改概率字段长度

-- author: zhangweijian----------date:20181024--------for:修改概率字段的类型
ALTER TABLE `wx_act_shaketicket_config`
MODIFY COLUMN `probability`  decimal(8,6) NULL DEFAULT NULL COMMENT '中奖概率' AFTER `award_id`;
-- author: zhangweijian----------date:20181024--------for:修改概率字段的类型

-- author: zhangweijian----------date:20181112--------for:设置默认头像
UPDATE wx_act_shaketicket_registration SET headimg='content/luckyroulette/img/defaultHeadImg.png'
WHERE headimg is null OR headimg ='';
-- author: zhangweijian----------date:20181112--------for:设置默认头像

-- author: zhangweijian----------date:20181114--------for:更新模板code
UPDATE wx_act_shaketicket_home SET template='newshake' WHERE template='default' OR template IS NULL OR template='';
-- author: zhangweijian----------date:20181114--------for:更新模板code

-- author: zhangweijian----------date:20181220--------for：新增广告字段
ALTER TABLE `wx_act_shaketicket_home`
ADD COLUMN `is_set_adv`  varchar(10) NULL COMMENT '是否设置广告图' AFTER `win_can_join`,
ADD COLUMN `adv_img`  varchar(255) NULL COMMENT '广告图' AFTER `is_set_adv`,
ADD COLUMN `adv_img2`  varchar(255) NULL COMMENT '广告图2' AFTER `adv_img`,
ADD COLUMN `bg_img`  varchar(255) NULL COMMENT '背景图' AFTER `adv_img`;

UPDATE `wx_act_shaketicket_home` SET is_set_adv = '0' WHERE is_set_adv='' OR is_set_adv IS NULL;
-- author: zhangweijian----------date:20181220--------for：新增广告字段



-- author:zhaofei date:20190822 for：修改wx_act_shaketicket_home表的hdurl
UPDATE `wx_act_shaketicket_home` SET `hdurl`='${domain}/linksucai/link.do?linkid=402880ee51d7fd1f0151d80cd8610012&actId=402880ee51cd37910151cd4702720001&jwid=gh_f268aa85d1c7' WHERE (`id`='402880ee51cd37910151cd4702720001');
-- author:zhaofei date:20190822 for：修改wx_act_shaketicket_home表的hdurl

-- author:zhaofei date:20190822 for：修改jw_system_project表的hdurl
UPDATE `jw_system_project` SET `hdurl`='${domain}/linksucai/link.do?linkid=402880ee51d7fd1f0151d80cd8610012' WHERE (`id`='402880ee51d7fd1f0151d80cd8610012');
-- author:zhaofei date:20190822 for：修改jw_system_project表的hdurl

-- author:zhaofei date:20191008 for：扩展图片路径长度
ALTER TABLE `wx_act_shaketicket_award`
MODIFY COLUMN `img` varchar(200)  NULL DEFAULT NULL COMMENT '奖品图片' AFTER `card_id`;
-- author:zhaofei date:20191008 for：扩展图片路径长度

-- author:zhaofei date:20191008 for：更新项目默认图片
UPDATE `jw_system_project` SET  `logo_img`='http://static.h5huodong.com/upload/img/system/82e46d4506bb411f92151a4bbb806e41.jpg', `img`='http://static.h5huodong.com/upload/img/system/d8ab5eee41db4b8f88fcbec7707910a3.jpg', `gif_url`='http://static.h5huodong.com/upload/img/system/d8ab5eee41db4b8f88fcbec7707910a3.jpg' WHERE (`id`='402880ee51d7fd1f0151d80cd8610012');
-- author:zhaofei date:20191008 for：更新项目默认图片

-- author:zhaofei date:20190930 for：修改默认分享图地址
UPDATE `jw_system_act_txt` SET  `content` = 'http://static.h5huodong.com/upload/common/shaketicket_fximg.png' WHERE (`id`='402880ee51d7fd1f0151d82ab77b0026');
-- author:zhaofei date:20190930 for：修改默认分享图地址

