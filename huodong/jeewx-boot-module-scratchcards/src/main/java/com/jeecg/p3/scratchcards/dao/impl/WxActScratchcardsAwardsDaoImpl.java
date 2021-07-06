//package com.jeecg.p3.scratchcards.dao.impl;
//
//import java.util.List;
//import java.util.Map;
//
//import org.jeecgframework.p3.core.utils.common.PageQuery;
//import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
//import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
//import org.springframework.stereotype.Repository;
//
//import com.google.common.collect.Maps;
//import com.jeecg.p3.scratchcards.dao.WxActScratchcardsAwardsDao;
//import com.jeecg.p3.scratchcards.entity.WxActScratchcardsAwards;
//import com.jeecg.p3.scratchcards.entity.WxActScratchcardsPrizes;
//
///**
// * 描述：</b>WxActScratchcardsAwardsDaoImpl<br>
// * @author：junfeng.zhou
// * @since：2016年07月13日 18时42分23秒 星期三
// * @version:1.0
// */
//@Repository("wxActScratchcardsAwardsDao")
//public class WxActScratchcardsAwardsDaoImpl extends GenericDaoDefault<WxActScratchcardsAwards> implements WxActScratchcardsAwardsDao{
//
//	@Override
//	public Integer count(PageQuery<WxActScratchcardsAwards> pageQuery) {
//		return (Integer) super.queryOne("count",pageQuery);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActScratchcardsAwards> queryPageList(
//			PageQuery<WxActScratchcardsAwards> pageQuery,Integer itemCount) {
//		PageQueryWrapper<WxActScratchcardsAwards> wrapper = new PageQueryWrapper<WxActScratchcardsAwards>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
//		return (List<WxActScratchcardsAwards>)super.query("queryPageList",wrapper);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActScratchcardsAwards> queryAwards(String jwid,String createBy) {
//		Map<String, String> param=Maps.newConcurrentMap();
//		param.put("jwid", jwid);
//		param.put("createBy", createBy);
//		return (List<WxActScratchcardsAwards>)super.query("queryAwards", param);
//	}
//
//	@Override
//	public List<WxActScratchcardsAwards> queryAwards(String jwid) {
//		Map<String,String> param = Maps.newConcurrentMap();
//		param.put("jwid", jwid);
//		return super.query("queryAwards2", param);
//	}
//
//	//update-begin--Author:zhangweijian  Date: 20180329 for：根据jwid，创建人，奖项名称查询奖项表
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActScratchcardsAwards> queryAwardsByName(String jwid, String createBy, String awardsName) {
//		Map<String,String> param = Maps.newConcurrentMap();
//		param.put("jwid", jwid);
//		param.put("createBy", createBy);
//		param.put("awardName", awardsName);
//		return (List<WxActScratchcardsAwards>)super.query("queryAwardsByName",param);
//	}
//	//update-end--Author:zhangweijian  Date: 20180329 for：根据jwid，创建人，奖项名称查询奖项表
//
//
//}
//
