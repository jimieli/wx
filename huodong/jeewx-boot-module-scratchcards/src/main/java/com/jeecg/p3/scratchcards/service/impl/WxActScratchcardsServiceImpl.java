package com.jeecg.p3.scratchcards.service.impl;

import com.jeecg.p3.baseApi.service.BaseApiActTxtService;
import com.jeecg.p3.scratchcards.dao.WxActScratchcardsAwardsDao;
import com.jeecg.p3.scratchcards.dao.WxActScratchcardsDao;
import com.jeecg.p3.scratchcards.dao.WxActScratchcardsPrizesDao;
import com.jeecg.p3.scratchcards.dao.WxActScratchcardsRelationDao;
import com.jeecg.p3.scratchcards.def.ScratchcardsProperties;
import com.jeecg.p3.scratchcards.entity.WxActScratchcards;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsAwards;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsPrizes;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsRelation;
import com.jeecg.p3.scratchcards.service.WxActScratchcardsService;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.p3.core.util.plugin.ContextHolderUtils;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import org.jeecgframework.p3.core.utils.persistence.OptimisticLockingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("wxActScratchcardsService")
public class WxActScratchcardsServiceImpl implements WxActScratchcardsService {
	@Resource
	private WxActScratchcardsDao wxActScratchcardsDao;
	@Autowired
	private BaseApiActTxtService baseApiActTxtService;
	@Autowired
	private WxActScratchcardsRelationDao wxActScratchcardsRelationDao;
	@Autowired
	private WxActScratchcardsAwardsDao wxActScratchcardsAwardsDao;
	@Autowired
	private WxActScratchcardsPrizesDao wxActScratchcardsPrizesDao;
	
	@Override
	public void doAdd(WxActScratchcards wxActScratchcards) {
		wxActScratchcards.setProjectCode("scratchcards");
		wxActScratchcardsDao.insert(wxActScratchcards);
		List<WxActScratchcardsRelation> awardsList = wxActScratchcards.getAwarsList();
		if (awardsList != null) {
			for (WxActScratchcardsRelation wxActScratchcardsRelation : awardsList) {
				//update-begin--Author:zhangweijian  Date: 20180329 for：判断奖品，奖项是否存在，如果不存在，在增加
				if(StringUtils.isEmpty(wxActScratchcardsRelation.getAwardId())){
					wxActScratchcardsRelation.setAwardId(saveAwards(wxActScratchcardsRelation.getAwardsName()));
				}else{
					WxActScratchcardsAwards wxActScratchcardsAward=wxActScratchcardsAwardsDao.get(wxActScratchcardsRelation.getAwardId());
					//判断awardId和awardName是否是匹配的，不匹配则增加
					if(!wxActScratchcardsAward.getAwardsName().equals(wxActScratchcardsRelation.getAwardsName())){
						wxActScratchcardsRelation.setAwardId(saveAwards(wxActScratchcardsRelation.getAwardsName()));
					}
				}
				
				if(StringUtils.isEmpty(wxActScratchcardsRelation.getPrizeId())){
					wxActScratchcardsRelation.setPrizeId(savePrizes(wxActScratchcardsRelation.getName(),wxActScratchcardsRelation.getAwardImg()));
				}else{
					//prizeId和prizeName是否是匹配的，不匹配则增加
					WxActScratchcardsPrizes wxActScratchcardsPrize= wxActScratchcardsPrizesDao.get(wxActScratchcardsRelation.getPrizeId());
					if(!wxActScratchcardsPrize.getName().equals(wxActScratchcardsRelation.getName())){
						wxActScratchcardsRelation.setPrizeId(savePrizes(wxActScratchcardsRelation.getName(),wxActScratchcardsRelation.getAwardImg()));
					}
					if(StringUtils.isNotEmpty(wxActScratchcardsRelation.getAwardImg()) && StringUtils.isNotEmpty(wxActScratchcardsPrize.getImg())){
						if(wxActScratchcardsPrize.getName().equals(wxActScratchcardsRelation.getName())&& !wxActScratchcardsPrize.getImg().equals(wxActScratchcardsRelation.getAwardImg())){
							wxActScratchcardsRelation.setPrizeId(savePrizes(wxActScratchcardsRelation.getName(),wxActScratchcardsRelation.getAwardImg()));
						}
					}
				}
				//update-end--Author:zhangweijian  Date: 20180329 for：判断奖品，奖项是否存在，如果不存在，在增加
				wxActScratchcardsRelation.setActId(wxActScratchcards.getId());
				if(wxActScratchcardsRelation.getAmount()==null){
					wxActScratchcardsRelation.setAmount(0);
				}
				wxActScratchcardsRelation.setRemainNum(wxActScratchcardsRelation.getAmount());
				//update-begin-zhangweijian-----Date:20180830----for:概率转换
				if (wxActScratchcardsRelation.getProbability() == null) {
					wxActScratchcardsRelation.setProbability(new BigDecimal("0"));
				}else{
					BigDecimal precent = new BigDecimal("100");
					wxActScratchcardsRelation.setProbability(wxActScratchcardsRelation.getProbability().divide(precent));
				}
				//update-end-zhangweijian-----Date:20180830----for:概率转换
				if(wxActScratchcardsRelation.getAmount()==null){
					wxActScratchcardsRelation.setAmount(0);
				}
				wxActScratchcardsRelation.setRemainNum(wxActScratchcardsRelation.getAmount());
			}
			for (WxActScratchcardsRelation wxActScratchcardsRelation : awardsList) {
				wxActScratchcardsRelationDao.insert(wxActScratchcardsRelation);
			}
		}

		baseApiActTxtService.copyActText(ScratchcardsProperties.TXT_ACTID_KEY,wxActScratchcards.getId());

	}

	//update-begin--Author:zhangweijian  Date: 20180329 for：判断奖品，奖项是否存在，如果不存在，在增加
	private String savePrizes(String name,String prizeImg) {
		WxActScratchcardsPrizes wxActScratchcardsPrizes =new WxActScratchcardsPrizes();
		wxActScratchcardsPrizes.setName(name);
		String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
		String createBy = "";
		String defaultJwid = ScratchcardsProperties.defaultJwid;
		if(defaultJwid.equals(jwid)){
			createBy = ContextHolderUtils.getSession().getAttribute("system_userid").toString();
		}
		//判断数据库是否存在奖品名称
		List<WxActScratchcardsPrizes> queryPrizesByName =wxActScratchcardsPrizesDao.queryPrizesByName(jwid, createBy, name);
		if(queryPrizesByName.size()>0){
			queryPrizesByName.get(0).setImg(prizeImg);
			wxActScratchcardsPrizesDao.update(queryPrizesByName.get(0));
			return queryPrizesByName.get(0).getId();
		}
		
		wxActScratchcardsPrizes.setCreateBy(createBy);
		wxActScratchcardsPrizes.setJwid(jwid);
		wxActScratchcardsPrizes.setName(name);
		wxActScratchcardsPrizes.setImg(prizeImg);
		wxActScratchcardsPrizesDao.insert(wxActScratchcardsPrizes);
		return wxActScratchcardsPrizes.getId();
	}

	private String saveAwards(String awardsName) {
		WxActScratchcardsAwards wxActScratchcardsAwards=new WxActScratchcardsAwards();
		wxActScratchcardsAwards.setAwardsName(awardsName);
		String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
		String defaultJwid = ScratchcardsProperties.defaultJwid;
		String createBy = "";
		//如果是H5活动汇
		if(defaultJwid.equals(jwid)){
			createBy = ContextHolderUtils.getSession().getAttribute("system_userid").toString();
		}
		List<WxActScratchcardsAwards> queryAwardsByName = wxActScratchcardsAwardsDao.queryAwardsByName(jwid, createBy, awardsName);
		if(queryAwardsByName.size()>0){
			return queryAwardsByName.get(0).getId();
		}
		wxActScratchcardsAwards.setCreateBy(createBy);
		wxActScratchcardsAwards.setJwid(jwid);
		wxActScratchcardsAwardsDao.insert(wxActScratchcardsAwards);
		return wxActScratchcardsAwards.getId();
	}
	//update-end--Author:zhangweijian  Date: 20180329 for：判断奖品，奖项是否存在，如果不存在，在增加
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String doEdit(WxActScratchcards wxActScratchcards) {
		wxActScratchcardsDao.update(wxActScratchcards);
		List<WxActScratchcardsRelation> newAwardsList = wxActScratchcards
				.getAwarsList();// 新的明细配置集合
		List<String> ids = new ArrayList<String>();
		String msg = "";
		if (newAwardsList != null) {
			for (WxActScratchcardsRelation relation : newAwardsList) {
				//update-begin--Author:zhangweijian  Date: 20180330 for：判断奖品，奖项是否存在，如果不存在，在增加
				if(StringUtils.isEmpty(relation.getAwardId())){
					relation.setAwardId(saveAwards(relation.getAwardsName()));
				}else{
					WxActScratchcardsAwards wxActScratchcardsAward = wxActScratchcardsAwardsDao.get(relation.getAwardId());
					//判断awardId和awardName是否是匹配的，不匹配则增加
					if(!wxActScratchcardsAward.getAwardsName().equals(relation.getAwardsName())){
						relation.setAwardId(saveAwards(relation.getAwardsName()));
					}
				}
				if(StringUtils.isEmpty(relation.getPrizeId())){
					relation.setPrizeId(savePrizes(relation.getName(),relation.getAwardImg()));
				}else{
					WxActScratchcardsPrizes wxActScratchcardsPrize = wxActScratchcardsPrizesDao.get(relation.getPrizeId());
					//prizeId和prizeName是否是匹配的，不匹配则增加
					if(!wxActScratchcardsPrize.getName().equals(relation.getName())){
						relation.setPrizeId(savePrizes(relation.getName(),relation.getAwardImg()));
					}
					if(StringUtils.isNotEmpty(relation.getAwardImg()) && StringUtils.isNotEmpty(wxActScratchcardsPrize.getImg())){
						if(wxActScratchcardsPrize.getName().equals(relation.getName())&& !wxActScratchcardsPrize.getImg().equals(relation.getAwardImg())){
							relation.setPrizeId(savePrizes(relation.getName(),relation.getAwardImg()));
						}
					}
				}
				//update-end--Author:zhangweijian  Date: 20180330 for：判断奖品，奖项是否存在，如果不存在，在增加
				String id = relation.getId();
				if (StringUtils.isNotEmpty(id)) {
					WxActScratchcardsRelation wxActScratchcardsRelation = wxActScratchcardsRelationDao
							.queryId(id);
					//update-begin--Author:sunkai  Date: 20180911 for：修改奖品剩余数量
					Integer outNum = wxActScratchcardsRelation.getAmount()-wxActScratchcardsRelation.getRemainNum();
					if(relation.getAmount()<outNum){
						WxActScratchcardsAwards awards = wxActScratchcardsAwardsDao.get(relation.getAwardId());
						msg = msg + awards.getAwardsName()+"奖品数量不得小于已抽出数量"+outNum+";</br>";
					}else{
						Integer newRemainNum = relation.getAmount()-wxActScratchcardsRelation.getAmount();
						int row = wxActScratchcardsRelationDao.updateAmountAndRemaiNum(id, newRemainNum);
						if (row == 0) {
							throw new OptimisticLockingException("乐观锁异常");
						}
					}
					//update-end--Author:sunkai  Date: 20180911 for：修改奖品剩余数量
					
					if (wxActScratchcardsRelation.getProbability() == null) {
						wxActScratchcardsRelation.setProbability(new BigDecimal("0"));
					}else{
						BigDecimal precent = new BigDecimal("100");
						wxActScratchcardsRelation.setProbability(wxActScratchcardsRelation.getProbability().divide(precent));
					}
					relation.setAmount(null);
					relation.setRemainNum(null);
				}
				if (StringUtils.isNotEmpty(relation.getId())) {
					ids.add(relation.getId());
				}
			}
			wxActScratchcardsRelationDao.bactchDeleteOldAwards(ids,
					wxActScratchcards.getId());// 批量删除不在新的明细配置集合的数据
			for (WxActScratchcardsRelation wxActScratchcardsRelation : newAwardsList) {
				if (wxActScratchcardsRelation.getProbability() == null) {
					wxActScratchcardsRelation.setProbability(new BigDecimal("0"));
				}else{
					BigDecimal precent = new BigDecimal("100");
					wxActScratchcardsRelation.setProbability(wxActScratchcardsRelation.getProbability().divide(precent));
				}
				if (StringUtils.isEmpty(wxActScratchcardsRelation.getId())) {
					wxActScratchcardsRelation.setActId(wxActScratchcards
							.getId());
					if(wxActScratchcardsRelation.getAmount()==null){
						wxActScratchcardsRelation.setAmount(0);
					}
					wxActScratchcardsRelation.setRemainNum(wxActScratchcardsRelation.getAmount());
					
					wxActScratchcardsRelationDao.insert(wxActScratchcardsRelation);
				} else {
					wxActScratchcardsRelationDao
							.update(wxActScratchcardsRelation);
				}
			}
		} else {
			wxActScratchcardsRelationDao.bactchDeleteOldAwards(ids,
					wxActScratchcards.getId());// 批量删除不在新的明细配置集合的数据
		}
		return msg;
	}

	@Override
	public void doDelete(String id) {
		wxActScratchcardsDao.delete(id);
		wxActScratchcardsRelationDao.batchDeleteByActId(id);
		//baseApiActTxtService.batchDeleteByActCode(id);
	}

	@Override
	public WxActScratchcards queryById(String id) {
		WxActScratchcards wxActScratchcards = wxActScratchcardsDao.get(id);
		return wxActScratchcards;
	}

	@Override
	public PageList<WxActScratchcards> queryPageList(
			PageQuery<WxActScratchcards> pageQuery) {
		PageList<WxActScratchcards> result = new PageList<WxActScratchcards>();
		Integer itemCount = wxActScratchcardsDao.count(pageQuery);
		PageQueryWrapper<WxActScratchcards> wrapper = new PageQueryWrapper<>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		List<WxActScratchcards> list = wxActScratchcardsDao.queryPageList(
				wrapper);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(),
				itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public void editShortUrl(String id, String shortUrl) {
		wxActScratchcardsDao.editShortUrl(id,shortUrl);
	}

}
