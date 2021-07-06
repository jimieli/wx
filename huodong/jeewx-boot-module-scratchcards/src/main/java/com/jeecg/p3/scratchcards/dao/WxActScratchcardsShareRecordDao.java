package com.jeecg.p3.scratchcards.dao;

import com.jeecg.p3.scratchcards.entity.WxActScratchcardsShareRecord;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import java.util.List;

/**
 * 描述：</b>分享记录表<br>
 * @author：sunkai
 * @since：2018年10月17日 10时34分17秒 星期三 
 * @version:1.0
 */
public interface WxActScratchcardsShareRecordDao extends GenericDao<WxActScratchcardsShareRecord>{
	
	public Integer count(PageQuery<WxActScratchcardsShareRecord> pageQuery);
	
	public List<WxActScratchcardsShareRecord> queryPageList(PageQueryWrapper<WxActScratchcardsShareRecord> wrapper);
	
}

