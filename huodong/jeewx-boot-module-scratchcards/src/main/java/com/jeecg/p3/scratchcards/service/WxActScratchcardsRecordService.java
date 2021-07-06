package com.jeecg.p3.scratchcards.service;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsRecord;

/**
 * 描述：</b>WxActScratchcardsRecordService<br>
 * @author：junfeng.zhou
 * @since：2016年07月13日 18时42分25秒 星期三 
 * @version:1.0
 */
public interface WxActScratchcardsRecordService {
	
	
	public void doAdd(WxActScratchcardsRecord wxActScratchcardsRecord);
	
	public void doEdit(WxActScratchcardsRecord wxActScratchcardsRecord);
	
	public void doDelete(String id);
	
	public WxActScratchcardsRecord queryById(String id);
	
	public PageList<WxActScratchcardsRecord> queryPageList(PageQuery<WxActScratchcardsRecord> pageQuery);

	public WxActScratchcardsRecord queryByCode(String code);

	public List<WxActScratchcardsRecord> queryList(String actId);

	public List<WxActScratchcardsRecord> queryMyList(String openid,String actId);

	/**
	 * 导出中奖纪录
	 * @param actId
	 * @param jwid
	 */
	public List<WxActScratchcardsRecord> exportExcel(String actId, String jwid);

	public List<WxActScratchcardsRecord> exportExcel1(String actId, String jwid);

	public Integer countByActId(String actId, String jwid, String openid);

	public List<WxActScratchcardsRecord> queryListByActId(String actId,
			String jwid, String openid, int i, int pageSize);
}

