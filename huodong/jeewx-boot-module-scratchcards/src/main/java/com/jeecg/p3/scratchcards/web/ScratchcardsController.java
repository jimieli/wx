package com.jeecg.p3.scratchcards.web;

import com.alibaba.fastjson.JSONObject;
import com.jeecg.p3.baseApi.service.BaseApiJwidService;
import com.jeecg.p3.baseApi.service.BaseApiSystemService;
import com.jeecg.p3.baseApi.util.EmojiFilter;
import com.jeecg.p3.baseApi.util.WeixinUserUtil;
import com.jeecg.p3.scratchcards.def.ScratchcardsProperties;
import com.jeecg.p3.scratchcards.entity.*;
import com.jeecg.p3.scratchcards.exception.ScratchcardsException;
import com.jeecg.p3.scratchcards.exception.ScratchcardsExceptionEnum;
import com.jeecg.p3.scratchcards.service.*;
import com.jeecg.p3.scratchcards.util.LotteryUtil;
import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.base.vo.WeixinDto;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.util.WeiXinHttpUtil;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.p3.core.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/scratchcards")
public class ScratchcardsController extends BaseController {
	public final static Logger LOG = LoggerFactory.getLogger(ScratchcardsController.class);
	@Autowired
	private WxActScratchcardsRelationService wxActScratchcardsRelationService;
	@Autowired
	private WxActScratchcardsService wxActScratchcardsService;
	@Autowired
	private WxActScratchcardsRegistrationService wxActScratchcardsRegistrationService;
	@Autowired
	private WxActScratchcardsRecordService wxActScratchcardsRecordService;
	@Autowired
	private WxActScratchcardsAwardsService wxActScratchcardsAwardsService;
	@Autowired
	private BaseApiJwidService baseApiJwidService;
	@Autowired
	private BaseApiSystemService baseApiSystemService;
	@Autowired
	private WxActScratchcardsShareRecordService wxActScratchcardsShareRecordService;
	private static String domain = ScratchcardsProperties.domain;
	/**
	 * 跳转到刮刮乐首页页面
	 * @param weixinDto
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/toIndex", method = { RequestMethod.GET,RequestMethod.POST })
	public void toScratchcards(@ModelAttribute WeixinDto weixinDto,HttpServletRequest request, HttpServletResponse response)throws Exception {
		LOG.info("toScratchcards parameter WeixinDto={}.",
				new Object[] { weixinDto });
		long start = System.currentTimeMillis();
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "scratchcards/vm/ggl.vm";
		try {
			// 验证参数
			validateWeixinDtoParam(weixinDto);
			String jwid = weixinDto.getJwid();
			String openid = weixinDto.getOpenid();
			String actId = weixinDto.getActId();
			String appid = weixinDto.getAppid();
			//获取活动信息
			WxActScratchcards wxActScratchcards = wxActScratchcardsService.queryById(actId);
			if(wxActScratchcards!=null && StringUtils.isNotEmpty(wxActScratchcards.getTemplateCode())){
				viewName = "scratchcards/vm/"+wxActScratchcards.getTemplateCode()+"/ggl.vm";
			}
			if (wxActScratchcards == null) {
				throw new ScratchcardsException(ScratchcardsExceptionEnum.DATA_NOT_EXIST_ERROR, "活动不存在");
			}
			long date = new Date().getTime();
			if(date<wxActScratchcards.getStarttime().getTime()){
				//throw new ScratchcardsException(ScratchcardsExceptionEnum.ACT_BARGAIN_NO_START, "活动还未开始");
				
				velocityContext.put("act_Status", "false");
				velocityContext.put("act_Status_Msg", "活动未开始");
			}
			if(date>wxActScratchcards.getEndtime().getTime()){
				//update-begin--Author:zhangweijian  Date: 20180316 for：活动开始结束页面
				//throw new ScratchcardsException(ScratchcardsExceptionEnum.ACT_BARGAIN_FINISH, "活动已结束");
				//update-end--Author:zhangweijian  Date: 20180316 for：活动开始结束页面
				velocityContext.put("act_Status", "false");
				velocityContext.put("act_Status_Msg", "活动已结束");
			}
			//--update-begin---author:huangqingquan---date:20161125-----for:是否关注可参加---------------
			if("1".equals(wxActScratchcards.getFoucsUserCanJoin())){//如果活动设置了需要关注用户才能参加	
				velocityContext.put("qrcodeUrl", baseApiJwidService.getQrcodeUrl(jwid));
				JSONObject userobj = WeiXinHttpUtil.getGzUserInfo(weixinDto.getOpenid(),weixinDto.getJwid());
				if(userobj!=null&&userobj.containsKey("subscribe")){
					if(!"1".equals(userobj.getString("subscribe"))){
						velocityContext.put("subscribeFlage", "1");
					}
				}else{
					velocityContext.put("subscribeFlage", "1");
				}
			}
			
			//初始化用户信息
            WeixinUserUtil.setWeixinDto(weixinDto,null);
			//--update-end---author:huangqingquan---date:20161125-----for:是否关注可参加---------------
			//--update-begin---author:sunkai---date:20180911-----for:是否中奖可继续参与---------------
			//是否中奖可继续参与 1不可继续参与 0可继续参与
			String prizeStatus = wxActScratchcards.getPrizeStatus();
			if(prizeStatus.equals("1")){
				List<WxActScratchcardsRecord> recordList = wxActScratchcardsRecordService.queryMyList(weixinDto.getOpenid(), actId);
				if(null!=recordList&&recordList.size()>0){
					velocityContext.put("prizeStatus", prizeStatus);
				}
			}
			//--update-end---author:sunkai---date:20180911-----for:是否中奖可继续参与---------------
			// 查出所有奖品的数量
			List<WxActScratchcardsRelation> list = wxActScratchcardsRelationService.queryPrizeAndAward(actId);
			
			Integer count = null;// 总次数
			Integer awardsNum = null;// 剩余次数
			Integer remainNumDay = null;// 每天剩余次数
			WxActScratchcardsRegistration registration = wxActScratchcardsRegistrationService.getOpenid(openid, actId);
			
			if(registration == null){// 用户没有注册过的情况
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String upDate = sdf.format(new Date());
				registration = new WxActScratchcardsRegistration();
				registration.setActId(actId);
				registration.setAwardsNum(0);
				registration.setAwardsStatus("1");
				registration.setJwid(jwid);
				registration.setCreateTime(new Date());
				registration.setNickname(EmojiFilter.filterEmoji(weixinDto.getNickname()));
				registration.setOpenid(openid);
				registration.setRemainNumDay(0);
				registration.setUpdateTime(upDate);
				wxActScratchcardsRegistrationService.doAdd(registration);
				registration = wxActScratchcardsRegistrationService.getOpenid(openid, actId);
			}
			
			count = wxActScratchcards.getCount();// 总数量
			if (registration == null) {// 第一次进活动显示所有次数
				awardsNum = count;// 个人可以刮刮乐的剩余次数
				remainNumDay = wxActScratchcards.getNumPerDay();// 每天的剩余次数
			}
			if (registration != null) {
				SimpleDateFormat sb = new SimpleDateFormat("yyyyMMdd");
				String update = sb.format(new Date());
				if(update.equals(registration.getUpdateTime())){
					remainNumDay = wxActScratchcards.getNumPerDay()-registration.getRemainNumDay();// 每天的剩余次数
					velocityContext.put("lotterytoday", "1");
				}else{				
					remainNumDay = wxActScratchcards.getNumPerDay();
					velocityContext.put("lotterytoday", "0");
				}
			    //update-begin--Author:sunkai  Date:20180906 for：总次数为零默认为无限制--------------------				
				if(count != 0){
					awardsNum = count-registration.getAwardsNum();
				}else if(count == 0){
					awardsNum = 0;
				}
				//update-end--Author:sunkai  Date:20180906 for：总次数为零默认为无限制--------------------					
			}
			if(awardsNum<0){
				awardsNum = 0;
			}
			if(remainNumDay<0){
				remainNumDay = 0;
			}
			if(count > 0 && count<awardsNum){
				awardsNum = count;
			}
			if(count > 0 && awardsNum<remainNumDay){
				remainNumDay = awardsNum;
			}
			//根据活动ID查询奖品，计算中奖概率
			List<WxActScratchcardsRelation> otherPrizeList = wxActScratchcardsRelationService.queryByActId(actId);
			WxActScratchcardsRelation relation = calcOtherPrizePercentage(otherPrizeList);
			if(relation!=null&&StringUtils.isNotEmpty(relation.getId())){
				WxActScratchcardsAwards wxActScratchcardsAwards = wxActScratchcardsAwardsService.queryById(relation.getAwardId());
				if(wxActScratchcardsAwards!=null){
					String awardsName = wxActScratchcardsAwards.getAwardsName();
					velocityContext.put("awardsName", awardsName);
				}
				velocityContext.put("relationId", relation.getId());
			}
			
			//acthor:sunkai--date:2018-10-17--for:分享得抽奖次数--------
			//判断是否分享可得次数
			if("1".equals(wxActScratchcards.getShareStatus())){
				velocityContext.put("paseNum", registration.getRemainNumDay());
				velocityContext.put("numPerDay", wxActScratchcards.getNumPerDay());
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				//查询当日是否分享过
				WxActScratchcardsShareRecord share = new WxActScratchcardsShareRecord();
				share.setActId(actId);
				share.setOpenid(openid);
				share.setShareDate(f.parse(f.format(new Date())));
				//查询该用户当天是否已经分享过
				PageQuery<WxActScratchcardsShareRecord> pageQuery = new PageQuery<WxActScratchcardsShareRecord>();
				pageQuery.setPageNo(0);
				pageQuery.setPageSize(10);
				pageQuery.setQuery(share);
				List<WxActScratchcardsShareRecord> shareList = wxActScratchcardsShareRecordService.queryPageList1(pageQuery);
				if(null != shareList && shareList.size()>0){
					velocityContext.put("ifshare", "1");
				}else{
					velocityContext.put("ifshare", "0");
				}
			}
			//acthor:sunkai--date:2018-10-17--for:分享得抽奖次数--------
			
			velocityContext.put("count", count);
			velocityContext.put("awardsNum", awardsNum);
			velocityContext.put("remainNumDay", remainNumDay);
			velocityContext.put("list", list);
			velocityContext.put("scratchcards", wxActScratchcards);
			velocityContext.put("weixinDto", weixinDto);
			String Hdurl = wxActScratchcards.getHdurl().replace("${domain}",domain);
			velocityContext.put("hdUrl",Hdurl); //获取分享URL
			velocityContext.put("appId", appid);// 必填，公众号的唯一标识
			velocityContext.put("nonceStr", WeiXinHttpUtil.nonceStr);// 必填，生成签名的随机串
			velocityContext.put("timestamp", WeiXinHttpUtil.timestamp);// 必填，生成签名的时间戳
			velocityContext.put("signature",WeiXinHttpUtil.getRedisSignature(request, jwid));// 必填，签名，见附录1
			//update-begin--Author:zhangweijian  Date: 20180314 for：底部logo修改
			velocityContext.put("huodong_bottom_copyright", baseApiSystemService.getHuodongLogoBottomCopyright(wxActScratchcards.getCreateBy()));
			//update-end--Author:zhangweijian  Date: 20180314 for：底部logo修改
			ViewVelocity.view(request, response, viewName, velocityContext);
		} catch (ScratchcardsException e) {
			e.printStackTrace();
			LOG.error("toIndex error:{}", e.getMessage());
			//update-begin--Author:zhangweijian  Date: 20180316 for：活动开始结束页面
			if(e.getDefineCode().equals(ScratchcardsExceptionEnum.ACT_BARGAIN_NO_START.getErrCode())){
				velocityContext.put("act_Status", "false");
				velocityContext.put("act_Status_Msg", "活动未开始");
			}else if(e.getDefineCode().equals(ScratchcardsExceptionEnum.ACT_BARGAIN_FINISH.getErrCode())){
				velocityContext.put("act_Status", "false");
				velocityContext.put("act_Status_Msg", "活动已结束");
			}else{
				viewName= "system/vm/error.vm";
			}
			//update-end--Author:zhangweijian  Date: 20180316 for：活动开始结束页面
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("toIndex error:{}", e);
			velocityContext.put("errCode",ScratchcardsExceptionEnum.SYS_ERROR.getErrCode());
			velocityContext.put("errMsg",ScratchcardsExceptionEnum.SYS_ERROR.getErrChineseMsg());
			viewName= "system/vm/error.vm";
		}
		LOG.info("toIndex time={}ms.",new Object[] { System.currentTimeMillis() - start });
		ViewVelocity.view(request, response, viewName, velocityContext);
	}
	/**
	 * 错误页面
	 * @param errorCode
	 * @return
	 */
	private String chooseErrorPage(String errorCode){
		if(errorCode.equals("02007")){
			return "system/vm/before.vm";
		}else if(errorCode.equals("02008")){
			return "system/vm/over.vm";
		}else{
			return"system/vm/error.vm";
		}
	}
	/**
	 * 刮完请求后台
	 */
	@RequestMapping(value = "/toPrizePro", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public AjaxJson toPrizePro(@ModelAttribute WeixinDto weixinDto,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
		AjaxJson j = new AjaxJson();
		try {
			String actId = request.getParameter("actId");
			String jwid = request.getParameter("jwid");
			String relationId = request.getParameter("relationId");
			WxActScratchcards wxActScratchcards = wxActScratchcardsService.queryById(actId);
			if (wxActScratchcards == null) {
				throw new ScratchcardsException(ScratchcardsExceptionEnum.DATA_NOT_EXIST_ERROR, "活动不存在");
			}
			if (!jwid.equals(wxActScratchcards.getJwid())) {
				throw new ScratchcardsException(ScratchcardsExceptionEnum.DATA_NOT_EXIST_ERROR,"活动不属于该微信公众号");
			}
			//--update-begin---author:sunkai---date:20180911-----for:是否中奖可继续参与---------------
			//判断活动是否中奖可参加   0：中奖可继续参与 1:中奖不可参与
			String prizeStatus = wxActScratchcards.getPrizeStatus();
			if(prizeStatus.equals("1")){
				List<WxActScratchcardsRecord> recordList = wxActScratchcardsRecordService.queryMyList(weixinDto.getOpenid(), actId);
				if(null!=recordList&&recordList.size()>0){
					j.setSuccess(false);
					j.setObj("prizeStatus");
					return j;
				}
			}
			//--update-end---author:sunkai---date:20180911-----for:是否中奖可继续参与---------------
			j = wxActScratchcardsRegistrationService.prizeRecord(weixinDto, j,relationId);// 根据概率返回已用的信息
		} catch (ScratchcardsException e) {
			e.printStackTrace();
			j.setSuccess(false);
			if(e.getMessage().contains("今日抽奖次数已用完")){
				j.setObj("1");
			}else if(e.getMessage().contains("总抽奖次数已用完")){
				j.setObj("2");
			}
			j.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("系统异常！");
		}
		
		
		return j;
	}

	/**
	 * 用户领取奖品详细信息
	 * 
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveScratchCardsPrize", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public AjaxJson saveGoldEggPrize(@ModelAttribute WeixinDto weixinDto,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
		LOG.info("saveScratchCardsPrize parameter WeixinDto={}.",
				new Object[] { weixinDto });
		AjaxJson j = new AjaxJson();
		long start = System.currentTimeMillis();
		try {
			String mobile = request.getParameter("mobile");
			String username = request.getParameter("username");
			String address = request.getParameter("address");
			String recordId = request.getParameter("recordId");
			WxActScratchcardsRecord queryByCode = wxActScratchcardsRecordService.queryById(recordId);
			queryByCode.setPhone(mobile);
			queryByCode.setAddress(address);
			queryByCode.setRealname(username);
			wxActScratchcardsRecordService.doEdit(queryByCode);
		} catch (ScratchcardsException e) {
			e.printStackTrace();
			LOG.error("saveScratchCardsPrize error:{}", e.getMessage());
			j.setSuccess(false);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("saveScratchCardsPrize error:{}", e.getMessage());
			j.setSuccess(false);
		}
		LOG.info("saveScratchCardsPrize time={}ms.",
				new Object[] { System.currentTimeMillis() - start });
		return j;
	}

	/**
	 * 展示我的奖品记录
	 * 
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */

	@RequestMapping(value = "/toMyPrize", method = { RequestMethod.GET,RequestMethod.POST })
	public void toMyPrize(@ModelAttribute WeixinDto weixinDto,HttpServletRequest request, HttpServletResponse response)throws Exception {
		LOG.info("toMyPrize parameter WeixinDto={}.",new Object[] { weixinDto });
		long start = System.currentTimeMillis();
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "scratchcards/vm/prizename.vm";
		try {
			String openid = weixinDto.getOpenid();
			String actId = weixinDto.getActId();
			String code = request.getParameter("code");
			WxActScratchcards wxActScratchcards = wxActScratchcardsService.queryById(actId);
			List<WxActScratchcardsRecord> queryLists = wxActScratchcardsRecordService.queryMyList(openid, actId);
			List<WxActScratchcardsRecord> queryByCodes = new ArrayList<WxActScratchcardsRecord>();
			for (WxActScratchcardsRecord list : queryLists) {
				String codes = list.getCode();
				if (codes != null) {
					WxActScratchcardsRecord queryByCode = wxActScratchcardsRecordService.queryByCode(codes);
					queryByCodes.add(queryByCode);
				}
			}
			velocityContext.put("code", code);
			velocityContext.put("queryList", queryByCodes);
			velocityContext.put("scratchcards", wxActScratchcards);
			velocityContext.put("weixinDto", weixinDto);
			velocityContext.put("nonceStr", WeiXinHttpUtil.nonceStr);// 必填，生成签名的随机串
			velocityContext.put("timestamp", WeiXinHttpUtil.timestamp);// 必填，生成签名的时间戳
			velocityContext.put("signature",WeiXinHttpUtil.getRedisSignature(request, weixinDto.getJwid()));// 必填，签名，见附录1
			//update-begin--Author:zhangweijian  Date: 20180314 for：底部logo修改
			velocityContext.put("huodong_bottom_copyright", baseApiSystemService.getHuodongLogoBottomCopyright(wxActScratchcards.getCreateBy()));
			//update-end--Author:zhangweijian  Date: 20180314 for：底部logo修改
		} catch (ScratchcardsException e) {
			e.printStackTrace();
			LOG.error("toMyPrize error:{}", e.getMessage());
			velocityContext.put("errCode", e.getDefineCode());
			velocityContext.put("errMsg", e.getMessage());
			viewName=chooseErrorPage(e.getDefineCode());
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("toMyPrize error:{}", e);
			velocityContext.put("errCode",ScratchcardsExceptionEnum.SYS_ERROR.getErrCode());
			velocityContext.put("errMsg",ScratchcardsExceptionEnum.SYS_ERROR.getErrChineseMsg());
			viewName= "system/vm/error.vm";
		}
		LOG.info("toMyPrize time={}ms.",
				new Object[] { System.currentTimeMillis() - start });
		ViewVelocity.view(request, response, viewName, velocityContext);

	}

	/**
	 * 展示所有奖品记录
	 * 
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAllPrize", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void toAllPrize(@ModelAttribute WeixinDto weixinDto,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("toAllPrize parameter WeixinDto={}.",new Object[] { weixinDto });
		long start = System.currentTimeMillis();
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "scratchcards/vm/allprizename.vm";
		try {
			String actId = weixinDto.getActId();
			WxActScratchcards wxActScratchcards = wxActScratchcardsService.queryById(actId);
			List<WxActScratchcardsRecord> queryLists = wxActScratchcardsRecordService.queryList(actId);
			List<WxActScratchcardsRecord> queryByCodes = new ArrayList<WxActScratchcardsRecord>();
			for (WxActScratchcardsRecord list : queryLists) {
				String codes = list.getCode();
				if (codes != null) {
					WxActScratchcardsRecord queryByCode = wxActScratchcardsRecordService.queryByCode(codes);
					queryByCodes.add(queryByCode);
				}
			}
			velocityContext.put("queryList", queryByCodes);
			velocityContext.put("scratchcards", wxActScratchcards);
			velocityContext.put("weixinDto", weixinDto);
			velocityContext.put("nonceStr", WeiXinHttpUtil.nonceStr);// 必填，生成签名的随机串
			velocityContext.put("timestamp", WeiXinHttpUtil.timestamp);// 必填，生成签名的时间戳
			velocityContext.put("signature",WeiXinHttpUtil.getRedisSignature(request, weixinDto.getJwid()));// 必填，签名，见附录1
			//update-begin--Author:zhangweijian  Date: 20180314 for：底部logo修改
			velocityContext.put("huodong_bottom_copyright", baseApiSystemService.getHuodongLogoBottomCopyright(wxActScratchcards.getCreateBy()));
			//update-end--Author:zhangweijian  Date: 20180314 for：底部logo修改
		} catch (ScratchcardsException e) {
			e.printStackTrace();
			LOG.error("toAllPrize error:{}", e.getMessage());
			velocityContext.put("errCode", e.getDefineCode());
			velocityContext.put("errMsg", e.getMessage());
			viewName=chooseErrorPage(e.getDefineCode());
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("toAllPrize error:{}", e);
			velocityContext.put("errCode",ScratchcardsExceptionEnum.SYS_ERROR.getErrCode());
			velocityContext.put("errMsg",ScratchcardsExceptionEnum.SYS_ERROR.getErrChineseMsg());
			viewName= "system/vm/error.vm";
		}
		LOG.info("toAllPrize time={}ms.",
				new Object[] { System.currentTimeMillis() - start });
		ViewVelocity.view(request, response, viewName, velocityContext);

	}
	

	/**
	 * 更新用户信息，兑奖换奖品
	 * 
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/toUpdateMessage", method = { RequestMethod.GET,RequestMethod.POST })
	public AjaxJson toUpdateMessage(@ModelAttribute WeixinDto weixinDto,
			HttpServletRequest request, HttpServletResponse response) {
		LOG.info("toUpdateMessage parameter WeixinDto={}.",
				new Object[] { weixinDto });
		long start = System.currentTimeMillis();
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		try {
			WxActScratchcardsRecord queryByCode = wxActScratchcardsRecordService.queryById(id);
			String userAddress = null;
			String userName = null;
			String userMobile = null;
			if (queryByCode != null) {
				userAddress = queryByCode.getAddress();
				userName = queryByCode.getRealname();
				userMobile = queryByCode.getPhone();
			}
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("userName", userName);
			mm.put("userAddress", userAddress);
			mm.put("userMobile", userMobile);
			j.setAttributes(mm);
			j.setObj("iscode");
		} catch (ScratchcardsException e) {
			e.printStackTrace();
			LOG.error("toUpdateMessage error:{}", e.getMessage());
			j.setSuccess(false);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("toUpdateMessage error:{}", e.getMessage());
			j.setSuccess(false);
		}
		LOG.info("toUpdateMessage time={}ms.",
				new Object[] { System.currentTimeMillis() - start });
		return j;
	}

	private void validateWeixinDtoParam(WeixinDto weixinDto) {
		
		if (StringUtils.isEmpty(weixinDto.getActId())) {
			throw new ScratchcardsException(
					ScratchcardsExceptionEnum.ARGUMENT_ERROR, "活动ID不能为空");
		}
		if (StringUtils.isEmpty(weixinDto.getOpenid())) {
			throw new ScratchcardsException(
					ScratchcardsExceptionEnum.ARGUMENT_ERROR, "参与人openid不能为空");
		}
		if (StringUtils.isEmpty(weixinDto.getJwid())) {
			throw new ScratchcardsException(
					ScratchcardsExceptionEnum.ARGUMENT_ERROR, "微信ID不能为空");
		}
		
	}
	
	// 抽中奖品
	public WxActScratchcardsRelation calcOtherPrizePercentage(
			List<WxActScratchcardsRelation> otherPrizeList) {// 通过活动id得到表里面的所有集合传过去
		if (otherPrizeList == null || otherPrizeList.size() == 0) {
			return null;
		}
		// 得到各奖品的概率列表
		List<Double> orignalRates = new ArrayList<Double>(otherPrizeList.size());
		List<WxActScratchcardsRelation> newPrizeList = new ArrayList<WxActScratchcardsRelation>();
		for (int i = 0; i < otherPrizeList.size(); i++) {
			Integer remainNum = otherPrizeList.get(i).getRemainNum();
			BigDecimal probability = otherPrizeList.get(i).getProbability();
			if(remainNum==null){
				remainNum=0;
			}
			if (remainNum <= 0) {// 剩余数量为零，需使它不能被抽到
				probability = BigDecimal.ZERO;
			}
			if(probability.compareTo(BigDecimal.ZERO) > 0){
				newPrizeList.add(otherPrizeList.get(i));
				orignalRates.add(probability.doubleValue());
			}
		}
		int index = LotteryUtil.lottery(orignalRates);
		WxActScratchcardsRelation wxActGoldeneggsRelation = null;
		if (index >= 0) {
			wxActGoldeneggsRelation = newPrizeList.get(index);
		}
		if (index < 0) {
			wxActGoldeneggsRelation = null;
		}
		return wxActGoldeneggsRelation;
	}
	
	/**
	 * 根据奖项生成的图片
	 * @param request
	 * @param response
	 * @param relationId
	 * @throws ServletException
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/awardsImg", method = RequestMethod.GET)
	public void service(HttpServletRequest request, HttpServletResponse response,String relationId) throws ServletException, java.io.IOException {
		try {
			WxActScratchcardsRelation wxActScratchcardsRelation = wxActScratchcardsRelationService.queryById(relationId);
			// 声明一个图片对象:指定宽、高、图片类型。类型一般为TYPE_INT_RGB
			BufferedImage image = new BufferedImage(180, 40,BufferedImage.TYPE_INT_RGB);
			
			// 得到该对象的一个画笔
			Graphics g = image.getGraphics();
			g.fillRect(0, 0, 180, 40);
			g.setColor(Color.BLACK);
			g.setFont(new Font("微软雅黑", Font.BOLD, 18));
			g.drawString(wxActScratchcardsRelation.getAwardsName(), 50, 30);
			g.dispose();
			
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			OutputStream sos = response.getOutputStream();
			ImageIO.write(image, "jpg", sos);
			sos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加分享记录
	 */
	@RequestMapping(value="addShareNum", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public AjaxJson addShareNum(@ModelAttribute WeixinDto weixinDto,
			HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			//接收参数
			String actId = request.getParameter("actId");
			String openid = request.getParameter("openid");
			String type = request.getParameter("type");
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			//创建分享记录
			WxActScratchcardsShareRecord share = new WxActScratchcardsShareRecord();
			share.setActId(actId);
			share.setOpenid(openid);
			share.setShareDate(f.parse(f.format(new Date())));
			//查询该用户当天是否已经分享过，如果有，不再增加抽奖机会，返回0；没有则返回一，增加抽奖机会
			PageQuery<WxActScratchcardsShareRecord> pageQuery = new PageQuery<WxActScratchcardsShareRecord>();
			pageQuery.setPageNo(0);
			pageQuery.setPageSize(10);
			pageQuery.setQuery(share);
			List<WxActScratchcardsShareRecord> shareList = wxActScratchcardsShareRecordService.queryPageList1(pageQuery);
			if(null != shareList && shareList.size()>0){
				j.setObj("0");
			}else{
				j.setObj("1");
				//当日抽奖次数加1
				share.setType(type);
				share.setCreateTime(new Date());
				wxActScratchcardsShareRecordService.doAdd(share);
			}
			j.setSuccess(true);
			//根据recordId查询修改中奖纪录
		} catch (Exception e) {
			j.setSuccess(false);
			e.printStackTrace();
		}
		return j;
	}   
	
	/**
	 * 展示中奖记录
	 * 
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	@RequestMapping(value = "/toPrize", method = { RequestMethod.GET,
			RequestMethod.POST })
			public void toPrize(@ModelAttribute WeixinDto weixinDto,HttpServletRequest request, HttpServletResponse response)throws Exception {
		LOG.info("toPrize parameter WeixinDto={}.",new Object[] { weixinDto });
		long start = System.currentTimeMillis();
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "scratchcards/vm/newTemplet/prize.vm";
		try {
			String actId = weixinDto.getActId();
			String jwid = weixinDto.getJwid();
			WxActScratchcards wxActScratchcards = wxActScratchcardsService.queryById(actId);
			Integer joinNum=wxActScratchcardsRegistrationService.queryCountByActIdAndJwid(actId,jwid);
			List<WxActScratchcardsRecord> queryLists = wxActScratchcardsRecordService.queryList(actId);
			List<WxActScratchcardsRecord> queryByCodes = new ArrayList<WxActScratchcardsRecord>();
			/*for (WxActScratchcardsRecord list : queryLists) {
				String codes = list.getCode();
				if (codes != null) {
					WxActScratchcardsRecord queryByCode = wxActScratchcardsRecordService.queryByCode(codes);
					queryByCodes.add(queryByCode);
				}
			}
			velocityContext.put("queryList", queryByCodes);*/
			velocityContext.put("scratchcards", wxActScratchcards);
			velocityContext.put("joinNum", joinNum);
			velocityContext.put("weixinDto", weixinDto);
			velocityContext.put("nonceStr", WeiXinHttpUtil.nonceStr);// 必填，生成签名的随机串
			velocityContext.put("timestamp", WeiXinHttpUtil.timestamp);// 必填，生成签名的时间戳
			velocityContext.put("signature",WeiXinHttpUtil.getRedisSignature(request, weixinDto.getJwid()));// 必填，签名，见附录1
			//update-begin--Author:zhangweijian  Date: 20180314 for：底部logo修改
			velocityContext.put("huodong_bottom_copyright", baseApiSystemService.getHuodongLogoBottomCopyright(wxActScratchcards.getCreateBy()));
			//update-end--Author:zhangweijian  Date: 20180314 for：底部logo修改
		} catch (ScratchcardsException e) {
			e.printStackTrace();
			LOG.error("toPrize error:{}", e.getMessage());
			velocityContext.put("errCode", e.getDefineCode());
			velocityContext.put("errMsg", e.getMessage());
			viewName=chooseErrorPage(e.getDefineCode());
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("toPrize error:{}", e);
			velocityContext.put("errCode",ScratchcardsExceptionEnum.SYS_ERROR.getErrCode());
			velocityContext.put("errMsg",ScratchcardsExceptionEnum.SYS_ERROR.getErrChineseMsg());
			viewName= "system/vm/error.vm";
		}
		LOG.info("toPrize time={}ms.",
				new Object[] { System.currentTimeMillis() - start });
		ViewVelocity.view(request, response, viewName, velocityContext);
		
	}
	
	/**
	 *查询中奖记录
	 */
	@RequestMapping(value="queryPrizeRecode", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public AjaxJson queryPrizeRecode(@ModelAttribute WeixinDto weixinDto, HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "5") int pageSize) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			//接收参数
			String actId = request.getParameter("actId");
			String jwid = request.getParameter("jwid");
			String openid = request.getParameter("openid");
			WxActScratchcards wxActScratchcard = wxActScratchcardsService.queryById(actId);
			Integer count=wxActScratchcardsRecordService.countByActId(actId,jwid,openid);
			List<WxActScratchcardsRecord> wxActScratchcards = wxActScratchcardsRecordService.queryListByActId(actId,jwid,openid,pageNo*pageSize,pageSize);
			map.put("wxActScratchcards", wxActScratchcards);
			map.put("count", count-pageNo*pageSize);
			j.setAttributes(map);
			j.setObj(wxActScratchcard);
		} catch (Exception e) {
			j.setSuccess(false);
			e.printStackTrace();
		}
		return j;
	}   
	/**
	 *查询中奖记录
	 */
	@RequestMapping(value="updateMyrecord", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public AjaxJson updateMyrecord(@ModelAttribute WxActScratchcardsRecord wxActScratchcardsRecord, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			wxActScratchcardsRecordService.doEdit(wxActScratchcardsRecord);
		} catch (Exception e) {
			j.setSuccess(false);
			e.printStackTrace();
		}
		return j;
	}
	
}
