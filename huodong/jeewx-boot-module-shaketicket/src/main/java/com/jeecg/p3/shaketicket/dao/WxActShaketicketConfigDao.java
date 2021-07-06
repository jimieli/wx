package com.jeecg.p3.shaketicket.dao;

import com.jeecg.p3.shaketicket.entity.WxActShaketicketConfig;
import org.apache.ibatis.annotations.Param;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import java.util.List;

/**
 * 描述：</b>WxActShaketicketConfigDao<br>
 * @author：pituo
 * @since：2015年12月24日 11时08分29秒 星期四 
 * @version:1.0
 */
public interface WxActShaketicketConfigDao extends GenericDao<WxActShaketicketConfig>{
	
	public Integer count(PageQuery<WxActShaketicketConfig> pageQuery);
	
	public List<WxActShaketicketConfig> queryPageList(PageQueryWrapper<WxActShaketicketConfig> wrapper);
	public void updateRemainNum(@Param("actid")String actid,@Param("jwid")String jwid,@Param("awardid")String awardid);
	
	public List<WxActShaketicketConfig> queryByActId(@Param("actId") String actId);
	
	public void batchDeleteByActId(@Param("actId") String actId) ;
	
	public void bactchDeleteOldAwards(@Param("ids")List<String> ids,@Param("actId")String actId) ;
	
	public void updateNum(@Param("id")String id,@Param("num")Integer num);

	//update-begin--Author:zhangweijian  Date: 20180330 for：根据awardid判断该奖项是否使用
	/**
	 * 根据awardid判断该奖项是否使用
	 * */
	public Integer validUsed(@Param("awardId")String awardId);
	//update-end--Author:zhangweijian  Date: 20180330 for：根据awardid判断该奖项是否使用
}

