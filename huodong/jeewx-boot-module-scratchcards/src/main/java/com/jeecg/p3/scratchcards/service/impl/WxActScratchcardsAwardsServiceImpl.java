package com.jeecg.p3.scratchcards.service.impl;

import com.jeecg.p3.scratchcards.dao.WxActScratchcardsAwardsDao;
import com.jeecg.p3.scratchcards.dao.WxActScratchcardsRelationDao;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsAwards;
import com.jeecg.p3.scratchcards.service.WxActScratchcardsAwardsService;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("wxActScratchcardsAwardsService")
public class WxActScratchcardsAwardsServiceImpl implements WxActScratchcardsAwardsService {
	@Resource
	private WxActScratchcardsAwardsDao wxActScratchcardsAwardsDao;
	@Resource
	private WxActScratchcardsRelationDao wxActScratchcardsRelationDao;

	@Override
	public void doAdd(WxActScratchcardsAwards wxActScratchcardsAwards) {
		wxActScratchcardsAwardsDao.insert(wxActScratchcardsAwards);
	}

	@Override
	public void doEdit(WxActScratchcardsAwards wxActScratchcardsAwards) {
		wxActScratchcardsAwardsDao.update(wxActScratchcardsAwards);
	}

	@Override
	public void doDelete(String id) {
		wxActScratchcardsAwardsDao.delete(id);
	}

	@Override
	public WxActScratchcardsAwards queryById(String id) {
		WxActScratchcardsAwards wxActScratchcardsAwards  = wxActScratchcardsAwardsDao.get(id);
		return wxActScratchcardsAwards;
	}

	@Override
	public PageList<WxActScratchcardsAwards> queryPageList(
		PageQuery<WxActScratchcardsAwards> pageQuery) {
		PageList<WxActScratchcardsAwards> result = new PageList<WxActScratchcardsAwards>();
		Integer itemCount = wxActScratchcardsAwardsDao.count(pageQuery);
		PageQueryWrapper<WxActScratchcardsAwards> wrapper = new PageQueryWrapper<>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		List<WxActScratchcardsAwards> list = wxActScratchcardsAwardsDao.queryPageList(wrapper);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public List<WxActScratchcardsAwards> queryAwards(String jwid,String createBy) {
		return wxActScratchcardsAwardsDao.queryAwards(jwid,createBy);
	}

	@Override
	public List<WxActScratchcardsAwards> queryAwards(String jwid) {
		return wxActScratchcardsAwardsDao.queryAwards2(jwid);
	}

	//update-begin--Author:zhangweijian  Date: 20180330 for：根据奖项id在奖项，奖品关系表查询水果机奖品配置表中奖品是否存在
	//根据奖项id在奖项，奖品关系表查询水果机奖品配置表中奖项是否存在
	@Override
	public Boolean validUsed(String id) {
		int n = wxActScratchcardsRelationDao.validUsed(id,null);
		return (n>0);
	}
	//update-end--Author:zhangweijian  Date: 20180330 for：根据奖项id在奖项，奖品关系表查询水果机奖品配置表中奖品是否存在

	@Override
	public List<WxActScratchcardsAwards> queryAwardsByName(String jwid,
			String createBy, String awardsName) {
		return wxActScratchcardsAwardsDao.queryAwardsByName(jwid, createBy, awardsName);
	}
}
