package com.jeecg.p3.scratchcards.service;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.scratchcards.entity.WxActScratchcardsShareRecord;

/**
 * 描述：</b>分享记录表<br>
 * @author：sunkai
 * @since：2018年10月17日 10时34分17秒 星期三 
 * @version:1.0
 */
public interface WxActScratchcardsShareRecordService {
	
	
	public void doAdd(WxActScratchcardsShareRecord wxActScratchcardsShareRecord);
	
	public void doEdit(WxActScratchcardsShareRecord wxActScratchcardsShareRecord);
	
	public void doDelete(String id);
	
	public WxActScratchcardsShareRecord queryById(String id);
	
	public PageList<WxActScratchcardsShareRecord> queryPageList(PageQuery<WxActScratchcardsShareRecord> pageQuery);

	public List<WxActScratchcardsShareRecord> queryPageList1(
			PageQuery<WxActScratchcardsShareRecord> pageQuery);
}

