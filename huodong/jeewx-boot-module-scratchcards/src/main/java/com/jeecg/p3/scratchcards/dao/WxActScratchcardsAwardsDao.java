package com.jeecg.p3.scratchcards.dao;

import com.jeecg.p3.scratchcards.entity.WxActScratchcardsAwards;
import org.apache.ibatis.annotations.Param;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import java.util.List;

/**
 * 描述：</b>WxActScratchcardsAwardsDao<br>
 * @author：junfeng.zhou
 * @since：2016年07月13日 18时42分23秒 星期三 
 * @version:1.0
 */
public interface WxActScratchcardsAwardsDao extends GenericDao<WxActScratchcardsAwards>{
	
	public Integer count(PageQuery<WxActScratchcardsAwards> pageQuery);
	
	public List<WxActScratchcardsAwards> queryPageList(PageQueryWrapper<WxActScratchcardsAwards> wrapper);

	/**
	 * 根据jwid和创建人查询奖项
	 * @param jwid
	 * @param createBy
	 * @return
	 */
	public List<WxActScratchcardsAwards> queryAwards(@Param("jwid")String jwid,@Param("createBy")String createBy);

	/**
	 * 根据jwid查询奖项
	 * @param jwid
	 * @return
	 */
	public List<WxActScratchcardsAwards> queryAwards2(@Param("jwid")String jwid);

	//update-begin--Author:zhangweijian  Date: 20180329 for：根据jwid，创建人，奖项名称查询奖项表
	/**
	 * 根据jwid，创建人，奖项名称查询奖项表
	 * @param jwid
	 * @param createBy
	 * @param awardsName
	 * */
	public List<WxActScratchcardsAwards> queryAwardsByName(@Param("jwid")String jwid, @Param("createBy")String createBy, @Param("awardsName")String awardsName);
	//update-end--Author:zhangweijian  Date: 20180329 for：根据jwid，创建人，奖项名称查询奖项表

	
}

