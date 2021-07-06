package com.jeecg.p3.shaketicket.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.shaketicket.service.WxActShaketicketShareRecordService;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketShareRecord;
import com.jeecg.p3.shaketicket.dao.WxActShaketicketShareRecordDao;

/**
 * 描述：</b>摇一摇分享记录表<br>
 * @author：junfeng.zhou
 * @since：2018年10月17日 10时27分39秒 星期三 
 * @version:1.0
 */
@Service("wxActShaketicketShareRecordService")
public class WxActShaketicketShareRecordServiceImpl implements WxActShaketicketShareRecordService {
	@Resource
	private WxActShaketicketShareRecordDao wxActShaketicketShareRecordDao;

	@Override
	public void doAdd(WxActShaketicketShareRecord wxActShaketicketShareRecord) {
		wxActShaketicketShareRecordDao.insert(wxActShaketicketShareRecord);
	}

	@Override
	public void doEdit(WxActShaketicketShareRecord wxActShaketicketShareRecord) {
		wxActShaketicketShareRecordDao.update(wxActShaketicketShareRecord);
	}

	@Override
	public void doDelete(String id) {
		wxActShaketicketShareRecordDao.delete(id);
	}

	@Override
	public WxActShaketicketShareRecord queryById(String id) {
		WxActShaketicketShareRecord wxActShaketicketShareRecord  = wxActShaketicketShareRecordDao.get(id);
		return wxActShaketicketShareRecord;
	}

	@Override
	public PageList<WxActShaketicketShareRecord> queryPageList(
		PageQuery<WxActShaketicketShareRecord> pageQuery) {
		PageList<WxActShaketicketShareRecord> result = new PageList<WxActShaketicketShareRecord>();
		Integer itemCount = wxActShaketicketShareRecordDao.count(pageQuery);
		PageQueryWrapper<WxActShaketicketShareRecord> wrapper = new PageQueryWrapper<WxActShaketicketShareRecord>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		List<WxActShaketicketShareRecord> list = wxActShaketicketShareRecordDao.queryPageList(wrapper);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	/**
	 * @功能：根据活动id和openid查询分享记录
	 */
	@Override
	public List<WxActShaketicketShareRecord> queryByActIdAndOpenid(String actId, String openid, String shareDate) {
		return wxActShaketicketShareRecordDao.queryByActIdAndOpenid(actId,openid,shareDate);
	}
	
}
