package com.jeecg.p3.shaketicket.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jeecg.p3.baseApi.util.EmojiFilter;
import com.jeecg.p3.shaketicket.dao.*;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketAward;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketCoupon;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketRecord;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketRegistration;
import com.jeecg.p3.shaketicket.service.WxActShaketicketRecordService;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.p3.base.vo.WeixinDto;
import org.jeecgframework.p3.core.util.WeiXinHttpUtil;
import org.jeecgframework.p3.core.util.oConvertUtils;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import org.jeecgframework.p3.core.utils.persistence.OptimisticLockingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("wxActShaketicketRecordService")
public class WxActShaketicketRecordServiceImpl implements WxActShaketicketRecordService {
	@Resource
	private WxActShaketicketRecordDao wxActShaketicketRecordDao;
	@Resource
	private WxActShaketicketAwardDao wxActShaketicketAwardDao;
	@Resource
	private WxActShaketicketConfigDao wxActShaketicketConfigDao;
	@Resource
	private WxActShaketicketCouponDao wxActShaketicketCouponDao;
	@Resource
	private WxActShaketicketRegistrationDao wxActShaketicketRegistrationDao;
	
	@Override
	public void doAdd(WxActShaketicketRecord wxActShaketicketRecord) {
		wxActShaketicketRecordDao.insert(wxActShaketicketRecord);
	}

	@Override
	public void doEdit(WxActShaketicketRecord wxActShaketicketRecord) {
		wxActShaketicketRecordDao.update(wxActShaketicketRecord);
	}

	@Override
	public void doDelete(String id) {
		wxActShaketicketRecordDao.delete(id);
	}

	@Override
	public WxActShaketicketRecord queryById(String id) {
		WxActShaketicketRecord wxActShaketicketRecord  = wxActShaketicketRecordDao.get(id);
		return wxActShaketicketRecord;
	}

	@Override
	public PageList<WxActShaketicketRecord> queryPageList(
		PageQuery<WxActShaketicketRecord> pageQuery) {
		PageList<WxActShaketicketRecord> result = new PageList<WxActShaketicketRecord>();
		Integer itemCount = wxActShaketicketRecordDao.count(pageQuery);
		PageQueryWrapper<WxActShaketicketRecord> wrapper = new PageQueryWrapper<WxActShaketicketRecord>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		List<WxActShaketicketRecord> list = wxActShaketicketRecordDao.queryPageList(wrapper);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public Map<String, Integer> getRecordCountByActIdAndOpenid(String actId, String openid,Date currDate) {
		// TODO Auto-generated method stub
		return wxActShaketicketRecordDao.getRecordCountByActIdAndOpenid(actId, openid,currDate);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public WxActShaketicketAward creatRecordAndReturnAward(
			WxActShaketicketRecord shaketicketRecord, WeixinDto weixinDto, WxActShaketicketRegistration registration) {
		//1.生成摇一摇记录
		shaketicketRecord.setActId(weixinDto.getActId());
		shaketicketRecord.setOpenid(weixinDto.getOpenid());
		shaketicketRecord.setJwid(weixinDto.getJwid());
		shaketicketRecord.setDrawTime(new Date());//记录抽奖时间
		shaketicketRecord.setReceiveStatus("0");//初始化为未领奖
		//获取中奖序号
		Integer maxAwardsSeq = wxActShaketicketRecordDao.getMaxAwardsSeq(shaketicketRecord.getActId());
		Integer nextAwardsSeq = oConvertUtils.getInt(maxAwardsSeq)+1;
		shaketicketRecord.setAwardsSeq(nextAwardsSeq);
		wxActShaketicketRecordDao.insert(shaketicketRecord);
		//update-begin--Author:zhangweijian Date:20181012 for：添加事务，更新注册信息，数量信息
		//2.更新个人的信息
		//update-begin--Author:zhangweijian Date:20181012 for：装载微信头像、昵称
		//装载微信头像、昵称
		if("content/shaketicket/back/img/defaultHeadImg.png".equals(registration.getHeadimg())){
			JSONObject userobj = WeiXinHttpUtil.getGzUserInfo(weixinDto.getOpenid(), weixinDto.getJwid());
			if(userobj!=null&& userobj.containsKey("nickname")){
				registration.setNickname(EmojiFilter.filterEmoji(userobj.getString("nickname")));
				registration.setHeadimg(userobj.getString("headimgurl"));
			}
		}
		registration.setShakeCount(registration.getShakeCount()+1);
		registration.setDayShakeNum(registration.getDayShakeNum()+1);
		registration.setLastShakeDate(new Date());
		//3.更新奖品数量
		if(StringUtils.isEmpty(shaketicketRecord.getAwardId())){
			wxActShaketicketRegistrationDao.update(registration);
			return new WxActShaketicketAward();			 
		}else{
			wxActShaketicketRegistrationDao.update(registration);
			wxActShaketicketConfigDao.updateRemainNum(weixinDto.getActId(), weixinDto.getJwid(), shaketicketRecord.getAwardId());
			return wxActShaketicketAwardDao.get(shaketicketRecord.getAwardId());
		}
		//update-begin--Author:zhangweijian Date:20181012 for：装载微信头像、昵称
	}

	@Override
	public List<WxActShaketicketRecord> queryMyAwardsRecordByOpenidAndActid(
			String openid, String actId) {
		// TODO Auto-generated method stub
		return wxActShaketicketRecordDao.queryMyAwardsRecordByOpenidAndActid(openid, actId);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void creatRecord(
			WxActShaketicketRecord shaketicketRecord, WeixinDto weixinDto,WxActShaketicketAward award,WxActShaketicketCoupon coupon) {
		shaketicketRecord.setActId(weixinDto.getActId());
		shaketicketRecord.setOpenid(weixinDto.getOpenid());
		shaketicketRecord.setJwid(weixinDto.getJwid());
		shaketicketRecord.setDrawTime(new Date());//记录中奖奖时间
		shaketicketRecord.setReceiveStatus("0");//初始化为未领奖
		//获取中奖序号
		Integer maxAwardsSeq = wxActShaketicketRecordDao.getMaxAwardsSeq(shaketicketRecord.getActId());
		Integer nextAwardsSeq = oConvertUtils.getInt(maxAwardsSeq)+1;
		shaketicketRecord.setAwardsSeq(nextAwardsSeq);
	    wxActShaketicketRecordDao.insert(shaketicketRecord);
	    wxActShaketicketConfigDao.updateRemainNum(weixinDto.getActId(), weixinDto.getJwid(), shaketicketRecord.getAwardId());
		//更新卡券中奖状态
	    //TODO 所有券码都变成已使用
	    coupon.setStatus("1");//已使用
	    int row = wxActShaketicketCouponDao.updateStatus(coupon.getId());
	    if (row == 0) {
			throw new OptimisticLockingException("乐观锁异常");
		}
//	    if("A001".equals(award.getCardId())){
//	    	coupon.setStatus("1");//已使用
//		    wxActShaketicketCouponDao.updateStatus(coupon.getId());
//	    }
	}

	/**
	 * @功能：根据actId查询摇一摇记录
	 */
	@Override
	public List<WxActShaketicketRecord> queryBargainAwardsByActId(String actId,String drawStatus) {
		return wxActShaketicketRecordDao.queryBargainAwardsByActId(actId,drawStatus);
	}

	//update-begin--Author:zhangweijian  Date: 20180919 for：统计当前活动的参与人数
	/**
	 * @功能：统计当前活动的参与人数
	 */
	@Override
	public int getCountByActId(String actId) {
		return wxActShaketicketRecordDao.getCountByActId(actId);
	}
	//update-end--Author:zhangweijian  Date: 20180919 for：统计当前活动的参与人数

	//update-begin--Author:zhangweijian  Date: 20181022 for：根据actId和兑奖码查询摇奖记录信息
	/**
	 * @功能：根据actId和兑奖码查询摇奖记录信息
	 */
	@Override
	public WxActShaketicketRecord queryByActIdAndawardCode(String actId,String awardCode) {
		return wxActShaketicketRecordDao.queryByActIdAndawardCode(actId,awardCode);
	}
	//update-end--Author:zhangweijian  Date: 20181022 for：根据actId和兑奖码查询摇奖记录信息
	
}
