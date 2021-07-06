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
//import com.jeecg.p3.shaketicket.dao.WxActShaketicketRegistrationDao;
//import com.jeecg.p3.shaketicket.entity.WxActShaketicketRegistration;
//
///**
// * 描述：</b>摇一摇注册表<br>
// * @author：junfeng.zhou
// * @since：2018年10月12日 15时26分06秒 星期五 
// * @version:1.0
// */
//@Repository("wxActShaketicketRegistrationDao")
//public class WxActShaketicketRegistrationDaoImpl extends GenericDaoDefault<WxActShaketicketRegistration> implements WxActShaketicketRegistrationDao{
//
//	@Override
//	public Integer count(PageQuery<WxActShaketicketRegistration> pageQuery) {
//		return (Integer) super.queryOne("count",pageQuery);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActShaketicketRegistration> queryPageList(
//			PageQuery<WxActShaketicketRegistration> pageQuery,Integer itemCount) {
//		PageQueryWrapper<WxActShaketicketRegistration> wrapper = new PageQueryWrapper<WxActShaketicketRegistration>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
//		return (List<WxActShaketicketRegistration>)super.query("queryPageList",wrapper);
//	}
//
//	/**
//	 * @功能：根据活动id和openid查询用户信息
//	 */
//	@Override
//	public WxActShaketicketRegistration queryByActIdAndOpenid(String actId,String openid) {
//		Map<String,Object> param = Maps.newConcurrentMap();
//		param.put("actId", actId);
//		param.put("openid", openid);
//		return (WxActShaketicketRegistration) super.queryOne("queryByActIdAndOpenid", param);
//	}
//
//	//update-begin--Author:zhangweijian Date:20181114 for：统计参与人数
//	/**
//	 * @功能：统计参与人数
//	 */
//	@Override
//	public int countByActId(String actId) {
//		return (Integer) super.queryOne("countByActId", actId);
//	}
//	//update-end--Author:zhangweijian Date:20181114 for：统计参与人数
//
//}
//
