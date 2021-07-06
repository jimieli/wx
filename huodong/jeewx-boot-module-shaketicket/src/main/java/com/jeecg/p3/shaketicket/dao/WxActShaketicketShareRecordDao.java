package com.jeecg.p3.shaketicket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.shaketicket.entity.WxActShaketicketShareRecord;

/**
 * 描述：</b>摇一摇分享记录表<br>
 * @author：junfeng.zhou
 * @since：2018年10月17日 10时27分39秒 星期三 
 * @version:1.0
 */
public interface WxActShaketicketShareRecordDao extends GenericDao<WxActShaketicketShareRecord>{
	
	public Integer count(PageQuery<WxActShaketicketShareRecord> pageQuery);
	
	public List<WxActShaketicketShareRecord> queryPageList(PageQueryWrapper<WxActShaketicketShareRecord> wrapper);

	/**
	 * @功能：根据活动id和openid查询分享记录
	 * @param actId
	 * @param openid
	 * @param shareDate 
	 * @return
	 */
	public List<WxActShaketicketShareRecord> queryByActIdAndOpenid(@Param("actId")String actId, @Param("openid")String openid, @Param("shareDate")String shareDate);
	
}

