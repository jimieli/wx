package com.jeecg.p3.shaketicket.dao;

import com.jeecg.p3.shaketicket.entity.WxActShaketicketRecord;
import org.apache.ibatis.annotations.Param;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述：</b>WxActShaketicketRecordDao<br>
 * @author：pituo
 * @since：2015年12月22日 19时03分50秒 星期二 
 * @version:1.0
 */
public interface WxActShaketicketRecordDao extends GenericDao<WxActShaketicketRecord>{
	
	public Integer count(PageQuery<WxActShaketicketRecord> pageQuery);
	
	public List<WxActShaketicketRecord> queryPageList(PageQueryWrapper<WxActShaketicketRecord> wrapper);
	public Map<String, Integer> getRecordCountByActIdAndOpenid(@Param("actId")String actId,@Param("openid")String openid,@Param("currDate")Date currDate);
	
	public Integer getMaxAwardsSeq(@Param("actId") String actId);
	
	public List<WxActShaketicketRecord> queryMyAwardsRecordByOpenidAndActid(@Param("openid")String openid,@Param("actId")String actId);
	//update-begin--Author:zhangweijian  Date: 20180925 for：添加一个中奖状态参数
	public List<WxActShaketicketRecord> queryBargainAwardsByActId(@Param("actId")String actId, @Param("drawStatus")String drawStatus);
	//update-end--Author:zhangweijian  Date: 20180925 for：添加一个中奖状态参数
	public List<WxActShaketicketRecord> queryBargainAwardsByActIdForWin(@Param("actId") String actId);

	//update-begin--Author:zhangweijian  Date: 20180919 for：统计当前活动的参与人数
	/**
	 * @功能：统计当前活动的参与人数
	 * @param actId
	 * @return
	 */
	public int getCountByActId(@Param("actId") String actId);
	//update-end--Author:zhangweijian  Date: 20180919 for：统计当前活动的参与人数

	//update-begin--Author:zhangweijian  Date: 20181022 for：根据actId和兑奖码查询摇奖记录信息
	/**
	 * @功能：根据actId和兑奖码查询摇奖记录信息
	 */
	public WxActShaketicketRecord queryByActIdAndawardCode(@Param("actId")String actId,@Param("awardCode")String awardCode);
	//update-end--Author:zhangweijian  Date: 20181022 for：根据actId和兑奖码查询摇奖记录信息
}

