//package com.jeecg.p3.shaketicket.dao.impl;
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
//import com.jeecg.p3.shaketicket.dao.WxActShaketicketShareRecordDao;
//import com.jeecg.p3.shaketicket.entity.WxActShaketicketShareRecord;
//
///**
// * 描述：</b>摇一摇分享记录表<br>
// * @author：junfeng.zhou
// * @since：2018年10月17日 10时27分39秒 星期三 
// * @version:1.0
// */
//@Repository("wxActShaketicketShareRecordDao")
//public class WxActShaketicketShareRecordDaoImpl extends GenericDaoDefault<WxActShaketicketShareRecord> implements WxActShaketicketShareRecordDao{
//
//	@Override
//	public Integer count(PageQuery<WxActShaketicketShareRecord> pageQuery) {
//		return (Integer) super.queryOne("count",pageQuery);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActShaketicketShareRecord> queryPageList(
//			PageQuery<WxActShaketicketShareRecord> pageQuery,Integer itemCount) {
//		PageQueryWrapper<WxActShaketicketShareRecord> wrapper = new PageQueryWrapper<WxActShaketicketShareRecord>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
//		return (List<WxActShaketicketShareRecord>)super.query("queryPageList",wrapper);
//	}
//
//	/**
//	 * @功能：根据活动id和openid查询分享记录
//	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActShaketicketShareRecord> queryByActIdAndOpenid(String actId, String openid, String shareDate) {
//		Map<String,String> param = Maps.newConcurrentMap();
//		param.put("actId", actId);
//		param.put("openid", openid);
//		param.put("shareDate", shareDate);
//		return super.query("queryByActIdAndOpenid",param);
//	}
//
//
//}
//
