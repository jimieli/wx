package com.jeecg.p3.shaketicket.dao;

import com.jeecg.p3.shaketicket.entity.WxActShaketicketRegistration;
import org.apache.ibatis.annotations.Param;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import java.util.List;

/**
 * 描述：</b>摇一摇注册表<br>
 * @author：junfeng.zhou
 * @since：2018年10月12日 15时26分06秒 星期五 
 * @version:1.0
 */
public interface WxActShaketicketRegistrationDao extends GenericDao<WxActShaketicketRegistration>{
	
	public Integer count(PageQuery<WxActShaketicketRegistration> pageQuery);
	
	public List<WxActShaketicketRegistration> queryPageList(PageQueryWrapper<WxActShaketicketRegistration> wrapper);

	/**
	 * @功能：根据活动id和openid查询用户信息
	 * @param actId
	 * @param openid
	 * @return
	 */
	public WxActShaketicketRegistration queryByActIdAndOpenid(@Param("actId")String actId,@Param("openid")String openid);

	//update-begin--Author:zhangweijian Date:20181114 for：统计参与人数
	/**
	 * @功能：统计参与人数
	 * @param actId
	 * @return
	 */
	public int countByActId(@Param("actId") String actId);
	//update-end--Author:zhangweijian Date:20181114 for：统计参与人数
	
}

