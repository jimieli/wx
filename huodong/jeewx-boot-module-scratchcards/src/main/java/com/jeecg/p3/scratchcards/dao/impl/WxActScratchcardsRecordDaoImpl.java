//package com.jeecg.p3.scratchcards.dao.impl;
//
//import java.util.List;
//import java.util.Map;
//
//import org.jeecgframework.p3.core.utils.common.PageQuery;
//import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
//import org.jeecgframework.p3.core.utils.common.StringUtils;
//import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
//import org.springframework.stereotype.Repository;
//
//import com.google.common.collect.Maps;
//import com.jeecg.p3.scratchcards.dao.WxActScratchcardsRecordDao;
//import com.jeecg.p3.scratchcards.entity.WxActScratchcardsRecord;
//
///**
// * 描述：</b>WxActScratchcardsRecordDaoImpl<br>
// * @author：junfeng.zhou
// * @since：2016年07月13日 18时42分25秒 星期三
// * @version:1.0
// */
//@Repository("wxActScratchcardsRecordDao")
//public class WxActScratchcardsRecordDaoImpl extends GenericDaoDefault<WxActScratchcardsRecord> implements WxActScratchcardsRecordDao{
//
//	@Override
//	public Integer count(PageQuery<WxActScratchcardsRecord> pageQuery) {
//		return (Integer) super.queryOne("count",pageQuery);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActScratchcardsRecord> queryPageList(
//			PageQuery<WxActScratchcardsRecord> pageQuery,Integer itemCount) {
//		PageQueryWrapper<WxActScratchcardsRecord> wrapper = new PageQueryWrapper<WxActScratchcardsRecord>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
//		return (List<WxActScratchcardsRecord>)super.query("queryPageList",wrapper);
//	}
//
//	@Override
//	public Integer getMaxAwardsSeq(String actId) {
//		Integer maxAwardsSeq = (Integer) super.queryOne("getMaxSeq",actId);
//		return maxAwardsSeq==null?0:maxAwardsSeq;
//	}
//
//	@Override
//	public WxActScratchcardsRecord queryByCode(String code) {
//		Map<String,String> param=Maps.newConcurrentMap();
//		param.put("code", code);
//		return(WxActScratchcardsRecord)super.queryOne("queryByCode",param);
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public List<WxActScratchcardsRecord> queryList(String actId) {
//		Map<String,String> param=Maps.newConcurrentMap();
//		param.put("actId", actId);
//		return(List<WxActScratchcardsRecord>)super.query("queryList",param);
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public List<WxActScratchcardsRecord> queryMyList(String openid,String actId) {
//		Map<String,String> param=Maps.newConcurrentMap();
//		param.put("openid", openid);
//		param.put("actId", actId);
//		return(List<WxActScratchcardsRecord>)super.query("queryMyList",param);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActScratchcardsRecord> exportExcel(String jwid, String actId) {
//		Map<String,String> param = Maps.newConcurrentMap();
//		param.put("jwid", jwid);
//		param.put("actId", actId);
//		return (List<WxActScratchcardsRecord>)super.query("exportExcel", param);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActScratchcardsRecord> exportExcel1(String jwid, String actId) {
//		Map<String,String> param = Maps.newConcurrentMap();
//		param.put("jwid", jwid);
//		param.put("actId", actId);
//		return (List<WxActScratchcardsRecord>)super.query("exportExcel1", param);
//	}
//
//	@Override
//	public Integer countByActId(String actId, String jwid, String openid) {
//		Map<String,String> param = Maps.newConcurrentMap();
//		param.put("jwid", jwid);
//		param.put("actId", actId);
//		if(StringUtils.isNotEmpty(openid)){
//			param.put("openid", openid);
//		}
//		return (Integer) super.queryOne("countByActId", param);
//	}
//
//	@Override
//	public List<WxActScratchcardsRecord> queryListByActId(String actId,
//			String jwid, String openid, int pageNo, int pageSize) {
//		Map<String, Object> param=Maps.newConcurrentMap();
//		param.put("startRow", pageNo);
//		param.put("pageSize", pageSize);
//		param.put("actId", actId);
//		param.put("jwid", jwid);
//		if(StringUtils.isNotEmpty(openid)){
//			param.put("openid", openid);
//		}
//		return (List<WxActScratchcardsRecord>)super.query("queryListByActId", param);
//	}
//
//
//}
//
