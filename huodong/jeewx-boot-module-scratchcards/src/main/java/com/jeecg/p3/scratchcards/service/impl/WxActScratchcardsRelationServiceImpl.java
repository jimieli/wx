package com.jeecg.p3.scratchcards.service.impl;

import com.jeecg.p3.scratchcards.dao.WxActScratchcardsRelationDao;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsRelation;
import com.jeecg.p3.scratchcards.service.WxActScratchcardsRelationService;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service("wxActScratchcardsRelationService")
public class WxActScratchcardsRelationServiceImpl implements WxActScratchcardsRelationService {
	@Resource
	private WxActScratchcardsRelationDao wxActScratchcardsRelationDao;

	@Override
	public void doAdd(WxActScratchcardsRelation wxActScratchcardsRelation) {
		wxActScratchcardsRelationDao.insert(wxActScratchcardsRelation);
	}

	@Override
	public void doEdit(WxActScratchcardsRelation wxActScratchcardsRelation) {
		wxActScratchcardsRelationDao.update(wxActScratchcardsRelation);
	}

	@Override
	public void doDelete(String id) {
		wxActScratchcardsRelationDao.delete(id);
	}

	@Override
	public WxActScratchcardsRelation queryById(String id) {
		WxActScratchcardsRelation wxActScratchcardsRelation  = wxActScratchcardsRelationDao.get(id);
		return wxActScratchcardsRelation;
	}

	@Override
	public PageList<WxActScratchcardsRelation> queryPageList(
		PageQuery<WxActScratchcardsRelation> pageQuery) {
		PageList<WxActScratchcardsRelation> result = new PageList<WxActScratchcardsRelation>();
		Integer itemCount = wxActScratchcardsRelationDao.count(pageQuery);
		PageQueryWrapper<WxActScratchcardsRelation> wrapper = new PageQueryWrapper<>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		List<WxActScratchcardsRelation> list = wxActScratchcardsRelationDao.queryPageList(wrapper);
		for(WxActScratchcardsRelation w:list){
			BigDecimal precent = new BigDecimal("0.01");
			w.setProbabilitys(w.getProbability().divide(precent).stripTrailingZeros().toPlainString());
		}
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public List<WxActScratchcardsRelation> queryByActId(String actId) {
		return wxActScratchcardsRelationDao.queryByActId(actId);
	}

	@Override
	public List<WxActScratchcardsRelation> queryPrizeAndAward(String actId) {
		return  wxActScratchcardsRelationDao.queryPrizeAndAward(actId);
	}

	@Override
	public List<WxActScratchcardsRelation> queryByActIdAndJwid(String actId,
			String jwid) {
		return wxActScratchcardsRelationDao.queryByActIdAndJwid(actId, jwid);
	}

}
