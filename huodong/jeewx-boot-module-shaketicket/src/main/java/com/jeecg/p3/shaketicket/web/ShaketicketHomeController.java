package com.jeecg.p3.shaketicket.web;

import com.alibaba.fastjson.JSONObject;
import com.jeecg.p3.baseApi.service.BaseApiJwidService;
import com.jeecg.p3.baseApi.service.BaseApiSystemService;
import com.jeecg.p3.baseApi.util.EmojiFilter;
import com.jeecg.p3.baseApi.util.WeixinUserUtil;
import com.jeecg.p3.core.annotation.SkipAuth;
import com.jeecg.p3.dict.service.SystemActTxtService;
import com.jeecg.p3.shaketicket.def.SystemShakProperties;
import com.jeecg.p3.shaketicket.entity.*;
import com.jeecg.p3.shaketicket.exception.ShaketicketHomeException;
import com.jeecg.p3.shaketicket.exception.ShaketicketHomeExceptionEnum;
import com.jeecg.p3.shaketicket.service.*;
import com.jeecg.p3.shaketicket.util.LotteryUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.base.vo.WeixinDto;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.common.utils.StringUtil;
import org.jeecgframework.p3.core.util.WeiXinHttpUtil;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.p3.core.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/shaketicket")
public class ShaketicketHomeController extends BaseController {
	public final static Logger LOG = LoggerFactory.getLogger(ShaketicketHomeController.class);
	@Autowired
	private WxActShaketicketHomeService homeService;
	@Autowired
	private WxActShaketicketRecordService recordService;
	@Autowired
	private WxActShaketicketAwardService awardService;
	@Autowired
	private SystemActTxtService systemActTxtService;
	@Autowired
	private BaseApiJwidService baseApiJwidService;
	@Autowired
	private BaseApiSystemService baseApiSystemService;
	@Autowired
	private WxActShaketicketRegistrationService wxActShaketicketRegistrationService;
	@Autowired
	private WxActShaketicketShareRecordService wxActShaketicketShareRecordService;
	@Autowired
	private BaseApiJwidService BaseApiJwidService;
	private static String domain = SystemShakProperties.domain;
	//update-begin--Author:zhangweijian Date:20181012 for：增加了注册逻辑
	/**
	 * 进入首页
	 * @param weixinDto
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/toIndex", method = { RequestMethod.GET,RequestMethod.POST })
	public void toIndex(@ModelAttribute WeixinDto weixinDto,HttpServletRequest request, HttpServletResponse response)throws Exception {
		LOG.info("toIndex parameter WeixinDto={}.",new Object[] { weixinDto });
		// 装载微信所需参数
		String jwid = weixinDto.getJwid();
		String appid = weixinDto.getAppid();
		String actId = weixinDto.getActId();
		String openid = weixinDto.getOpenid();
		String adv =request.getParameter("adv");
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "shaketicket/default/vm/index.vm";
		try {
			// 微信参数验证
			velocityContext.put("weixinDto", weixinDto);
			validateBargainDtoParam(weixinDto);
			// 活动参数验证
			WxActShaketicketHome shaketicket = homeService.queryById(actId);
			if (!jwid.equals(shaketicket.getJwid())) {
				throw new ShaketicketHomeException(ShaketicketHomeExceptionEnum.DATA_NOT_EXIST_ERROR,"活动不属于该微信公众号");
			}			
			if("0".equals(shaketicket.getActiveFlag())){
				throw new ShaketicketHomeException(ShaketicketHomeExceptionEnum.ACT_BARGAIN_END,"活动未激活！");
			}
			//update-begin--Author:zhangweijian Date:20181220 for：判断是否跳广告页
			// 判断是否设置广告
			if("1".equals(shaketicket.getIsSetAdv())&&adv==null){
				viewName = "shaketicket/"+shaketicket.getTemplate()+"/vm/advert.vm";
			}else{
				// 活动模板选择
				if (StringUtils.isNotEmpty(shaketicket.getTemplate())) {
					viewName = "shaketicket/"+shaketicket.getTemplate()+"/vm/index.vm";
				}
			}
			//update-end--Author:zhangweijian Date:20181220 for：判断是否跳广告页
			// 分享参数，底部广告
			velocityContext.put("nonceStr", WeiXinHttpUtil.nonceStr);
			velocityContext.put("timestamp", WeiXinHttpUtil.timestamp);
			String Hdurl = shaketicket.getHdurl().replace("${domain}",domain);
			velocityContext.put("hdUrl",Hdurl); //获取分享URL
			velocityContext.put("appId", appid);
			velocityContext.put("signature",WeiXinHttpUtil.getRedisSignature(request, jwid));
			velocityContext.put("huodong_bottom_copyright", baseApiSystemService.getHuodongLogoBottomCopyright(shaketicket.getCreateBy()));
			// 活动信息验证
			velocityContext.put("shaketicket", shaketicket);
			validateActDate(shaketicket);
			//获取参与人信息
			Date currDate = new Date();
			WxActShaketicketRegistration registration=wxActShaketicketRegistrationService.queryByActIdAndOpenid(actId,openid);
			if(registration==null){
				registration=new WxActShaketicketRegistration();
				registration.setActId(actId);
				registration.setOpenid(openid);
				//装载微信头像、昵称
				JSONObject userobj = WeiXinHttpUtil.getGzUserInfo(weixinDto.getOpenid(), weixinDto.getJwid());
				if(userobj!=null&& userobj.containsKey("nickname")){
					registration.setNickname(EmojiFilter.filterEmoji(userobj.getString("nickname")));
					registration.setHeadimg(userobj.getString("headimgurl"));
				}else{
					registration.setNickname("匿名");
					registration.setHeadimg("http://static.h5huodong.com/upload/common/defaultHeadImg.png");
				}
				//update-begin--Author:zhangweijian  Date: 20181022 for：老数据处理
				// 老数据处理
				Map<String, Integer> countMap = recordService.getRecordCountByActIdAndOpenid(weixinDto.getActId(),weixinDto.getOpenid(), currDate);		
				registration.setShakeCount(((Number)countMap.get("count")).intValue());
				registration.setDayShakeNum(((Number)countMap.get("countday")).intValue());
				registration.setLastShakeDate(currDate);
				if(((Number)countMap.get("wincount")).intValue()>0){
					registration.setAwardStatus("1");
				}else{
					registration.setAwardStatus("0");
				}
				//update-end--Author:zhangweijian  Date: 20181022 for：老数据处理
				registration.setCreateTime(currDate);
				wxActShaketicketRegistrationService.doAdd(registration);
			}
			// 重置每天的摇一摇次数
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			if(!sd.format(new Date()).equals(sd.format(registration.getLastShakeDate()))){
				registration.setDayShakeNum(0);
				registration.setLastShakeDate(new Date());
				wxActShaketicketRegistrationService.doEdit(registration);
			}
			// 根据活动id，访问人openid查询抽奖人抽奖次数和每日抽奖次数
			int count= registration.getShakeCount();
			int countday=  registration.getDayShakeNum();
			String url = request.getRequestURL() + "?"+ request.getQueryString();
			if (url.indexOf("#") != -1) {
				url = url.substring(0, url.indexOf("#"));
			}
			// 判断是否分享得额外次数
			if("0".equals(shaketicket.getExtraLuckyDraw())){
				// 判断当前是否分享 shareFlag=0：‘可得一次额外机会’
				List<WxActShaketicketShareRecord> shareRecord=wxActShaketicketShareRecordService.queryByActIdAndOpenid(actId,openid,sd.format(new Date()));
				if(shareRecord.size()>0){
					velocityContext.put("shareFlag","0");
				}else{
					velocityContext.put("shareFlag","1");
				}
			}else{
				velocityContext.put("shareFlag","1");
			}
			//判断为关注是否能参加：‘1’:未关注不能参加
			if("1".equals(shaketicket.getFoucsUserCanJoin())){
				String qrcodeUrl = baseApiJwidService.getQrcodeUrl(weixinDto.getJwid());
				velocityContext.put("qrcodeUrl", qrcodeUrl);
			}
			velocityContext.put("count",count);//累计抽奖次数
			velocityContext.put("countday",countday);//每日抽奖次数
		} catch (ShaketicketHomeException e) {
			e.printStackTrace();
			LOG.error("toIndex error:{}", e.getMessage());
			if(e.getDefineCode().equals(ShaketicketHomeExceptionEnum.ACT_BARGAIN_NO_START.getErrCode())){
				velocityContext.put("act_Status", "noStart");
				velocityContext.put("act_Status_Msg", "活动未开始");
			}else if(e.getDefineCode().equals(ShaketicketHomeExceptionEnum.ACT_BARGAIN_END.getErrCode())){
				velocityContext.put("act_Status", "isEnd");
				velocityContext.put("act_Status_Msg", "活动已结束");
			}else{
				velocityContext.put("errMsg",e.getMessage());
				viewName= "system/vm/error.vm";
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("toIndex error:{}", e);
			velocityContext.put("errCode",ShaketicketHomeExceptionEnum.SYS_ERROR.getErrCode());
			velocityContext.put("errMsg",ShaketicketHomeExceptionEnum.SYS_ERROR.getErrChineseMsg());
			viewName= "system/vm/error.vm";
		}
		ViewVelocity.view(request,response, viewName, velocityContext);
	}
	//update-end--Author:zhangweijian Date:20181012 for：增加了注册逻辑
	
	/**
	 * 摇奖
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shakeTicket", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public AjaxJson shakeTicket(@ModelAttribute WeixinDto weixinDto,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		LOG.info("shakeTicket parameter WeixinDto={}.",new Object[] { weixinDto });
		// 装载微信所需参数
		String actId = weixinDto.getActId();
		String openid = weixinDto.getOpenid();
		try {
			// 微信参数验证
			validateBargainDtoParam(weixinDto);
			// 获取活动信息
			WxActShaketicketHome shaketicket = homeService.queryById(actId);
			// 获取用户的关注状态
			WeixinUserUtil.setWeixinDto(weixinDto,null);
			if("1".equals(shaketicket.getFoucsUserCanJoin())){	
				//未关注
				 if(!"1".equals(weixinDto.getSubscribe())){
					 j.setSuccess(false);
						j.setObj("isNotFoucs");
						return j;
				 }
			}
			//TODO 暂时对手机号未做限制
			/*if("1".equals(shaketicket.getBindingMobileCanJoin())){//如果活动设置了需要绑定手机号才能参加				
				// 获取绑定手机号
				String bindPhone = getBindPhone(weixinDto.getOpenid(),jwid);
				// 判断是否绑定了手机号
				if (StringUtils.isEmpty(bindPhone)) {
					j.setSuccess(false);
					j.setObj("isNotBind");
					return j;
				}
			}*/
			// 获取参与人信息
			WxActShaketicketRegistration registration=wxActShaketicketRegistrationService.queryByActIdAndOpenid(actId,openid);
			//生成用户的抽奖记录
			WxActShaketicketRecord shaketicketRecord = new WxActShaketicketRecord();
			shaketicketRecord.setDrawStatus("0");//初始化为未中奖，‘0’为未中奖，‘1’为中奖
			//为用户抽取活动奖品，得到有剩余的活动奖品
			List<WxActShaketicketAward> awards = awardService.queryRemainAwardsByActId(actId);
			List<WxActShaketicketAward> newAwards = new ArrayList<WxActShaketicketAward>();
			//update-begin--Author:zhangweijian  Date: 20181115 for：抽奖逻辑修改
			if(("0".equals(shaketicket.getWinCanJoin())||("0".equals(registration.getAwardStatus())))){
				//得到各奖品的概率列表
				List<Double> orignalRates = new ArrayList<Double>(awards.size());
				for (WxActShaketicketAward award : awards) {
					Integer remainNum = award.getRemainNum();
					Double probability = award.getProbability();
					if (remainNum==null||remainNum <= 0) {//剩余数量为零，需使它不能被抽到
						probability = new Double(0);
					}
					if(probability==null){
						probability = new Double(0);
					}
					if(probability>0){
						orignalRates.add(probability);
						newAwards.add(award);
					}
				}
				//根据概率产生奖品
				int index = LotteryUtil.lottery(orignalRates);					
				if (index>=0) {
					WxActShaketicketAward award	= newAwards.get(index);
					shaketicketRecord.setAwardId(award.getId());
					shaketicketRecord.setDrawStatus("1");
					//生成兑奖码
					String awardCode="";
					for(int i=0;i<3;i++){
						awardCode=getCoupon();
						//判断新生成的兑奖码是否存在
						WxActShaketicketRecord codeRecord=recordService.queryByActIdAndawardCode(weixinDto.getActId(),awardCode);
						if(codeRecord==null){
							break;
						}
						if(i==2){
							throw new ShaketicketHomeException(ShaketicketHomeExceptionEnum.SYS_ERROR);
						}
					}
					shaketicketRecord.setAwardCode(awardCode);
					//修改用户中奖状态
					registration.setAwardStatus("1");
				}
				//update-end--Author:zhangweijian  Date: 20181115 for：抽奖逻辑修改
			}
			WxActShaketicketAward award = recordService.creatRecordAndReturnAward(shaketicketRecord,weixinDto,registration);
			j.setSuccess(true);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("shaketicketRecord",shaketicketRecord);
			map.put("shaketicketAward",award);
			j.setAttributes(map);	
		} catch (ShaketicketHomeException e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg(e.getMessage());
			LOG.error("bargain error:{}", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("抽奖失败!");
			LOG.error("bargain error:{}", e.getMessage());
		}
		return j;
	}
	
	//update-begin--Author:zhangweijian  Date: 20181022 for：生成兑奖码
	/**
	 * @功能：随机生成兑奖码
	 * @return
	 */
	private synchronized static String getCoupon(){
		char ch[]=new char[]{'0','1','2','3','4','5','6','7','8','9',
							'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
							'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
							};
		Random rand = new Random();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<20;i++){
			sb.append(ch[rand.nextInt(62)]);
		}
		return sb.toString();
	}
	//update-end--Author:zhangweijian  Date: 20181022 for：生成兑奖码
	
	/**
	 * 我的卡券
	 * 
	 * @return
	 */
	    @RequestMapping(value = "/getMyAwards", method = { RequestMethod.GET,
			RequestMethod.POST })
		@ResponseBody
		public AjaxJson getMyAwards(@ModelAttribute WeixinDto weixinDto,
					HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		LOG.info("getMyAwards parameter WeixinDto={}.",
				new Object[] { weixinDto });
		try {
			// 我的中奖记录
/*			List<WxActShaketicketRecord> recordList = recordService.queryMyAwardsRecordByOpenidAndActid(weixinDto.getOpenid(), weixinDto.getActId());
			//卡券参数
			Map<String,String> dataMap = new HashMap<String, String>();
			populationMap(dataMap,recordList.get(0),weixinDto.getJwid());
			
			dataMap.put("record_id", recordList.get(0).getId());
			j.setObj(dataMap);*/
			// ====================================================================================================
			WxActShaketicketRecord wxActShaketicketRecord = recordService.queryById(request.getParameter("id"));
			Map<String,String> dataMap = new HashMap<String, String>();
			populationMap(dataMap,wxActShaketicketRecord,weixinDto.getJwid());
			dataMap.put("record_id", wxActShaketicketRecord.getId());
			j.setObj(dataMap);
			//j.setObj(recordList.get(0));
			j.setSuccess(true);
		} catch (ShaketicketHomeException e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg(e.getMessage());
			LOG.error("bargain error:{}", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("获取失败!");
			LOG.error("bargain error:{}", e.getMessage());
		}
		return j;
	}
		private void populationMap(Map<String,String> dataMap,WxActShaketicketRecord record,String jwid){
			String api_ticket = BaseApiJwidService.queryTicketByJwid(jwid);
			dataMap.put("api_ticket", api_ticket);
			System.out.println("api_ticket------->"+api_ticket);
			dataMap.put("timestamp", WeiXinHttpUtil.timestamp);
			System.out.println("timestamp------->"+dataMap.get("timestamp"));
			dataMap.put("nonce_str", WeiXinHttpUtil.nonceStr);
			System.out.println("nonce_str------->"+dataMap.get("nonce_str"));
			dataMap.put("card_id", record.getCardId());
			System.out.println("card_id------->"+dataMap.get("card_id"));
			dataMap.put("openid", record.getOpenid());
			System.out.println("openid------->"+dataMap.get("openid"));
//			dataMap.put("record_id", record.getId());
//			System.out.println("record_id------->"+dataMap.get("record_id"));
			signatureDataMap(dataMap);
		}
		
		private void signatureDataMap(Map<String,String> dataMap){
			List<String> list = new ArrayList<String>();
			for(Map.Entry<String,String> entry : dataMap.entrySet()){
				list.add(entry.getValue());
			}
			Collections.sort(list);
			String signatureStr = "";
			for(String str:list){
				if(StringUtils.isNotEmpty(str)){
					signatureStr+=str;
				}
			}
			String signature = DigestUtils.shaHex(signatureStr);
			System.out.println("signatureStr------->"+signatureStr);
			System.out.println("signature------->"+signature);
			dataMap.put("signature", signature);
		}
	/**
	 * 跳转到我的奖品
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toMyAwardsRecordList", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void toMyAwardsRecordList(@ModelAttribute WeixinDto weixinDto,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("toMyAwardsRecordList parameter WeixinDto={}.",
				new Object[] { weixinDto });
		// 装载微信所需参数
		String jwid = weixinDto.getJwid();
		String actId = weixinDto.getActId();
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "shaketicket/default/vm/myprizes.vm";
		try {
			// 参数验证
			validateBargainDtoParam(weixinDto);
			// 获取活动信息
			WxActShaketicketHome shaketicket = homeService
					.queryById(actId);
			//validateActDate(shaketicket);
			velocityContext.put("weixinDto", weixinDto);
			velocityContext.put("shaketicket", shaketicket);
			//update-begin--Author:zhangweijian Date:20181114 for：奖品名字长度限制
			viewName = "shaketicket/"+shaketicket.getTemplate()+"/vm/myprizes.vm";
			// 参与总人数
			int joinCount=wxActShaketicketRegistrationService.countByActId(actId);
			velocityContext.put("joinCount", joinCount);
			// 我的中奖记录
			List<WxActShaketicketRecord> recordList = recordService.queryMyAwardsRecordByOpenidAndActid(weixinDto.getOpenid(), weixinDto.getActId());
			if(recordList.size()>0){
				for(WxActShaketicketRecord record:recordList){
					if(!StringUtil.isEmpty(record.getAwardsName())&&record.getAwardsName().length()>8){
						record.setAwardsName(record.getAwardsName().substring(0,8)+"...");
					}
				}
			}
			//update-end--Author:zhangweijian Date:20181114 for：奖品名字长度限制
			velocityContext.put("recordList", recordList);
			String url = request.getRequestURL() + "?"
					+ request.getQueryString();
			if (url.indexOf("#") != -1) {
				url = url.substring(0, url.indexOf("#"));
			}
			System.out.println("--------------当前访问PageUrl---------------："
					+ url);
			velocityContext.put("nonceStr", WeiXinHttpUtil.nonceStr);
			velocityContext.put("timestamp", WeiXinHttpUtil.timestamp);
			String Hdurl = shaketicket.getHdurl().replace("${domain}",domain);
			velocityContext.put("hdUrl",Hdurl); //获取分享URL
			velocityContext.put("signature",WeiXinHttpUtil.getRedisSignature(request, jwid));
			velocityContext.put("appId", weixinDto.getAppid());
			//update-begin--Author:zhangweijian  Date: 20180314 for：底部logo修改
			velocityContext.put("huodong_bottom_copyright", baseApiSystemService.getHuodongLogoBottomCopyright(shaketicket.getCreateBy()));
			//update-end--Author:zhangweijian  Date: 20180314 for：底部logo修改
		} catch (ShaketicketHomeException e) {
			e.printStackTrace();
			LOG.error("toMyAwardsRecordList error:{}", e.getMessage());
			velocityContext.put("errCode", e.getDefineCode());
			velocityContext.put("errMsg", e.getMessage());
			if(e.getDefineCode().equals("02007")){
				viewName= "system/vm/before.vm";
			}else if(e.getDefineCode().equals("02008")){
				viewName= "system/vm/over.vm";
			}else{
				viewName= "system/vm/error.vm";
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("toMyAwardsRecordList error:{}", e);
			velocityContext.put("errCode",
					ShaketicketHomeExceptionEnum.SYS_ERROR.getErrCode());
			velocityContext.put("errMsg",
					ShaketicketHomeExceptionEnum.SYS_ERROR.getErrChineseMsg());
			viewName= "system/vm/error.vm";
		}
		ViewVelocity.view(request,response, viewName, velocityContext);
	}
	
	/**
	 * 卡券发放回调
	 * @return
	 */
	@RequestMapping(value = "/addCardCallback",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson addCardCallback(@ModelAttribute WxActShaketicketRecord record,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		LOG.info("addCardCallback parameter wxActShakecouponRecord={}.", new Object[]{record});
		try {
			//参数验证
			if(StringUtils.isEmpty(record.getId())){
				 throw new ShaketicketHomeException(ShaketicketHomeExceptionEnum.ARGUMENT_ERROR,"获奖记录ID不能为空");
			 }
			record.setReceiveStatus("1");//已领取
			record.setReceiveTime(new Date());
			recordService.doEdit(record);
		} catch (ShaketicketHomeException e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg(e.getMessage());
			LOG.error("addCardCallback error:{}", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("优惠券兑换回调失败");
			LOG.error("addCardCallback error:{}", e.getMessage());
		}
		return j;
	}
	
	/**
	 * @功能：活动参数验证
	 * @param weixinDto
	 */
	private void validateBargainDtoParam(WeixinDto weixinDto) {
		if (StringUtils.isEmpty(weixinDto.getActId())) {
			throw new ShaketicketHomeException(
					ShaketicketHomeExceptionEnum.ARGUMENT_ERROR, "活动ID不能为空");
		}
		if (StringUtils.isEmpty(weixinDto.getOpenid())) {
			throw new ShaketicketHomeException(
					ShaketicketHomeExceptionEnum.ARGUMENT_ERROR, "参与人openid不能为空");
		}
		if (StringUtils.isEmpty(weixinDto.getJwid())) {
			throw new ShaketicketHomeException(
					ShaketicketHomeExceptionEnum.ARGUMENT_ERROR, "微信原始id不能为空");
		}
	}
	
	@SuppressWarnings("unused")
	private String getBindPhone(String openid,String jwid) {
		String bindPhine = "";
		try {
			JSONObject jsonObj = WeiXinHttpUtil.getGzUserInfo(openid, jwid);
			LOG.info("getBindPhone json{}.", new Object[] { jsonObj });
			if (jsonObj.containsKey("bindPhoneStatus")) {
				if ("Y".equals(jsonObj.getString("bindPhoneStatus"))) {
					if (jsonObj.containsKey("phone")) {
						bindPhine = jsonObj.getString("phone");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bindPhine;
	}
	
	/**
	 * 
	 * @param weixinDto
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMyAwardsList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public AjaxJson queryMyAwardsList(@ModelAttribute WeixinDto weixinDto,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AjaxJson j=new AjaxJson();
		LOG.info("queryMyAwardsList parameter WeixinDto={}.",
				new Object[] { weixinDto });
		try {
			// 参数验证
			validateBargainDtoParam(weixinDto);
			// 我的中奖记录
			List<WxActShaketicketRecord> recordList = recordService.queryMyAwardsRecordByOpenidAndActid(weixinDto.getOpenid(), weixinDto.getActId());
			j.setObj(recordList);
			j.setSuccess(true);
		} catch (ShaketicketHomeException e) {
			e.printStackTrace();
			LOG.error("toMyAwardsRecordList error:{}", e.getMessage());
			j.setSuccess(false);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("toMyAwardsRecordList error:{}", e);
			j.setSuccess(false);
		}
		return j;
	}
	
	@RequestMapping(value = "/queryOneMyAwards", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public AjaxJson queryOneMyAwards(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AjaxJson j=new AjaxJson();
		try {
			String id = request.getParameter("id");
			// 我的中奖记录
			WxActShaketicketRecord record = recordService.queryById(id);
			j.setObj(record);
			j.setSuccess(true);
		} catch (ShaketicketHomeException e) {
			e.printStackTrace();
			LOG.error("toMyAwardsRecordList error:{}", e.getMessage());
			j.setSuccess(false);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("toMyAwardsRecordList error:{}", e);
			j.setSuccess(false);
		}
		return j;
	}
	@RequestMapping(value = "/updateMyAwards", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public AjaxJson updateMyAwards(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AjaxJson j=new AjaxJson();
		try {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String mobile = request.getParameter("mobile");
			
			// 我的中奖记录
			WxActShaketicketRecord record = recordService.queryById(id);
			record.setMobile(mobile);
			record.setRelName(name);
			record.setAddress(address);
			recordService.doEdit(record);
			j.setSuccess(true);
		} catch (ShaketicketHomeException e) {
			e.printStackTrace();
			LOG.error("toMyAwardsRecordList error:{}", e.getMessage());
			j.setSuccess(false);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("toMyAwardsRecordList error:{}", e);
			j.setSuccess(false);
		}
		return j;
	}
	@RequestMapping(value = "/toDetail", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public void toDetail(@ModelAttribute WeixinDto weixinDto,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("toIndex parameter WeixinDto={}.",
				new Object[] { weixinDto });
		String viewName = "shaketicket/default/vm/detail.vm";
		VelocityContext velocityContext=new VelocityContext();
		try {
			validateBargainDtoParam(weixinDto);
			// 获取活动信息
			WxActShaketicketHome shaketicket = homeService.queryById(weixinDto.getActId());
			validateActDate(shaketicket);
			velocityContext.put("shaketicket",shaketicket);
			velocityContext.put("weixinDto",weixinDto);
			velocityContext.put("nonceStr", WeiXinHttpUtil.nonceStr);
			velocityContext.put("timestamp", WeiXinHttpUtil.timestamp);
			String Hdurl = shaketicket.getHdurl().replace("${domain}",domain);
			velocityContext.put("hdUrl",Hdurl); //获取分享URL
			velocityContext.put("appId", weixinDto.getAppid());
			velocityContext.put("signature",WeiXinHttpUtil.getRedisSignature(request, weixinDto.getJwid()));
			//update-begin--Author:zhangweijian  Date: 20180314 for：底部logo修改
			velocityContext.put("huodong_bottom_copyright", baseApiSystemService.getHuodongLogoBottomCopyright(shaketicket.getCreateBy()));
			//update-end--Author:zhangweijian  Date: 20180314 for：底部logo修改
		} catch (ShaketicketHomeException e) {
			e.printStackTrace();
			LOG.error("toIndex error:{}", e.getMessage());
			velocityContext.put("errCode", e.getDefineCode());
			velocityContext.put("errMsg", e.getMessage());
			if(e.getDefineCode().equals("02007")){
				viewName= "system/vm/before.vm";
			}else if(e.getDefineCode().equals("02008")){
				viewName= "system/vm/over.vm";
			}else{
				viewName= "system/vm/error.vm";
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("toIndex error:{}", e);
			velocityContext.put("errCode",
					ShaketicketHomeExceptionEnum.SYS_ERROR.getErrCode());
			velocityContext.put("errMsg",
					ShaketicketHomeExceptionEnum.SYS_ERROR.getErrChineseMsg());
			viewName= "system/vm/error.vm";
		}
		ViewVelocity.view(request,response, viewName, velocityContext);
	}
	private void validateActDate(WxActShaketicketHome shaketicket) {
		Date date = new Date();
		if (shaketicket == null) {
			throw new ShaketicketHomeException(
					ShaketicketHomeExceptionEnum.DATA_NOT_EXIST_ERROR, "活动不存在");
		}
		if(shaketicket.getBeginTime()!=null){
			if (date.before(shaketicket.getBeginTime())) {
				throw new ShaketicketHomeException(
						ShaketicketHomeExceptionEnum.ACT_BARGAIN_NO_START, "活动未开始");
			}
		}
		if(shaketicket.getEndTime()!=null){
			if (date.after(shaketicket.getEndTime())) {
				throw new ShaketicketHomeException(ShaketicketHomeExceptionEnum.ACT_BARGAIN_END,
						"活动已结束");
			}
		}
	}

	
	/**
	 * @功能：分享插入记录
	 * @param weixinDto
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fxCallback", method = { RequestMethod.GET,RequestMethod.POST })
	public AjaxJson fxCallback(@ModelAttribute WeixinDto weixinDto,HttpServletRequest request, HttpServletResponse response){
		AjaxJson j=new AjaxJson();
		try {
			String actId = request.getParameter("actId");
			String openid = request.getParameter("openid");
			String type = request.getParameter("type");
			//插入分享记录信息
 			WxActShaketicketShareRecord shareRecords=new WxActShaketicketShareRecord();
			shareRecords.setActId(actId);
			shareRecords.setOpenid(openid);
			shareRecords.setShareDate(new Date());
			shareRecords.setType(type);
			shareRecords.setCreateTime(new Date());
			wxActShaketicketShareRecordService.doAdd(shareRecords);
			// 返回信息
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}
}
