package com.jeecg.p3.shaketicket.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>WxActShaketicketConfig:活动奖项配置表<br>
 * @author pituo
 * @since：2015年12月24日 11时08分29秒 星期四 
 * @version:1.0
 */
public class WxActShaketicketConfig implements Entity<String> {
	private static final long serialVersionUID = 1L;
		/**	 *活动ID	 */	private String id;	/**	 *所属活动	 */	private String actId;	/**	 *	 */	private String awardId;
	/**
	 *
	 */
	private String awardsName;
	public String getAwardsName() {		return awardsName;
	}
	public void setAwardsName(String awardsName) {
		this.awardsName = awardsName;
	}
	//update-begin--Author:zhangweijian  Date: 20180913 for：添加奖品图片字段
	private String awardImg;
	
	public String getAwardImg() {
		return awardImg;
	}
	public void setAwardImg(String awardImg) {
		this.awardImg = awardImg;
	}
	//update-end--Author:zhangweijian  Date: 20180913 for：添加奖品图片字段
	/**	 *中奖概率	 */
	//update-begin--Author:zhangweijian  Date: 20181024 for：修改概率字段类型	private BigDecimal probability;
		public BigDecimal getProbability() {
		return probability;
	}
	public void setProbability(BigDecimal probability) {
		this.probability = probability;
	}
	//update-end--Author:zhangweijian  Date: 20181024 for：修改概率字段类型
	private String probabilitys;
	
	public String getProbabilitys() {
		return probabilitys;
	}
	public void setProbabilitys(String probabilitys) {
		this.probabilitys = probabilitys;
	}
	/**	 *总数量	 */	private Integer amount;	/**	 *剩余数量	 */	private Integer remainNum;	/**	 *微信公众号	 */	private String jwid;	private String active;
	/**
	 *开始时间
	 */
	private Date beginTime;
	/**
	 *结束时间
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
	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getActId() {	    return this.actId;	}	public void setActId(String actId) {	    this.actId=actId;	}	public String getAwardId() {	    return this.awardId;	}	public void setAwardId(String awardId) {	    this.awardId=awardId;	}	public Integer getAmount() {	    return this.amount;	}	public void setAmount(Integer amount) {	    this.amount=amount;	}	public Integer getRemainNum() {	    return this.remainNum;	}	public void setRemainNum(Integer remainNum) {	    this.remainNum=remainNum;	}	public String getJwid() {	    return this.jwid;	}	public void setJwid(String jwid) {	    this.jwid=jwid;	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
}

