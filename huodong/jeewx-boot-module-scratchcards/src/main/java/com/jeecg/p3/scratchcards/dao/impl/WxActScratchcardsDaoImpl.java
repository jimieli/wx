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
//import com.jeecg.p3.scratchcards.dao.WxActScratchcardsDao;
//import com.jeecg.p3.scratchcards.entity.WxActScratchcards;
//
///**
// * 描述：</b>WxActScratchcardsDaoImpl<br>
// * @author：junfeng.zhou
// * @since：2016年07月13日 18时42分22秒 星期三
// * @version:1.0
// */
//@Repository("wxActScratchcardsDao")
//public class WxActScratchcardsDaoImpl extends GenericDaoDefault<WxActScratchcards> implements WxActScratchcardsDao{
//
//	@Override
//	public Integer count(PageQuery<WxActScratchcards> pageQuery) {
//		return (Integer) super.queryOne("count",pageQuery);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActScratchcards> queryPageList(
//			PageQuery<WxActScratchcards> pageQuery,Integer itemCount) {
//		PageQueryWrapper<WxActScratchcards> wrapper = new PageQueryWrapper<WxActScratchcards>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
//		return (List<WxActScratchcards>)super.query("queryPageList",wrapper);
//	}
//
//	@Override
//	public void editShortUrl(String id, String shortUrl) {
//		Map<String,String> param = Maps.newConcurrentMap();
//		param.put("id", id);
//		param.put("shortUrl", shortUrl);
//		super.update("editShortUrl", param);
//	}
//
//
//}
//
