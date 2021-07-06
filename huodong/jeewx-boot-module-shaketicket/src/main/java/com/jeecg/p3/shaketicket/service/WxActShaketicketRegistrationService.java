package com.jeecg.p3.shaketicket.service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketRegistration;

/**
 * 描述：</b>摇一摇注册表<br>
 * @author：junfeng.zhou
 * @since：2018年10月12日 15时26分06秒 星期五 
 * @version:1.0
 */
public interface WxActShaketicketRegistrationService {
	
	
	public void doAdd(WxActShaketicketRegistration wxActShaketicketRegistration);
	
	public void doEdit(WxActShaketicketRegistration wxActShaketicketRegistration);
	
	public void doDelete(String id);
	
	public WxActShaketicketRegistration queryById(String id);
	
	public PageList<WxActShaketicketRegistration> queryPageList(PageQuery<WxActShaketicketRegistration> pageQuery);

	/**
	 * @功能：根据活动id和openid查询用户信息
	 * @param actId
	 * @param openid
	 * @return
	 */
	public WxActShaketicketRegistration queryByActIdAndOpenid(String actId,String openid);

	//update-begin--Author:zhangweijian Date:20181114 for：统计参与人数
	/**
	 * @功能：统计参与人数
	 * @param actId
	 * @return
	 */
	public int countByActId(String actId);
	//update-end--Author:zhangweijian Date:20181114 for：统计参与人数
}

