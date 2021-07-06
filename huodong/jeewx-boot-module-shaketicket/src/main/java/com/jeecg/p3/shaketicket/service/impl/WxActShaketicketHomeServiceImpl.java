package com.jeecg.p3.shaketicket.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.p3.core.util.plugin.ContextHolderUtils;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecg.p3.baseApi.service.BaseApiActTxtService;
import com.jeecg.p3.shaketicket.dao.WxActShaketicketAwardDao;
import com.jeecg.p3.shaketicket.dao.WxActShaketicketConfigDao;
import com.jeecg.p3.shaketicket.dao.WxActShaketicketHomeDao;
import com.jeecg.p3.shaketicket.def.SystemShakProperties;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketAward;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketConfig;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketHome;
import com.jeecg.p3.shaketicket.exception.ShaketicketHomeException;
import com.jeecg.p3.shaketicket.exception.ShaketicketHomeExceptionEnum;
import com.jeecg.p3.shaketicket.service.WxActShaketicketHomeService;

@Service("wxActShaketicketHomeService")
public class WxActShaketicketHomeServiceImpl implements WxActShaketicketHomeService {
	@Resource
	private WxActShaketicketHomeDao wxActShaketicketHomeDao;
	@Resource
	private WxActShaketicketConfigDao wxActShaketicketConfigDao;
	@Autowired
	private BaseApiActTxtService baseApiActTxtService;
	@Autowired
	private WxActShaketicketAwardDao wxActShaketicketAwardDao;
	private static String oldActCode = SystemShakProperties.oldActCode;
	private static String defaultJwid = SystemShakProperties.defaultJwid;
	
	//update-begin--Author:zhangweijian  Date: 20180920 for：后台新增,编辑逻辑修改
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void doAdd(WxActShaketicketHome wxActShaketicketHome) {
		//添加主表数据
		wxActShaketicketHome.setProjectCode("shaketicket");
		wxActShaketicketHomeDao.insert(wxActShaketicketHome);
		//添加奖品配置表数据
		List<WxActShaketicketConfig> awardsList= wxActShaketicketHome.getAwarsList();
		if(awardsList!=null){
			for (WxActShaketicketConfig actShaketicketConfig : awardsList) {
				//1.新增奖品信息
				actShaketicketConfig.setAwardId(saveAwards(actShaketicketConfig.getAwardsName(),actShaketicketConfig.getAwardImg()));
				actShaketicketConfig.setActId(wxActShaketicketHome.getId());
				actShaketicketConfig.setActive("1");
				if(actShaketicketConfig.getProbability()==null){
					//update-begin--Author:zhangweijian Date:20181024 for：概率字段类型改为BigDecimal
					actShaketicketConfig.setProbability(new BigDecimal("0"));
				}else{
					BigDecimal precent = new BigDecimal("100");
					actShaketicketConfig.setProbability(actShaketicketConfig.getProbability().divide(precent));
					//update-end--Author:zhangweijian Date:20181024 for：概率字段类型改为BigDecimal
				}
			}
			for (WxActShaketicketConfig wxActShaketicketConfig : awardsList) {
				wxActShaketicketConfigDao.insert(wxActShaketicketConfig);
			}
		}
		baseApiActTxtService.copyActText(oldActCode, wxActShaketicketHome.getId());
	}

	/**
	 * @功能：保存奖项
	 * @param awardsName
	 * @param awardImg
	 * @return
	 */
	private String saveAwards(String awardsName, String awardImg) {
		WxActShaketicketAward wxActShaketicketAward=new WxActShaketicketAward();
		wxActShaketicketAward.setAwardsName(awardsName);
		String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
		String createBy = ContextHolderUtils.getSession().getAttribute("system_userid").toString();
		//如果是H5活动汇
		if(defaultJwid.equals(jwid)){
			List<WxActShaketicketAward> queryAwardsByName = wxActShaketicketAwardDao.queryAwardsByName(jwid, createBy, awardsName);
			if(queryAwardsByName.size()>0){
				queryAwardsByName.get(0).setImg(awardImg);
				wxActShaketicketAwardDao.update(queryAwardsByName.get(0));
				return queryAwardsByName.get(0).getId();
			}
		}else{
			List<WxActShaketicketAward> queryAwardsByName = wxActShaketicketAwardDao.queryAwardsByName(jwid, "", awardsName);
			if(queryAwardsByName.size()>0){
				queryAwardsByName.get(0).setImg(awardImg);
				wxActShaketicketAwardDao.update(queryAwardsByName.get(0));
				return queryAwardsByName.get(0).getId();
			}
		}
		wxActShaketicketAward.setCreateBy(createBy);
		wxActShaketicketAward.setJwid(jwid);
		wxActShaketicketAward.setImg(awardImg);
		wxActShaketicketAwardDao.insert(wxActShaketicketAward);
		return wxActShaketicketAward.getId();
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void doEdit(WxActShaketicketHome wxActShaketicketHome) {
		//更新主表数据
		wxActShaketicketHomeDao.update(wxActShaketicketHome);
		//更新配置表数据
		List<WxActShaketicketConfig> newAwardsList= wxActShaketicketHome.getAwarsList();
		List<String> ids=new ArrayList<String>();
		if(newAwardsList!=null){
			for (WxActShaketicketConfig relation : newAwardsList) {
				//1.新增奖品信息
				relation.setAwardId(saveAwards(relation.getAwardsName(),relation.getAwardImg()));
				if(StringUtils.isNotEmpty(relation.getId())){				
					ids.add(relation.getId());
				}
			}
			wxActShaketicketConfigDao.bactchDeleteOldAwards(ids,wxActShaketicketHome.getId());//批量删除不在新的明细配置集合的数据
			for (WxActShaketicketConfig actShaketicketConfig : newAwardsList) {
				if(actShaketicketConfig.getProbability()==null){
					//update-begin--Author:zhangweijian Date:20181024 for：概率字段类型改为BigDecimal
					actShaketicketConfig.setProbability(new BigDecimal("0"));
				}else{
					BigDecimal precent = new BigDecimal("100");
					actShaketicketConfig.setProbability(actShaketicketConfig.getProbability().divide(precent));
					//update-end--Author:zhangweijian Date:20181024 for：概率字段类型改为BigDecimal
				}
				if(StringUtils.isEmpty(actShaketicketConfig.getId())){
					actShaketicketConfig.setActId(wxActShaketicketHome.getId());
					actShaketicketConfig.setActive("1");
					wxActShaketicketConfigDao.insert(actShaketicketConfig);
				}else{
					WxActShaketicketConfig wxActShaketicketConfig = wxActShaketicketConfigDao.get(actShaketicketConfig.getId());
					if(wxActShaketicketConfig!=null&&wxActShaketicketConfig.getAmount()!=null&&actShaketicketConfig.getAmount()!=null){
						Integer num=actShaketicketConfig.getAmount()-wxActShaketicketConfig.getAmount();
						if(num!=0){
							//更新数据库
							if(wxActShaketicketConfig.getRemainNum()!=null){
								if(wxActShaketicketConfig.getRemainNum()>=0-num){
									wxActShaketicketConfigDao.updateNum(actShaketicketConfig.getId(), num);
								}else{
									throw new ShaketicketHomeException(ShaketicketHomeExceptionEnum.ACT_BARGAIN_DATA_ERROR,"数量更新失败");
								}
							}
						}
					}
					actShaketicketConfig.setAmount(null);
					wxActShaketicketConfigDao.update(actShaketicketConfig);
				}
			}
		}else{
			wxActShaketicketConfigDao.bactchDeleteOldAwards(ids,wxActShaketicketHome.getId());//批量删除不在新的明细配置集合的数据
		}
	}
	//update-end--Author:zhangweijian  Date: 20180920 for：后台新增,编辑逻辑修改
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void doDelete(String id) {
		wxActShaketicketHomeDao.delete(id);
		wxActShaketicketConfigDao.batchDeleteByActId(id);//同步活动明细配置
		baseApiActTxtService.batchDeleteByActCode(id);//同步删除系统文本
	}

	@Override
	public WxActShaketicketHome queryById(String id) {
		WxActShaketicketHome wxActShaketicketHome  = wxActShaketicketHomeDao.get(id);
		return wxActShaketicketHome;
	}

	@Override
	public PageList<WxActShaketicketHome> queryPageList(
		PageQuery<WxActShaketicketHome> pageQuery) {
		PageList<WxActShaketicketHome> result = new PageList<WxActShaketicketHome>();
		Integer itemCount = wxActShaketicketHomeDao.count(pageQuery);
		PageQueryWrapper<WxActShaketicketHome> wrapper = new PageQueryWrapper<WxActShaketicketHome>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		List<WxActShaketicketHome> list = wxActShaketicketHomeDao.queryPageList(wrapper);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public void doedit(WxActShaketicketHome wxActShaketicketHome) {
		wxActShaketicketHomeDao.update(wxActShaketicketHome);
		
	}
	
}
