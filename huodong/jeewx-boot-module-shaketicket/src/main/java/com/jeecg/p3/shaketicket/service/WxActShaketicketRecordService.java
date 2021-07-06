package com.jeecg.p3.shaketicket.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeecgframework.p3.base.vo.WeixinDto;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.shaketicket.entity.WxActShaketicketAward;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketCoupon;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketRecord;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketRegistration;

/**
 * 描述：</b>WxActShaketicketRecordService<br>
 * @author：pituo
 * @since：2015年12月22日 19时03分50秒 星期二 
 * @version:1.0
 */
public interface WxActShaketicketRecordService {
	
	
	public void doAdd(WxActShaketicketRecord wxActShaketicketRecord);
	
	public void doEdit(WxActShaketicketRecord wxActShaketicketRecord);
	
	public void doDelete(String id);
	
	public WxActShaketicketRecord queryById(String id);
	public Map<String, Integer> getRecordCountByActIdAndOpenid(String actId,String openid,Date currDate);
	
	public PageList<WxActShaketicketRecord> queryPageList(PageQuery<WxActShaketicketRecord> pageQuery);
	//update-begin--Author:zhangweijian Date:20181012 for：添加一个用户对象参数
	public WxActShaketicketAward creatRecordAndReturnAward(WxActShaketicketRecord shaketicketRecord,WeixinDto weixinDto, WxActShaketicketRegistration registration);
	//update-end--Author:zhangweijian Date:20181012 for：添加一个用户对象参数
	public List<WxActShaketicketRecord> queryMyAwardsRecordByOpenidAndActid(String openid,String actId);
	
	public void creatRecord(WxActShaketicketRecord shaketicketRecord,WeixinDto weixinDto,WxActShaketicketAward award,WxActShaketicketCoupon coupon);

	//update-begin--Author:zhangweijian  Date: 20180913 for：添加奖品图片字段
	/**
	 * @param drawStatus 
	 * @功能：根据actId查询摇一摇记录
	 */
	public List<WxActShaketicketRecord> queryBargainAwardsByActId(String actId, String drawStatus);
	//update-end--Author:zhangweijian  Date: 20180913 for：添加奖品图片字段
	
	//update-begin--Author:zhangweijian  Date: 20180919 for：统计当前活动的参与人数
	/**
	 * @功能：统计当前活动的参与人数
	 */
	public int getCountByActId(String actId);
	//update-end--Author:zhangweijian  Date: 20180919 for：统计当前活动的参与人数

	//update-begin--Author:zhangweijian  Date: 20181022 for：根据actId和兑奖码查询摇奖记录信息
	/**
	 * @功能：根据actId和兑奖码查询摇奖记录信息
	 */
	public WxActShaketicketRecord queryByActIdAndawardCode(String actId,String awardCode);
	//update-end--Author:zhangweijian  Date: 20181022 for：根据actId和兑奖码查询摇奖记录信息
}

