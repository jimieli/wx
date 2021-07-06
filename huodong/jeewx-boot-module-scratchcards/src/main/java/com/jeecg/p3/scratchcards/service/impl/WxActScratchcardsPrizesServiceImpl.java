package com.jeecg.p3.scratchcards.service.impl;

import com.jeecg.p3.scratchcards.dao.WxActScratchcardsPrizesDao;
import com.jeecg.p3.scratchcards.dao.WxActScratchcardsRelationDao;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsPrizes;
import com.jeecg.p3.scratchcards.service.WxActScratchcardsPrizesService;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("wxActScratchcardsPrizesService")
public class WxActScratchcardsPrizesServiceImpl implements WxActScratchcardsPrizesService {
	@Resource
	private WxActScratchcardsPrizesDao wxActScratchcardsPrizesDao;
	@Resource
	private WxActScratchcardsRelationDao wxActScratchcardsRelationDao;

	@Override
	public void doAdd(WxActScratchcardsPrizes wxActScratchcardsPrizes) {
		wxActScratchcardsPrizesDao.insert(wxActScratchcardsPrizes);
	}

	@Override
	public void doEdit(WxActScratchcardsPrizes wxActScratchcardsPrizes) {
		wxActScratchcardsPrizesDao.update(wxActScratchcardsPrizes);
	}

	@Override
	public void doDelete(String id) {
		wxActScratchcardsPrizesDao.delete(id);
	}

	@Override
	public WxActScratchcardsPrizes queryById(String id) {
		WxActScratchcardsPrizes wxActScratchcardsPrizes  = wxActScratchcardsPrizesDao.get(id);
		return wxActScratchcardsPrizes;
	}

	@Override
	public PageList<WxActScratchcardsPrizes> queryPageList(
		PageQuery<WxActScratchcardsPrizes> pageQuery) {
		PageList<WxActScratchcardsPrizes> result = new PageList<WxActScratchcardsPrizes>();
		Integer itemCount = wxActScratchcardsPrizesDao.count(pageQuery);
		PageQueryWrapper<WxActScratchcardsPrizes> wrapper = new PageQueryWrapper<>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		List<WxActScratchcardsPrizes> list = wxActScratchcardsPrizesDao.queryPageList(wrapper);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public List<WxActScratchcardsPrizes> queryPrizes(String jwid,String createBy) {
		return wxActScratchcardsPrizesDao.queryPrizes(jwid,createBy);
	}

	//update-begin--Author:zhangweijian  Date: 20180330 for：根据奖项id在奖项，奖品关系表查询水果机奖品配置表中奖品是否存在
	//根据奖品id在奖项，奖品关系表查询该奖品是否存在
	@Override
	public Boolean validUsed(String id) {
		int n = wxActScratchcardsRelationDao.validUsed(null,id);
		return (n>0);
	}
	//update-end--Author:zhangweijian  Date: 20180330 for：根据奖项id在奖项，奖品关系表查询水果机奖品配置表中奖品是否存在

	@Override
	public List<WxActScratchcardsPrizes> queryPrizesByName(String jwid,
			String createBy, String name) {
		return wxActScratchcardsPrizesDao.queryPrizesByName(jwid, createBy, name);
	}
	
}
