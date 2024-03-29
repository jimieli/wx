//package com.jeecg.p3.shaketicket.dao.impl;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.jeecgframework.p3.core.common.utils.DateUtil;
//import org.jeecgframework.p3.core.utils.common.PageQuery;
//import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
//import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
//import org.springframework.stereotype.Repository;
//
//import com.google.common.collect.Maps;
//import com.jeecg.p3.shaketicket.dao.WxActShaketicketRecordDao;
//import com.jeecg.p3.shaketicket.entity.WxActShaketicketRecord;
//
///**
// * 描述：</b>WxActShaketicketRecordDaoImpl<br>
// * @author：pituo
// * @since：2015年12月22日 19时03分50秒 星期二 
// * @version:1.0
// */
//@Repository("wxActShaketicketRecordDao")
//public class WxActShaketicketRecordDaoImpl extends GenericDaoDefault<WxActShaketicketRecord> implements WxActShaketicketRecordDao{
//
//	@Override
//	public Integer count(PageQuery<WxActShaketicketRecord> pageQuery) {
//		return (Integer) super.queryOne("count",pageQuery);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActShaketicketRecord> queryPageList(
//			PageQuery<WxActShaketicketRecord> pageQuery,Integer itemCount) {
//		PageQueryWrapper<WxActShaketicketRecord> wrapper = new PageQueryWrapper<WxActShaketicketRecord>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
//		return (List<WxActShaketicketRecord>)super.query("queryPageList",wrapper);
//	}
//
//	@Override
//	public Map<String, Integer> getRecordCountByActIdAndOpenid(String actId, String openid,Date currDate) {
//		// TODO Auto-generated method stub
//		Map<String,String> param = Maps.newConcurrentMap();
//		param.put("openid", openid);
//		param.put("actId", actId);
//        param.put("currDate", DateUtil.date2Str(currDate,"yyyy-MM-dd"));
//		return (Map<String, Integer>)super.getMap("getRecordCountByActIdAndOpenid", param);
//	}
//
//	@Override
//	public Integer getMaxAwardsSeq(String actId) {
//		Integer maxAwardsSeq = (Integer) super.queryOne("getMaxSeq",actId);
//		return maxAwardsSeq==null?0:maxAwardsSeq;
//	}
//
//	@Override
//	public List<WxActShaketicketRecord> queryMyAwardsRecordByOpenidAndActid(
//			String openid, String actId) {
//		Map<String,String> param = Maps.newConcurrentMap();
//		param.put("openid", openid);
//		param.put("actId", actId);
//		return (List<WxActShaketicketRecord>)super.query("queryMyAwardsRecordByOpenidAndActid",param);
//	}
//
//	@Override
//	public List<WxActShaketicketRecord> queryBargainAwardsByActId(String actId, String drawStatus) {
//		// TODO Auto-generated method stub
//		Map<String,String> param = Maps.newConcurrentMap();
//		param.put("actId", actId);
//		//update-begin--Author:zhangweijian  Date: 20180925 for：添加一个中奖状态参数
//		param.put("drawStatus", drawStatus);
//		//update-end--Author:zhangweijian  Date: 20180925 for：添加一个中奖状态参数
//		return (List<WxActShaketicketRecord>)super.query("queryBargainAwardsByActId",param);
//	}
//	@Override
//	public List<WxActShaketicketRecord> queryBargainAwardsByActIdForWin(String actId) {
//		// TODO Auto-generated method stub
//		Map<String,String> param = Maps.newConcurrentMap();
//		param.put("actId", actId);
//		return (List<WxActShaketicketRecord>)super.query("queryBargainAwardsByActIdForWin",param);
//	}
//	
//	//update-begin--Author:zhangweijian  Date: 20180919 for：统计当前活动的参与人数
//	/**
//	 * @功能：统计当前活动的参与人数
//	 */
//	@Override
//	public int getCountByActId(String actId) {
//		return (Integer) super.queryOne("getCountByActId", actId);
//	}
//	//update-end--Author:zhangweijian  Date: 20180919 for：统计当前活动的参与人数
//
//	//update-begin--Author:zhangweijian  Date: 20181022 for：根据actId和兑奖码查询摇奖记录信息
//	@Override
//	public WxActShaketicketRecord queryByActIdAndawardCode(String actId,String awardCode) {
//		Map<String,Object> param = Maps.newConcurrentMap();
//		param.put("actId", actId);
//		param.put("awardCode", awardCode);
//		return (WxActShaketicketRecord) super.queryOne("queryByActIdAndawardCode", param);
//	}
//	//update-end--Author:zhangweijian  Date: 20181022 for：根据actId和兑奖码查询摇奖记录信息
//}
//
