package com.jeecg.p3.scratchcards.service;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.scratchcards.entity.WxActScratchcardsRelation;

/**
 * 描述：</b>WxActScratchcardsRelationService<br>
 * @author：junfeng.zhou
 * @since：2016年07月13日 18时42分26秒 星期三 
 * @version:1.0
 */
public interface WxActScratchcardsRelationService {
	
	
	public void doAdd(WxActScratchcardsRelation wxActScratchcardsRelation);
	
	public void doEdit(WxActScratchcardsRelation wxActScratchcardsRelation);
	
	public void doDelete(String id);
	
	public WxActScratchcardsRelation queryById(String id);
	
	public PageList<WxActScratchcardsRelation> queryPageList(PageQuery<WxActScratchcardsRelation> pageQuery);

	public List<WxActScratchcardsRelation> queryByActId(String actId);

	public List<WxActScratchcardsRelation> queryPrizeAndAward(String actId);

	public List<WxActScratchcardsRelation> queryByActIdAndJwid(String actId,
			String jwid);

}

