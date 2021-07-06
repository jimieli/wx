package com.jeecg.p3.scratchcards.service;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsAwards;

/**
 * 描述：</b>WxActScratchcardsAwardsService<br>
 * @author：junfeng.zhou
 * @since：2016年07月13日 18时42分23秒 星期三 
 * @version:1.0
 */
public interface WxActScratchcardsAwardsService {
	
	
	public void doAdd(WxActScratchcardsAwards wxActScratchcardsAwards);
	
	public void doEdit(WxActScratchcardsAwards wxActScratchcardsAwards);
	
	public void doDelete(String id);
	
	public WxActScratchcardsAwards queryById(String id);
	
	public PageList<WxActScratchcardsAwards> queryPageList(PageQuery<WxActScratchcardsAwards> pageQuery);

	/**
	 * 根据jwid和创建人查询奖项
	 * @param jwid
	 * @param createBy
	 * @return
	 */
	public List<WxActScratchcardsAwards> queryAwards(String jwid,String createBy);

	/**
	 * 根据jwid查询奖项
	 * @param jwid
	 * @return
	 */
	public List<WxActScratchcardsAwards> queryAwards(String jwid);

	//update-begin--Author:zhangweijian  Date: 20180330 for：根据奖项id在奖项，奖品关系表查询水果机奖品配置表中奖品是否存在
	/**
	 * 根据奖项id在奖项，奖品关系表查询该奖品是否存在
	 * @param awardId
	 * */
	public Boolean validUsed(String id);
	//update-end--Author:zhangweijian  Date: 20180330 for：根据奖项id在奖项，奖品关系表查询水果机奖品配置表中奖品是否存在

	public List<WxActScratchcardsAwards> queryAwardsByName(String jwid,
			String createBy, String awardsName);
}

