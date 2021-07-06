package com.jeecg.p3.scratchcards.dao;

import com.jeecg.p3.scratchcards.entity.WxActScratchcardsRegistration;
import org.apache.ibatis.annotations.Param;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import java.util.List;

/**
 * 描述：</b>WxActScratchcardsRegistrationDao<br>
 * @author：junfeng.zhou
 * @since：2016年07月13日 18时42分25秒 星期三 
 * @version:1.0
 */
public interface WxActScratchcardsRegistrationDao extends GenericDao<WxActScratchcardsRegistration>{
	
	public Integer count(PageQuery<WxActScratchcardsRegistration> pageQuery);
	
	public List<WxActScratchcardsRegistration> queryPageList(PageQueryWrapper<WxActScratchcardsRegistration> wrapper);

	public WxActScratchcardsRegistration getOpenid(@Param("openid")String openid, @Param("actId")String actId);

	/**
	 * 更新相同天数的个人数量
	 * @param count
	 * @param numPerDay
	 * @param date
	 * @param id
	 */
	public Integer updateNumSameDay(@Param("count")Integer count, @Param("numPerDay")Integer numPerDay, @Param("date")String date,
								 @Param("id")String id);
	/**
	 * 更新不同天数的个人数量
	 */
	public Integer updateNumDistinctDay(@Param("count")Integer count,@Param("perDayNum")Integer perDayNum,@Param("date")String date,@Param("id")String id);
	/**
	 * 获取参与人数
	 * @param actId
	 * @param jwid
	 * @return
	 */
	public Integer queryCountByActIdAndJwid(@Param("actId")String actId, @Param("jwid")String jwid);

}

