package com.jeecg.p3.shaketicket.dao;

import com.jeecg.p3.shaketicket.entity.WxActShaketicketCoupon;
import org.apache.ibatis.annotations.Param;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import java.util.List;

/**
 * 描述：</b>WxActShaketicketCouponDao<br>
 * @author：junfeng.zhou
 * @since：2016年03月24日 14时33分55秒 星期四 
 * @version:1.0
 */
public interface WxActShaketicketCouponDao extends GenericDao<WxActShaketicketCoupon>{
	
	public Integer count(PageQuery<WxActShaketicketCoupon> pageQuery);
	
	public List<WxActShaketicketCoupon> queryPageList(PageQueryWrapper<WxActShaketicketCoupon> wrapper);
	
	public List<WxActShaketicketCoupon> queryCouponListByActIdAndAwardId(@Param("actId")String actId, @Param("awardId")String awardId);
	
	public Integer updateStatus(@Param("id") String id);
	
}

