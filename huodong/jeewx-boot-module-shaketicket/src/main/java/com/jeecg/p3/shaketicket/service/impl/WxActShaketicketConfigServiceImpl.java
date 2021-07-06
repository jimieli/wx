package com.jeecg.p3.shaketicket.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.shaketicket.service.WxActShaketicketConfigService;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketConfig;
import com.jeecg.p3.shaketicket.dao.WxActShaketicketConfigDao;

@Service("wxActShaketicketConfigService")
public class WxActShaketicketConfigServiceImpl implements WxActShaketicketConfigService {
	@Resource
	private WxActShaketicketConfigDao wxActShaketicketConfigDao;

	@Override
	public void doAdd(WxActShaketicketConfig wxActShaketicketConfig) {
		wxActShaketicketConfigDao.insert(wxActShaketicketConfig);
	}

	@Override
	public void doEdit(WxActShaketicketConfig wxActShaketicketConfig) {
		wxActShaketicketConfigDao.update(wxActShaketicketConfig);
	}

	@Override
	public void doDelete(String id) {
		wxActShaketicketConfigDao.delete(id);
	}

	@Override
	public WxActShaketicketConfig queryById(String id) {
		WxActShaketicketConfig wxActShaketicketConfig  = wxActShaketicketConfigDao.get(id);
		return wxActShaketicketConfig;
	}

	@Override
	public PageList<WxActShaketicketConfig> queryPageList(
		PageQuery<WxActShaketicketConfig> pageQuery) {
		PageList<WxActShaketicketConfig> result = new PageList<WxActShaketicketConfig>();
		Integer itemCount = wxActShaketicketConfigDao.count(pageQuery);
		PageQueryWrapper<WxActShaketicketConfig> wrapper = new PageQueryWrapper<WxActShaketicketConfig>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		List<WxActShaketicketConfig> list = wxActShaketicketConfigDao.queryPageList(wrapper);
		//update-begin--Author:zhangweijian Date:20181021 for：概率转换成100%
		if(list.size()>0){
			 for(int i=0;i<list.size();i++){
				 //update-begin--Author:zhangweijian Date:20181024 for：去掉多余的0
				 BigDecimal precent = new BigDecimal("0.01");
				 list.get(i).setProbabilitys(list.get(i).getProbability().divide(precent).stripTrailingZeros().toPlainString());
				//update-end--Author:zhangweijian Date:20181024 for：去掉多余的0
			 }
		}
		//update-end--Author:zhangweijian Date:20181021 for：概率转换成100%
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public List<WxActShaketicketConfig> queryByActId(String actId) {
		// TODO Auto-generated method stub
		return wxActShaketicketConfigDao.queryByActId(actId);
	}
	
}
