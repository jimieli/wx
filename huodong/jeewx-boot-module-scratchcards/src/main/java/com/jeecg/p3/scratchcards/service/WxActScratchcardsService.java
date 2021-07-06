package com.jeecg.p3.scratchcards.service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.scratchcards.entity.WxActScratchcards;

/**
 * 描述：</b>WxActScratchcardsService<br>
 * @author：junfeng.zhou
 * @since：2016年07月13日 18时42分22秒 星期三 
 * @version:1.0
 */
public interface WxActScratchcardsService {
	
	
	public void doAdd(WxActScratchcards wxActScratchcards);
	
	public String doEdit(WxActScratchcards wxActScratchcards);
	
	public void doDelete(String id);
	
	public WxActScratchcards queryById(String id);
	
	public PageList<WxActScratchcards> queryPageList(PageQuery<WxActScratchcards> pageQuery);

	/**
	 * 修改短链接
	 * @param id
	 * @param shortUrl
	 */
	public void editShortUrl(String id, String shortUrl);
}

