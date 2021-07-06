package com.jeecg.p3.scratchcards.service;

import org.jeecgframework.p3.base.vo.WeixinDto;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.scratchcards.entity.WxActScratchcardsRegistration;

/**
 * 描述：</b>WxActScratchcardsRegistrationService<br>
 * 
 * @author：junfeng.zhou
 * @since：2016年07月13日 18时42分25秒 星期三
 * @version:1.0
 */
public interface WxActScratchcardsRegistrationService {

	public void doAdd(WxActScratchcardsRegistration wxActScratchcardsRegistration);

	public void doEdit(WxActScratchcardsRegistration wxActScratchcardsRegistration);

	public void doDelete(String id);

	public WxActScratchcardsRegistration queryById(String id);

	public PageList<WxActScratchcardsRegistration> queryPageList(
			PageQuery<WxActScratchcardsRegistration> pageQuery);

	public WxActScratchcardsRegistration getOpenid(String openid, String actId);

	public AjaxJson prizeRecord(WeixinDto weixinDto, AjaxJson j,String relationId);

	public Integer queryCountByActIdAndJwid(String actId, String jwid);
}
