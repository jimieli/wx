//package com.jeecg.p3.scratchcards.dao.impl;
//
//import java.util.List;
//import java.util.Map;
//
//import org.jeecgframework.p3.core.utils.common.PageQuery;
//import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
//import org.jeecgframework.p3.core.utils.persistence.OptimisticLockingException;
//import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
//import org.springframework.stereotype.Repository;
//
//import com.google.common.collect.Maps;
//import com.jeecg.p3.scratchcards.dao.WxActScratchcardsRegistrationDao;
//import com.jeecg.p3.scratchcards.entity.WxActScratchcardsRegistration;
//
///**
// * 描述：</b>WxActScratchcardsRegistrationDaoImpl<br>
// * @author：junfeng.zhou
// * @since：2016年07月13日 18时42分25秒 星期三
// * @version:1.0
// */
//@Repository("wxActScratchcardsRegistrationDao")
//public class WxActScratchcardsRegistrationDaoImpl extends GenericDaoDefault<WxActScratchcardsRegistration> implements WxActScratchcardsRegistrationDao{
//
//	@Override
//	public Integer count(PageQuery<WxActScratchcardsRegistration> pageQuery) {
//		return (Integer) super.queryOne("count",pageQuery);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActScratchcardsRegistration> queryPageList(
//			PageQuery<WxActScratchcardsRegistration> pageQuery,Integer itemCount) {
//		PageQueryWrapper<WxActScratchcardsRegistration> wrapper = new PageQueryWrapper<WxActScratchcardsRegistration>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
//		return (List<WxActScratchcardsRegistration>)super.query("queryPageList",wrapper);
//	}
//
//	@Override
//	public WxActScratchcardsRegistration getOpenid(String openid, String actId) {
//		Map<String,String> params=Maps.newConcurrentMap();
//		params.put("actId", actId);
//		params.put("openid", openid);
//		return (WxActScratchcardsRegistration)super.queryOne("getOpenid",params);
//	}
//
//	@Override
//	public void updateNumSameDay(Integer count, Integer numPerDay, String date, String id) {
//		Map<String,Object> param=Maps.newConcurrentMap();
//		param.put("id", id);
//		param.put("count", count);
//		param.put("numPerDay", numPerDay);
//		param.put("date", date);
//		int row = super.getSqlSession().update(getStatementId("updateNumSameDay"), param);
//		if (row == 0) {
//			throw new OptimisticLockingException("乐观锁异常");
//		}
//	}
//
//	@Override
//	public void updateNumDistinctDay(Integer count, Integer numPerDay,
//			String date, String id) {
//		Map<String,Object> param=Maps.newConcurrentMap();
//		param.put("id", id);
//		param.put("count", count);
//		param.put("numPerDay", numPerDay);
//		param.put("date", date);
//		int row = super.getSqlSession().update(getStatementId("updateNumDistinctDay"), param);
//		if (row == 0) {
//			throw new OptimisticLockingException("乐观锁异常");
//		}
//	}
//
//	@Override
//	public Integer queryCountByActIdAndJwid(String actId, String jwid) {
//		Map<String,String> params=Maps.newConcurrentMap();
//		params.put("actId", actId);
//		params.put("jwid", jwid);
//		return (Integer) super.queryOne("queryCountByActIdAndJwid", params);
//	}
//
//
//}
//
