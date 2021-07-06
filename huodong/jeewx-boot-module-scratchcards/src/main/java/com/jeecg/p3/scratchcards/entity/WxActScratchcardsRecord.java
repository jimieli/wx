package com.jeecg.p3.scratchcards.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jeecgframework.p3.core.utils.persistence.Entity;

import com.jeecg.p3.scratchcards.annotation.Excel;

/**
 * 描述：</b>WxActScratchcardsRecord:砍价帮砍记录表<br>
 * @author junfeng.zhou
 * @since：2016年07月13日 18时42分25秒 星期三 
 * @version:1.0
 */
public class WxActScratchcardsRecord implements Entity<String> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 活动名称
	 */
	@Excel(exportName="活动名称",exportConvertSign=0,exportFieldWidth=30,importConvertSign=0)
	private String title;
	@Excel(exportName="openid",exportConvertSign=0,exportFieldWidth=30,importConvertSign=0)
	@Excel(exportName="昵称",exportConvertSign=0,exportFieldWidth=30,importConvertSign=0)
	@Excel(exportName="抽奖时间",exportConvertSign=1,exportFieldWidth=30,importConvertSign=0)
	@Excel(exportName="姓名",exportConvertSign=0,exportFieldWidth=30,importConvertSign=0)
	@Excel(exportName="手机号",exportConvertSign=0,exportFieldWidth=30,importConvertSign=0)
	@Excel(exportName="地址",exportConvertSign=0,exportFieldWidth=30,importConvertSign=0)
	@Excel(exportName="奖品名字",exportConvertSign=0,exportFieldWidth=30,importConvertSign=0)
	 *领奖状态
	 */
	private String recieveStatus;
	public String getRecieveStatus() {
		return recieveStatus;
	}
	public void setRecieveStatus(String recieveStatus) {
		this.recieveStatus = recieveStatus;
	}
	/**
	 *头像
	 */
	private String headImg;
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	/**
	 *奖品编码
	 */
	 *奖品图片 冗余字段
	 */
	
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getId() {
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreateTimeConvert(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(this.createTime);
	}
}
