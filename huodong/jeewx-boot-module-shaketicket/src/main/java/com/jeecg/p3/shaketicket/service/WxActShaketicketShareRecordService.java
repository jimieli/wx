package com.jeecg.p3.shaketicket.service;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketShareRecord;

/**
 * 描述：</b>摇一摇分享记录表<br>
 * @author：junfeng.zhou
 * @since：2018年10月17日 10时27分39秒 星期三 
 * @version:1.0
 */
public interface WxActShaketicketShareRecordService {
	
	
	public void doAdd(WxActShaketicketShareRecord wxActShaketicketShareRecord);
	
	public void doEdit(WxActShaketicketShareRecord wxActShaketicketShareRecord);
	
	public void doDelete(String id);
	
	public WxActShaketicketShareRecord queryById(String id);
	
	public PageList<WxActShaketicketShareRecord> queryPageList(PageQuery<WxActShaketicketShareRecord> pageQuery);

	/**
	 * @功能：根据活动id和openid查询分享记录
	 * @param actId
	 * @param openid
	 * @param shareDate 
	 * @return
	 */
	public List<WxActShaketicketShareRecord> queryByActIdAndOpenid(String actId, String openid, String shareDate);
}

