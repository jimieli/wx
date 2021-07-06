//package com.jeecg.p3.scratchcards.dao.impl;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//import org.jeecgframework.p3.core.utils.common.PageQuery;
//import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
//import org.jeecgframework.p3.core.utils.persistence.OptimisticLockingException;
//import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
//import org.springframework.stereotype.Repository;
//
//import com.google.common.collect.Maps;
//import com.jeecg.p3.scratchcards.dao.WxActScratchcardsRelationDao;
//import com.jeecg.p3.scratchcards.entity.WxActScratchcardsRelation;
//
///**
// * 描述：</b>WxActScratchcardsRelationDaoImpl<br>
// * @author：junfeng.zhou
// * @since：2016年07月13日 18时42分26秒 星期三
// * @version:1.0
// */
//@Repository("wxActScratchcardsRelationDao")
//public class WxActScratchcardsRelationDaoImpl extends GenericDaoDefault<WxActScratchcardsRelation> implements WxActScratchcardsRelationDao{
//
//	@Override
//	public Integer count(PageQuery<WxActScratchcardsRelation> pageQuery) {
//		return (Integer) super.queryOne("count",pageQuery);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActScratchcardsRelation> queryPageList(
//			PageQuery<WxActScratchcardsRelation> pageQuery,Integer itemCount) {
//		PageQueryWrapper<WxActScratchcardsRelation> wrapper = new PageQueryWrapper<WxActScratchcardsRelation>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
//		return (List<WxActScratchcardsRelation>)super.query("queryPageList",wrapper);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActScratchcardsRelation> queryByActId(String actId) {
//		Map<String,String> param=Maps.newConcurrentMap();
//		param.put("actId", actId);
//		return (List<WxActScratchcardsRelation>)super.query("queryByActId", param);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActScratchcardsRelation> queryPrizeAndAward(String actId) {
//		Map<String,String> param=Maps.newConcurrentMap();
//		param.put("actId", actId);
//		return(List<WxActScratchcardsRelation>)super.query("queryPrizeAndAward",param);
//	}
//
//	@Override
//	public void bactchDeleteOldAwards(List<String> ids, String actId) {
//		Map<String,Object> param = Maps.newConcurrentMap();
//		param.put("ids", ids);
//		param.put("actId", actId);
//		super.delete("bactchDeleteOldAwards",param);
//
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActScratchcardsRelation> queryByActIdAndJwid(String actId,
//			String jwid) {
//		Map<String,String> param=Maps.newConcurrentMap();
//		param.put("actId", actId);
//		param.put("jwid", jwid);
//		return (List<WxActScratchcardsRelation>)super.query("queryByActIdAndJwid",param);
//	}
//
//	@Override
//	public void batchDeleteByActId(String actid) {
//		// TODO Auto-generated method stub
//		Map<String,String> param = Maps.newConcurrentMap();
//		param.put("actId", actid);
//		super.delete("batchDeleteByActId", param);
//	}
//
//	@Override
//	public WxActScratchcardsRelation queryId(String id) {
//		Map<String,String> param = Maps.newConcurrentMap();
//		param.put("id", id);
//		return (WxActScratchcardsRelation)super.queryOne("queryId", param);
//	}
//
//
//	@Override
//	public void updateAmountAndRemaiNum(String id, Integer newRemainNum) {
//		Map<String,Object> param=Maps.newConcurrentMap();
//		param.put("id", id);
//		param.put("newRemainNum", newRemainNum);
//		int row = super.getSqlSession().update(getStatementId("updateAmountAndRemaiNum"), param);
//		if (row == 0) {
//			throw new OptimisticLockingException("乐观锁异常");
//		}
//	}
//
//	//update-begin--Author:zhangweijian  Date: 20180330 for：根据奖项id在奖项，奖品关系表查询水果机奖品配置表中奖品是否存在
//	/**
//	 * 根据奖品或奖项id来查询在水果机奖品配置表中是否存在
//	 * */
//	@Override
//	public Boolean validUsed(String awardid, String prizeid) {
//		Map<String,String> param = Maps.newConcurrentMap();
//		if(StringUtils.isEmpty(awardid)){
//			param.put("prizeId", prizeid);
//		}else{
//			param.put("awardId", awardid);
//		}
//		List<WxActScratchcardsRelation> relations=(List<WxActScratchcardsRelation>)super.query("validUsed",param);
//		if(relations.size()>0){
//			return true;
//		}else{
//			return false;
//		}
//	}
//	//update-end--Author:zhangweijian  Date: 20180330 for：根据奖项id在奖项，奖品关系表查询水果机奖品配置表中奖品是否存在
//}
//
