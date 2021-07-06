package com.jeecg.p3.shaketicket.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;
import org.jeewx.api.core.util.DateUtils;

import com.jeecg.p3.shaketicket.annotation.Excel;

/**
 * 描述：</b>WxActShaketicketRecord:抽奖记录表<br>
 * @author pituo
 * @since：2015年12月23日 13时55分34秒 星期三 
 * @version:1.0
 */
public class WxActShaketicketRecord implements Entity<String> {
	private static final long serialVersionUID = 1L;
		/**	 *ID	 */	private String id;	/**	 *活动ID	 */	private String actId;	/**	 *奖品ID	 */	private String awardId;	/**	 *微信openid	 */
	@Excel(exportName="openid", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private String openid;
	/**
	 *奖品名称
	 */
	@Excel(exportName="奖品名称", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private String awardsName;
	public String getAwardsName() {
		return awardsName;
	}
	public void setAwardsName(String awardsName) {
		this.awardsName = awardsName;
	}	/**	 *奖品序号	 */	private Integer awardsSeq;
	//update-begin--Author:zhangweijian  Date: 20181022 for：添加兑奖码字段	/**	 *兑奖码	 */
	@Excel(exportName="兑奖码", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)	private String awardCode;
		public String getAwardCode() {
		return awardCode;
	}
	public void setAwardCode(String awardCode) {
		this.awardCode = awardCode;
	}
	//update-end--Author:zhangweijian  Date: 20181022 for：添加兑奖码字段
	/**	 *抽奖状态 0 未中奖 1已中奖	 */
	private String drawStatus;
	@Excel(exportName="抽奖状态", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)	private String drawStatusForExcel;
	
	public String getDrawStatusForExcel() {
		return drawStatusForExcel;
	}
	public void setDrawStatusForExcel(String drawStatusForExcel) {
		this.drawStatusForExcel = drawStatusForExcel;
	}
	/**	 *领取状态 0 未领取 1已领取	 */
	private String receiveStatus;	@Excel(exportName="领取状态", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private String receiveStatusForExcel;
	public String getReceiveStatusForExcel() {
		return receiveStatusForExcel;
	}
	public void setReceiveStatusForExcel(String receiveStatusForExcel) {
		this.receiveStatusForExcel = receiveStatusForExcel;
	}
	/**	 *抽奖时间	 */
	@Excel(exportName="抽奖时间", exportConvertSign = 1, exportFieldWidth = 30, importConvertSign = 0)	private Date drawTime;
	public String getDrawTimeConvert() {
		return DateUtils.formatDate(this.drawTime, "yyyy-MM-dd HH:mm:ss");
	}	/**	 *领奖时间	 */	private Date receiveTime;	/**	 *微信公众号	 */	private String jwid;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getActId() {	    return this.actId;	}	public void setActId(String actId) {	    this.actId=actId;	}	public String getAwardId() {
		return awardId;
	}
	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}	public String getOpenid() {	    return this.openid;	}	public void setOpenid(String openid) {	    this.openid=openid;	}	public Integer getAwardsSeq() {	    return this.awardsSeq;	}	public void setAwardsSeq(Integer awardsSeq) {	    this.awardsSeq=awardsSeq;	}	public String getReceiveStatus() {	    return this.receiveStatus;	}	public void setReceiveStatus(String receiveStatus) {	    this.receiveStatus=receiveStatus;	}	public Date getDrawTime() {	    return this.drawTime;	}	public void setDrawTime(Date drawTime) {	    this.drawTime=drawTime;	}	public Date getReceiveTime() {	    return this.receiveTime;	}	public void setReceiveTime(Date receiveTime) {	    this.receiveTime=receiveTime;	}	public String getJwid() {	    return this.jwid;	}	public void setJwid(String jwid) {	    this.jwid=jwid;	}
	public String getDrawStatus() {
		return drawStatus;
	}
	public void setDrawStatus(String drawStatus) {
		this.drawStatus = drawStatus;
	}
	
	/**
	 *卡券id
	 */
	private String cardId;
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	/**
	 * 券码
	 */
	private String cardPsd;
	public String getCardPsd() {
		return cardPsd;
	}
	public void setCardPsd(String cardPsd) {
		this.cardPsd = cardPsd;
	}
	@Excel(exportName="relName", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private String relName;
	/**
	 * 手机号
	 */
	@Excel(exportName="mobile", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private String mobile;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	private String isCard;
	public String getIsCard() {
		return isCard;
	}
	public void setIsCard(String isCard) {
		this.isCard = isCard;
	}
	
	//update begin author:huangqingquan for:查询图片和姓名地址。
	private String img;
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Excel(exportName="address", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	private String address;
	public String getRelName() {
		return relName;
	}
	public void setRelName(String relName) {
		this.relName = relName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	//update end author:huangqingquan for:查询图片和姓名地址。
	//update-begin--Author:zhangweijian Date:20181012 for：添加昵称，头像，活动名称的引用
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 头像
	 */
	private String headimg;
	/**
	 * 活动名称
	 */
	private String actName;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	//update-end--Author:zhangweijian Date:20181012 for：添加昵称，头像，活动名称的引用
}

