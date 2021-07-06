package com.jeecg.p3.scratchcards.service.impl;

import com.jeecg.p3.scratchcards.dao.WxActScratchcardsShareRecordDao;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsShareRecord;
import com.jeecg.p3.scratchcards.service.WxActScratchcardsShareRecordService;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述：</b>分享记录表<br>
 * @author：sunkai
 * @since：2018年10月17日 10时34分17秒 星期三 
 * @version:1.0
 */
@Service("wxActScratchcardsShareRecordService")
public class WxActScratchcardsShareRecordServiceImpl implements WxActScratchcardsShareRecordService {
	@Resource
	private WxActScratchcardsShareRecordDao wxActScratchcardsShareRecordDao;

	@Override
	public void doAdd(WxActScratchcardsShareRecord wxActScratchcardsShareRecord) {
		wxActScratchcardsShareRecordDao.insert(wxActScratchcardsShareRecord);
	}

	@Override
	public void doEdit(WxActScratchcardsShareRecord wxActScratchcardsShareRecord) {
		wxActScratchcardsShareRecordDao.update(wxActScratchcardsShareRecord);
	}

	@Override
	public void doDelete(String id) {
		wxActScratchcardsShareRecordDao.delete(id);
	}

	@Override
	public WxActScratchcardsShareRecord queryById(String id) {
		WxActScratchcardsShareRecord wxActScratchcardsShareRecord  = wxActScratchcardsShareRecordDao.get(id);
		return wxActScratchcardsShareRecord;
	}

	@Override
	public PageList<WxActScratchcardsShareRecord> queryPageList(
		PageQuery<WxActScratchcardsShareRecord> pageQuery) {
		PageList<WxActScratchcardsShareRecord> result = new PageList<WxActScratchcardsShareRecord>();
		Integer itemCount = wxActScratchcardsShareRecordDao.count(pageQuery);
		PageQueryWrapper<WxActScratchcardsShareRecord> wrapper = new PageQueryWrapper<>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		List<WxActScratchcardsShareRecord> list = wxActScratchcardsShareRecordDao.queryPageList(wrapper);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public List<WxActScratchcardsShareRecord> queryPageList1(
			PageQuery<WxActScratchcardsShareRecord> pageQuery) {
		Integer itemCount = wxActScratchcardsShareRecordDao.count(pageQuery);
		PageQueryWrapper<WxActScratchcardsShareRecord> wrapper = new PageQueryWrapper<>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return wxActScratchcardsShareRecordDao.queryPageList(wrapper);
	}
	
}
