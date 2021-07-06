package com.jeecg.p3.scratchcards.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jeecg.p3.baseApi.util.EmojiFilter;
import com.jeecg.p3.scratchcards.dao.WxActScratchcardsRecordDao;
import com.jeecg.p3.scratchcards.dao.WxActScratchcardsRegistrationDao;
import com.jeecg.p3.scratchcards.entity.*;
import com.jeecg.p3.scratchcards.exception.ScratchcardsException;
import com.jeecg.p3.scratchcards.exception.ScratchcardsExceptionEnum;
import com.jeecg.p3.scratchcards.service.*;
import org.jeecgframework.p3.base.vo.WeixinDto;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.common.utils.StringUtil;
import org.jeecgframework.p3.core.util.WeiXinHttpUtil;
import org.jeecgframework.p3.core.util.oConvertUtils;
import org.jeecgframework.p3.core.utils.common.*;
import org.jeecgframework.p3.core.utils.persistence.OptimisticLockingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("wxActScratchcardsRegistrationService")
public class WxActScratchcardsRegistrationServiceImpl implements
		WxActScratchcardsRegistrationService {
	@Resource
	private WxActScratchcardsRegistrationDao wxActScratchcardsRegistrationDao;
	@Autowired
	private WxActScratchcardsRelationService wxActScratchcardsRelationService;
	@Autowired
	private WxActScratchcardsPrizesService wxActScratchcardsPrizesService;
	@Autowired
	private WxActScratchcardsAwardsService wxActScratchcardsAwardsService;
	@Autowired
	private WxActScratchcardsRecordDao wxActScratchcardsRecordDao;
	@Autowired
	private WxActScratchcardsRecordService wxActScratchcardsRecordService;
	@Autowired
	private WxActScratchcardsService wxActScratchcardsService;
	@Autowired
	private WxActScratchcardsShareRecordService  wxActScratchcardsShareRecordService;
	
	@Override
	public void doAdd(
			WxActScratchcardsRegistration wxActScratchcardsRegistration) {
		wxActScratchcardsRegistrationDao.insert(wxActScratchcardsRegistration);
	}

	@Override
	public void doEdit(
			WxActScratchcardsRegistration wxActScratchcardsRegistration) {
		wxActScratchcardsRegistrationDao.update(wxActScratchcardsRegistration);
	}

	@Override
	public void doDelete(String id) {
		wxActScratchcardsRegistrationDao.delete(id);
	}

	@Override
	public WxActScratchcardsRegistration queryById(String id) {
		WxActScratchcardsRegistration wxActScratchcardsRegistration = wxActScratchcardsRegistrationDao
				.get(id);
		return wxActScratchcardsRegistration;
	}

	@Override
	public PageList<WxActScratchcardsRegistration> queryPageList(
			PageQuery<WxActScratchcardsRegistration> pageQuery) {
		PageList<WxActScratchcardsRegistration> result = new PageList<WxActScratchcardsRegistration>();
		Integer itemCount = wxActScratchcardsRegistrationDao.count(pageQuery);
		PageQueryWrapper<WxActScratchcardsRegistration> wrapper = new PageQueryWrapper<>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		List<WxActScratchcardsRegistration> list = wxActScratchcardsRegistrationDao
				.queryPageList(wrapper);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(),
				itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public WxActScratchcardsRegistration getOpenid(String openid, String actId) {

		return wxActScratchcardsRegistrationDao.getOpenid(openid, actId);
	}

	
	@Transactional(rollbackFor = Exception.class)
	public AjaxJson prizeRecord(WeixinDto weixinDto, AjaxJson j,String relationId) {
		//获取所需参数
		String actId = weixinDto.getActId();
		String jwid = weixinDto.getJwid();
		String openid = weixinDto.getOpenid();
		String nickname = "";
		String headimgUrl = "";
		//update-begin--Author:sunkai  Date:20180906 for：抽奖次数--------------------
		//1. 查询是openid是否存在，没有则插入用户参与记录
		WxActScratchcardsRegistration wxActScratchcardsRegistration = wxActScratchcardsRegistrationDao
				.getOpenid(openid, actId);
		SimpleDateFormat sb = new SimpleDateFormat("yyyyMMdd");
		if (wxActScratchcardsRegistration == null) {
			wxActScratchcardsRegistration = new WxActScratchcardsRegistration();
			wxActScratchcardsRegistration.setActId(actId);
			wxActScratchcardsRegistration.setOpenid(openid);
			wxActScratchcardsRegistration.setAwardsNum(0);
			wxActScratchcardsRegistration.setCreateTime(new Date());
			wxActScratchcardsRegistration.setAwardsStatus("1");
			wxActScratchcardsRegistration.setJwid(jwid);
			wxActScratchcardsRegistration.setUpdateTime(sb.format(new Date()));
			wxActScratchcardsRegistration.setRemainNumDay(0);
			wxActScratchcardsRegistration.setNickname(EmojiFilter
					.filterEmoji(nickname));
			wxActScratchcardsRegistrationDao.insert(wxActScratchcardsRegistration);// 插入参与者的记录表
		}
			
		//2.校验抽奖次数是否合法
		// 如果存在openid更新操作
		WxActScratchcards wxActScratchcards = wxActScratchcardsService.queryById(actId);
		Integer numPerDay = wxActScratchcards.getNumPerDay();
		if("1".equals(wxActScratchcards.getShareStatus())){
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			WxActScratchcardsShareRecord share = new WxActScratchcardsShareRecord();
			share.setActId(actId);
			share.setOpenid(openid);
			try {
				share.setShareDate(f.parse(f.format(new Date())));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//查询该用户当天是否已经分享过
			PageQuery<WxActScratchcardsShareRecord> pageQuery = new PageQuery<WxActScratchcardsShareRecord>();
			pageQuery.setPageNo(0);
			pageQuery.setPageSize(10);
			pageQuery.setQuery(share);
			List<WxActScratchcardsShareRecord> shareList = wxActScratchcardsShareRecordService.queryPageList1(pageQuery);
			if(null != shareList && shareList.size()>0){
				numPerDay = wxActScratchcards.getNumPerDay() + 1;
			}
		}
		if (sb.format(new Date()).equals(wxActScratchcardsRegistration.getUpdateTime())) {
			if (wxActScratchcardsRegistration.getRemainNumDay() >= numPerDay) {
				//用户当日抽奖次数>=活动每日抽奖次数
				throw new ScratchcardsException(
						ScratchcardsExceptionEnum.DATA_NOT_EXIST_ERROR,
						"今日抽奖次数已用完!");
			}
			if (wxActScratchcards.getCount() != 0 && wxActScratchcardsRegistration.getAwardsNum() >= wxActScratchcards.getCount()) {
				//活动抽奖总次数不等于0，即有限制；并且抽奖总次数>=活动的限制的每人抽奖总次数
				throw new ScratchcardsException(
						ScratchcardsExceptionEnum.DATA_NOT_EXIST_ERROR,
						"总抽奖次数已用完！");
			}
		} else {
			// 当前时间在更新时间之后，即今日还未抽奖
			if (wxActScratchcards.getCount() != 0 && wxActScratchcardsRegistration.getAwardsNum() >= wxActScratchcards.getCount()) {
				//活动抽奖总次数不等于0，即有限制；并且抽奖总次数>=活动的限制的每人抽奖总次数
				throw new ScratchcardsException(
						ScratchcardsExceptionEnum.DATA_NOT_EXIST_ERROR,
						"总抽奖次数已用完！");
			} 
		}

		//3.抽奖次数合法，开始抽奖流程
		WxActScratchcardsRelation relation = wxActScratchcardsRelationService.queryById(relationId);
		String name = null;// 奖品的名字
		String awardsName = null;// 奖项的名字
		String img = null;// 奖品图片
		Map<String, Object> mm = new HashMap<String, Object>();
		String prizeId = null;
		String awardId = null;
		String code = null;
		if (relation == null || "".equals(relation)) {
			name = null;// 奖品的名字
			awardsName = null;// 奖项的名字
			j.setObj("noPrizes");
		} else {
			prizeId = relation.getPrizeId();
			awardId = relation.getAwardId();
			WxActScratchcardsPrizes prizes = wxActScratchcardsPrizesService
					.queryById(prizeId);
			WxActScratchcardsAwards awards = wxActScratchcardsAwardsService
					.queryById(awardId);
			if (prizeId != null) {
				name = prizes.getName();// 奖品的名字
			}
			if (prizes != null && StringUtils.isNotEmpty(prizes.getImg())) {
				img = prizes.getImg();// 奖品图片
			}
			if (awards != null) {
				awardsName = awards.getAwardsName();// 奖品的等级
			}
			Integer rnamer = relation.getRemainNum();// 得到剩余数量的值
			if (rnamer >= 1) {// 逻辑控制
				rnamer = rnamer - 1;
				relation.setRemainNum(rnamer);
				wxActScratchcardsRelationService.doEdit(relation);// 修改商品的数量
				j.setObj("isPrizes");
			} else {
				j.setObj("noPrizes");
			}
			code = getCoupon();
			mm.put("awardsName", awardsName);
			mm.put("code", code);
			mm.put("name", name);
			mm.put("img", img);
		}
		
		// 4.插入中奖记录表的数据
		WxActScratchcardsRecord wxActScratchcardsRecord = new WxActScratchcardsRecord();
		JSONObject userobj = WeiXinHttpUtil.getGzUserInfo(openid, jwid);
		if (userobj != null && userobj.containsKey("nickname")) {
			nickname = userobj.getString("nickname");
		}
		if (StringUtil.isEmpty(nickname)) {
			nickname = "游客";
		}
		if(userobj != null && userobj.containsKey("headimgurl")){
			headimgUrl = userobj.getString("headimgurl");
			wxActScratchcardsRecord.setHeadImg(headimgUrl);
		}
		if (StringUtil.isEmpty(headimgUrl)) {
			wxActScratchcardsRecord.setHeadImg("http://static.h5huodong.com/upload/common/defaultHeadImg.png");
		}
		wxActScratchcardsRecord.setActId(actId);
		wxActScratchcardsRecord.setOpenid(openid);
		wxActScratchcardsRecord.setNickname(EmojiFilter.filterEmoji(nickname));
		wxActScratchcardsRecord.setCreateTime(new Date());
		wxActScratchcardsRecord.setAwardsName(awardsName);
		if (name == null && awardsName == null) {
			wxActScratchcardsRecord.setPrizesState("0");
		} else {
			wxActScratchcardsRecord.setPrizesState("1");
			wxActScratchcardsRecord.setCode(code);
			wxActScratchcardsRecord.setRecieveStatus("0");
			// 设置中奖序号
			Integer maxAwardsSeq = wxActScratchcardsRecordDao
					.getMaxAwardsSeq(wxActScratchcardsRecord.getActId());
			Integer nextAwardsSeq = oConvertUtils.getInt(maxAwardsSeq) + 1;
			wxActScratchcardsRecord.setSeq(nextAwardsSeq);
		}
		wxActScratchcardsRecord.setPrizesName(name);
		wxActScratchcardsRecord.setJwid(jwid);
		wxActScratchcardsRecord.setNickname(nickname);
		wxActScratchcardsRecord.setAwardsId(awardId);
		wxActScratchcardsRecordService.doAdd(wxActScratchcardsRecord);// 插入中奖记录
		mm.put("id", wxActScratchcardsRecord.getId());
		j.setAttributes(mm);
		//5.更新用户参与记录中的抽奖次数
		if (sb.format(new Date()).equals(wxActScratchcardsRegistration.getUpdateTime())) {
			// 如果是当前时间,即今天已经抽奖过，则当日抽奖次数和总抽奖次数+1
			int row = wxActScratchcardsRegistrationDao.updateNumSameDay(wxActScratchcards.getCount(), numPerDay,sb.format(new Date()), wxActScratchcardsRegistration.getId());
			if (row == 0) {
				throw new OptimisticLockingException("乐观锁异常");
			}
		} else {
			int row = wxActScratchcardsRegistrationDao.updateNumDistinctDay(wxActScratchcards.getCount(), numPerDay, sb.format(new Date()), wxActScratchcardsRegistration.getId());
			if (row == 0) {
				throw new OptimisticLockingException("乐观锁异常");
			}
			//--author:sunkai ----- date:20180906 ----- for：总次数为零默认为无限制-----------
		}
		//update-end--Author:sunkai  Date:20180906 for：抽奖次数--------------------
		return j;
	}
	
	
	/**
	 * 生成12位数字,大写字母,小写字母随机的券码，拼接100到999的随机数，共20位
	 * @return 生成后的券码
	 */
	private synchronized static String getCoupon(){
		char ch[]=new char[]{'0','1','2','3','4','5','6','7','8','9'
				,'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
				,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
				};
		Random rand = new Random();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<12;i++){
			sb.append(ch[rand.nextInt(62)]);
		}
		return sb.toString();
	}

	@Override
	public Integer queryCountByActIdAndJwid(String actId, String jwid) {
		return wxActScratchcardsRegistrationDao.queryCountByActIdAndJwid(actId,jwid);
	}
}
