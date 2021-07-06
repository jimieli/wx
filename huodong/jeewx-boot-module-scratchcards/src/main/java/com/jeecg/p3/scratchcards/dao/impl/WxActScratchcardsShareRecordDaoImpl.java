//package com.jeecg.p3.scratchcards.dao.impl;
//
//import java.util.List;
//
//import org.jeecgframework.p3.core.utils.common.PageQuery;
//import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
//import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
//import org.springframework.stereotype.Repository;
//import com.jeecg.p3.scratchcards.dao.WxActScratchcardsShareRecordDao;
//import com.jeecg.p3.scratchcards.entity.WxActScratchcardsShareRecord;
//
///**
// * 描述：</b>分享记录表<br>
// * @author：sunkai
// * @since：2018年10月17日 10时34分17秒 星期三
// * @version:1.0
// */
//@Repository("wxActScratchcardsShareRecordDao")
//public class WxActScratchcardsShareRecordDaoImpl extends GenericDaoDefault<WxActScratchcardsShareRecord> implements WxActScratchcardsShareRecordDao{
//
//	@Override
//	public Integer count(PageQuery<WxActScratchcardsShareRecord> pageQuery) {
//		return (Integer) super.queryOne("count",pageQuery);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<WxActScratchcardsShareRecord> queryPageList(
//			PageQuery<WxActScratchcardsShareRecord> pageQuery,Integer itemCount) {
//		PageQueryWrapper<WxActScratchcardsShareRecord> wrapper = new PageQueryWrapper<WxActScratchcardsShareRecord>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
//		return (List<WxActScratchcardsShareRecord>)super.query("queryPageList",wrapper);
//	}
//
//
//}
//
