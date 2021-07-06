package com.jeecg.p3.scratchcards.service;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsPrizes;

/**
 * 描述：</b>WxActScratchcardsPrizesService<br>
 * @author：junfeng.zhou
 * @since：2016年07月13日 18时42分24秒 星期三 
 * @version:1.0
 */
public interface WxActScratchcardsPrizesService {
	
	
	public void doAdd(WxActScratchcardsPrizes wxActScratchcardsPrizes);
	
	public void doEdit(WxActScratchcardsPrizes wxActScratchcardsPrizes);
	
	public void doDelete(String id);
	
	public WxActScratchcardsPrizes queryById(String id);
	
	public PageList<WxActScratchcardsPrizes> queryPageList(PageQuery<WxActScratchcardsPrizes> pageQuery);

	public List<WxActScratchcardsPrizes> queryPrizes(String jwid,String createBy);

	//update-begin--Author:zhangweijian  Date: 20180330 for：根据奖项id在奖项，奖品关系表查询水果机奖品配置表中奖品是否存在
	/**
	 * 根据奖品id在奖项，奖品关系表查询该奖品是否存在
	 * @param awardId
	 * */
	public Boolean validUsed(String id);
	//update-end--Author:zhangweijian  Date: 20180330 for：根据奖项id在奖项，奖品关系表查询水果机奖品配置表中奖品是否存在

	public List<WxActScratchcardsPrizes> queryPrizesByName(String jwid,
			String createBy, String name);
}

