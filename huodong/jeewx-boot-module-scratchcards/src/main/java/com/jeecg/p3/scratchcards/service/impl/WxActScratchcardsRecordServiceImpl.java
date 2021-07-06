package com.jeecg.p3.scratchcards.service.impl;

import com.jeecg.p3.scratchcards.dao.WxActScratchcardsRecordDao;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsRecord;
import com.jeecg.p3.scratchcards.service.WxActScratchcardsRecordService;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("wxActScratchcardsRecordService")
public class WxActScratchcardsRecordServiceImpl implements WxActScratchcardsRecordService {
	@Resource
	private WxActScratchcardsRecordDao wxActScratchcardsRecordDao;

	@Override
	public void doAdd(WxActScratchcardsRecord wxActScratchcardsRecord) {
		wxActScratchcardsRecordDao.insert(wxActScratchcardsRecord);
	}

	@Override
	public void doEdit(WxActScratchcardsRecord wxActScratchcardsRecord) {
		wxActScratchcardsRecordDao.update(wxActScratchcardsRecord);
	}

	@Override
	public void doDelete(String id) {
		wxActScratchcardsRecordDao.delete(id);
	}

	@Override
	public WxActScratchcardsRecord queryById(String id) {
		WxActScratchcardsRecord wxActScratchcardsRecord  = wxActScratchcardsRecordDao.get(id);
		return wxActScratchcardsRecord;
	}

	@Override
	public PageList<WxActScratchcardsRecord> queryPageList(
		PageQuery<WxActScratchcardsRecord> pageQuery) {
		PageList<WxActScratchcardsRecord> result = new PageList<WxActScratchcardsRecord>();
		Integer itemCount = wxActScratchcardsRecordDao.count(pageQuery);
		PageQueryWrapper<WxActScratchcardsRecord> wrapper = new PageQueryWrapper<>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		List<WxActScratchcardsRecord> list = wxActScratchcardsRecordDao.queryPageList(wrapper);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public WxActScratchcardsRecord queryByCode(String code) {
		return wxActScratchcardsRecordDao.queryByCode(code);
	}

	@Override
	public List<WxActScratchcardsRecord> queryList(String actId) {
	
		return wxActScratchcardsRecordDao.queryList(actId);
	}

	@Override
	public List<WxActScratchcardsRecord> queryMyList(String openid,String actId) {
		return wxActScratchcardsRecordDao.queryMyList(openid,actId);
	}

	@Override
	public List<WxActScratchcardsRecord> exportExcel(String actId, String jwid) {
		return wxActScratchcardsRecordDao.exportExcel(jwid,actId);
	}

	@Override
	public List<WxActScratchcardsRecord> exportExcel1(String actId, String jwid) {
		return wxActScratchcardsRecordDao.exportExcel1(jwid,actId);
	}

	@Override
	public Integer countByActId(String actId, String jwid,String openid) {
		return wxActScratchcardsRecordDao.countByActId(actId,jwid,openid);
	}

	@Override
	public List<WxActScratchcardsRecord> queryListByActId(String actId,
			String jwid,String openid, int i, int pageSize) {
		return wxActScratchcardsRecordDao.queryListByActId(actId,jwid,openid,  i,  pageSize);
	}
	
}
