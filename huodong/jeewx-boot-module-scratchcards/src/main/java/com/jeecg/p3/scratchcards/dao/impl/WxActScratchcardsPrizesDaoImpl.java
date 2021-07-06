//package com.jeecg.p3.scratchcards.dao.impl;
//
//import java.util.List;
//import java.util.Map;
//
//import org.jeecgframework.p3.core.utils.common.PageQuery;
//import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
//import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
//import org.springframework.stereotype.Repository;
//
//import com.google.common.collect.Maps;
//import com.jeecg.p3.scratchcards.dao.WxActScratchcardsPrizesDao;
//import com.jeecg.p3.scratchcards.entity.WxActScratchcardsPrizes;
//
///**
// * 描述：</b>WxActScratchcardsPrizesDaoImpl<br>
// * @author：junfeng.zhou
// * @since：2016年07月13日 18时42分24秒 星期三
// * @version:1.0
// */
//@Repository("wxActScratchcardsPrizesDao")
//public class WxActScratchcardsPrizesDaoImpl extends GenericDaoDefault<WxActScratchcardsPrizes> implements WxActScratchcardsPrizesDao{
//
//	@Override
//	public Integer count(PageQuery<WxActScratchcardsPrizes> pageQuery) {
//		return (Integer) super.queryOne("count",pageQuery);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActScratchcardsPrizes> queryPageList(
//			PageQuery<WxActScratchcardsPrizes> pageQuery,Integer itemCount) {
//		PageQueryWrapper<WxActScratchcardsPrizes> wrapper = new PageQueryWrapper<WxActScratchcardsPrizes>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
//		return (List<WxActScratchcardsPrizes>)super.query("queryPageList",wrapper);
//	}
//
//	@Override
//	public List<WxActScratchcardsPrizes> queryPrizes(String jwid,String createBy) {
//		Map<String, String> param=Maps.newConcurrentMap();
//		param.put("jwid", jwid);
//		param.put("createBy", createBy);
//		return (List<WxActScratchcardsPrizes>)super.query("queryPrizes", param);
//	}
//
//	//update-begin--Author:zhangweijian  Date: 20180329 for：根据jwid，创建人，奖品名称查询奖品表
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActScratchcardsPrizes> queryPrizesByName(String jwid, String createBy, String name) {
//		Map<String,String> param = Maps.newConcurrentMap();
//		param.put("jwid", jwid);
//		param.put("createBy", createBy);
//		param.put("name", name);
//		return super.query("queryAwardsByName",param);
//	}
//	//update-end--Author:zhangweijian  Date: 20180329 for：根据jwid，创建人，奖品名称查询奖品表
//
//}
//
