package com.jeecg.p3.shaketicket.entity;

import java.util.Date;
import java.util.List;

import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>WxActShaketicketHome:九宫格活动表<br>
 * @author pituo
 * @since：2015年12月22日 19时03分50秒 星期二 
 * @version:1.0
 */
public class WxActShaketicketHome implements Entity<String> {
	private static final long serialVersionUID = 1L;
		/**	 *键主	 */	private String id;	/**	 *活动名称	 */	private String actName;
	/**	 *启用状态（0：停用；1：激活）	 */	private String activeFlag;	/**	 *模版简称	 */	private String template;	/**	 *抽奖次数	 */	private Integer count;	/**	 *每日抽奖次数	 */	private Integer numPerDay;	/**	 *入口地址	 */	private String hdurl;	/**	 *是否关注可参加 0否1是	 */	private String foucsUserCanJoin;	/**	 *是否绑定手机可参加 0否1是	 */	private String bindingMobileCanJoin;	/**	 *是否中奖可参与 0：中奖可继续参与 1:中奖不可参与	 */
	//update-begin--Author:zhangweijian Date:20181220 for：新增广告字段	private String winCanJoin;
	/**
	 *是否设置广告图
	 */
	private String isSetAdv;
	/**
	 *广告图
	 */
	private String advImg;
	/**
	 *广告图2
	 */
	private String advImg2;
	
	public String getAdvImg2() {
		return advImg2;
	}
	public void setAdvImg2(String advImg2) {
		this.advImg2 = advImg2;
	}

	/**
	 *背景图
	 */
	private String bgImg;
		public String getIsSetAdv() {
		return isSetAdv;
	}
	public void setIsSetAdv(String isSetAdv) {
		this.isSetAdv = isSetAdv;
	}
	public String getAdvImg() {
		return advImg;
	}
	public void setAdvImg(String advImg) {
		this.advImg = advImg;
	}
	public String getBgImg() {
		return bgImg;
	}
	public void setBgImg(String bgImg) {
		this.bgImg = bgImg;
	}
	//update-end--Author:zhangweijian Date:20181220 for：新增广告字段
	/**	 *微信原始id	 */	private String jwid;	/**	 *微信原始id	 */	private String jwidName;	/**	 *所属项目编码	 */	private String projectCode;
	/**
	 *创建人
	 */
	private String createBy;
	
	private Date deadlinetime;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getActName() {	    return this.actName;	}	public void setActName(String actName) {	    this.actName=actName;	}	public String getActiveFlag() {	    return this.activeFlag;	}	public void setActiveFlag(String activeFlag) {	    this.activeFlag=activeFlag;	}		
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public Integer getCount() {	    return this.count;	}	public void setCount(Integer count) {	    this.count=count;	}	public Integer getNumPerDay() {	    return this.numPerDay;	}	public void setNumPerDay(Integer numPerDay) {	    this.numPerDay=numPerDay;	}	public String getHdurl() {	    return this.hdurl;	}	public void setHdurl(String hdurl) {	    this.hdurl=hdurl;	}	public String getFoucsUserCanJoin() {	    return this.foucsUserCanJoin;	}	public void setFoucsUserCanJoin(String foucsUserCanJoin) {	    this.foucsUserCanJoin=foucsUserCanJoin;	}	public String getBindingMobileCanJoin() {	    return this.bindingMobileCanJoin;	}	public void setBindingMobileCanJoin(String bindingMobileCanJoin) {	    this.bindingMobileCanJoin=bindingMobileCanJoin;	}	public String getWinCanJoin() {	    return this.winCanJoin;	}	public void setWinCanJoin(String winCanJoin) {	    this.winCanJoin=winCanJoin;	}	public String getJwid() {	    return this.jwid;	}	public void setJwid(String jwid) {	    this.jwid=jwid;	}	public String getProjectCode() {	    return this.projectCode;	}	public void setProjectCode(String projectCode) {	    this.projectCode=projectCode;	}
	public String getJwidName() {
		return jwidName;
	}
	public void setJwidName(String jwidName) {
		this.jwidName = jwidName;
	}
	
	private List<WxActShaketicketConfig> awarsList;
	public List<WxActShaketicketConfig> getAwarsList() {
		return awarsList;
	}
	public void setAwarsList(List<WxActShaketicketConfig> awarsList) {
		this.awarsList = awarsList;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getDeadlinetime() {
		return deadlinetime;
	}
	public void setDeadlinetime(Date deadlinetime) {
		this.deadlinetime = deadlinetime;
	}
	private Date createTime;
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 活动说明
	 */
	private String detail;
	
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	/**
	 * 短链接
	 */
	private String shortUrl;
	
	//update-begin--Author:zhangweijian Date:20181021 for：添加开始结束的引用
	private String extraLuckyDraw;
	public String getExtraLuckyDraw() {
		return extraLuckyDraw;
	}
	public void setExtraLuckyDraw(String extraLuckyDraw) {
		this.extraLuckyDraw = extraLuckyDraw;
	}
	//update-end--Author:zhangweijian Date:20181021 for：添加开始结束的引用
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	/**
	 * 活动开始时间
	 */
	private Date beginTime;
	/**
	 * 活动结束时间
	 */
	private Date endTime;
	
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Override
	public String toString() {
		return "WxActShaketicketHome [id=" + id + ", actName=" + actName
				+ ", activeFlag=" + activeFlag + ", template=" + template
				+ ", count=" + count + ", numPerDay=" + numPerDay + ", hdurl="
				+ hdurl + ", foucsUserCanJoin=" + foucsUserCanJoin
				+ ", bindingMobileCanJoin=" + bindingMobileCanJoin
				+ ", winCanJoin=" + winCanJoin + ", jwid=" + jwid
				+ ", jwidName=" + jwidName + ", projectCode=" + projectCode
				+ ", createBy=" + createBy + ", deadlinetime=" + deadlinetime
				+ ", awarsList=" + awarsList + ", createTime=" + createTime
				+ ", detail=" + detail + ", shortUrl=" + shortUrl + "]";
	}
	
}

