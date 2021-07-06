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
	}
	//update-begin--Author:zhangweijian  Date: 20181022 for：添加兑奖码字段
	@Excel(exportName="兑奖码", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	
		return awardCode;
	}
	public void setAwardCode(String awardCode) {
		this.awardCode = awardCode;
	}
	//update-end--Author:zhangweijian  Date: 20181022 for：添加兑奖码字段
	/**
	private String drawStatus;
	@Excel(exportName="抽奖状态", exportConvertSign = 0, exportFieldWidth = 30, importConvertSign = 0)
	
	public String getDrawStatusForExcel() {
		return drawStatusForExcel;
	}
	public void setDrawStatusForExcel(String drawStatusForExcel) {
		this.drawStatusForExcel = drawStatusForExcel;
	}
	/**
	private String receiveStatus;
	private String receiveStatusForExcel;
	public String getReceiveStatusForExcel() {
		return receiveStatusForExcel;
	}
	public void setReceiveStatusForExcel(String receiveStatusForExcel) {
		this.receiveStatusForExcel = receiveStatusForExcel;
	}
	/**
	@Excel(exportName="抽奖时间", exportConvertSign = 1, exportFieldWidth = 30, importConvertSign = 0)
	public String getDrawTimeConvert() {
		return DateUtils.formatDate(this.drawTime, "yyyy-MM-dd HH:mm:ss");
	}
		return awardId;
	}
	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}
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
