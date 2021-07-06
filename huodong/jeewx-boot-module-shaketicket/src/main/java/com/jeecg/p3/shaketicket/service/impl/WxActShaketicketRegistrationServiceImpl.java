package com.jeecg.p3.shaketicket.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.shaketicket.service.WxActShaketicketRegistrationService;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketRegistration;
import com.jeecg.p3.shaketicket.dao.WxActShaketicketRegistrationDao;

/**
 * 描述：</b>摇一摇注册表<br>
 * @author：junfeng.zhou
 * @since：2018年10月12日 15时26分06秒 星期五 
 * @version:1.0
 */
@Service("wxActShaketicketRegistrationService")
public class WxActShaketicketRegistrationServiceImpl implements WxActShaketicketRegistrationService {
	@Resource
	private WxActShaketicketRegistrationDao wxActShaketicketRegistrationDao;

	@Override
	public void doAdd(WxActShaketicketRegistration wxActShaketicketRegistration) {
		wxActShaketicketRegistrationDao.insert(wxActShaketicketRegistration);
	}

	@Override
	public void doEdit(WxActShaketicketRegistration wxActShaketicketRegistration) {
		wxActShaketicketRegistrationDao.update(wxActShaketicketRegistration);
	}

	@Override
	public void doDelete(String id) {
		wxActShaketicketRegistrationDao.delete(id);
	}

	@Override
	public WxActShaketicketRegistration queryById(String id) {
		WxActShaketicketRegistration wxActShaketicketRegistration  = wxActShaketicketRegistrationDao.get(id);
		return wxActShaketicketRegistration;
	}

	@Override
	public PageList<WxActShaketicketRegistration> queryPageList(
		PageQuery<WxActShaketicketRegistration> pageQuery) {
		PageList<WxActShaketicketRegistration> result = new PageList<WxActShaketicketRegistration>();
		Integer itemCount = wxActShaketicketRegistrationDao.count(pageQuery);
		PageQueryWrapper<WxActShaketicketRegistration> wrapper = new PageQueryWrapper<WxActShaketicketRegistration>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		List<WxActShaketicketRegistration> list = wxActShaketicketRegistrationDao.queryPageList(wrapper);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	/**
	 * @功能：根据活动id和openid查询用户信息
	 */
	@Override
	public WxActShaketicketRegistration queryByActIdAndOpenid(String actId,String openid) {
		return wxActShaketicketRegistrationDao.queryByActIdAndOpenid(actId,openid);
	}

	//update-begin--Author:zhangweijian Date:20181114 for：统计参与人数
	/**
	 * @功能：统计参与人数
	 */
	@Override
	public int countByActId(String actId) {
		return wxActShaketicketRegistrationDao.countByActId(actId);
	}
	//update-end--Author:zhangweijian Date:20181114 for：统计参与人数
	
}
