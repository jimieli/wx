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
		/**	 *记录id	 */	private String id;	/**	 *活动id	 */	private String actId;
	/**
	 * 活动名称
	 */
	@Excel(exportName="活动名称",exportConvertSign=0,exportFieldWidth=30,importConvertSign=0)
	private String title;	/**	 *openid	 */
	@Excel(exportName="openid",exportConvertSign=0,exportFieldWidth=30,importConvertSign=0)	private String openid;	/**	 *昵称	 */
	@Excel(exportName="昵称",exportConvertSign=0,exportFieldWidth=30,importConvertSign=0)	private String nickname;	/**	 *中奖时间	 */
	@Excel(exportName="抽奖时间",exportConvertSign=1,exportFieldWidth=30,importConvertSign=0)	private Date createTime;	/**	 *奖项	 */	private String awardsId;	/**	 *收货人姓名	 */
	@Excel(exportName="姓名",exportConvertSign=0,exportFieldWidth=30,importConvertSign=0)	private String realname;	/**	 *手机号	 */
	@Excel(exportName="手机号",exportConvertSign=0,exportFieldWidth=30,importConvertSign=0)	private String phone;	/**	 *地址	 */
	@Excel(exportName="地址",exportConvertSign=0,exportFieldWidth=30,importConvertSign=0)	private String address;	/**	 *抽奖序号	 */	private Integer seq;	/**	 *对应微信平台原始id	 */	private String jwid;	/**	 *奖品名字	 */
	@Excel(exportName="奖品名字",exportConvertSign=0,exportFieldWidth=30,importConvertSign=0)	private String prizesName;	/**	 *中奖状态(0未中奖，1中奖)	 */	private String awardsName;	/**	 *	 */	private String prizesState;	/**
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
	 */	private String code;	/**
	 *奖品图片 冗余字段
	 */	private String img;
		public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getActId() {	    return this.actId;	}	public void setActId(String actId) {	    this.actId=actId;	}	public String getOpenid() {	    return this.openid;	}	public void setOpenid(String openid) {	    this.openid=openid;	}	public String getNickname() {	    return this.nickname;	}	public void setNickname(String nickname) {	    this.nickname=nickname;	}	public Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(Date createTime) {	    this.createTime=createTime;	}	public String getAwardsId() {	    return this.awardsId;	}	public void setAwardsId(String awardsId) {	    this.awardsId=awardsId;	}	public String getRealname() {	    return this.realname;	}	public void setRealname(String realname) {	    this.realname=realname;	}	public String getPhone() {	    return this.phone;	}	public void setPhone(String phone) {	    this.phone=phone;	}	public String getAddress() {	    return this.address;	}	public void setAddress(String address) {	    this.address=address;	}	public Integer getSeq() {	    return this.seq;	}	public void setSeq(Integer seq) {	    this.seq=seq;	}	public String getJwid() {	    return this.jwid;	}	public void setJwid(String jwid) {	    this.jwid=jwid;	}	public String getPrizesName() {	    return this.prizesName;	}	public void setPrizesName(String prizesName) {	    this.prizesName=prizesName;	}	public String getAwardsName() {	    return this.awardsName;	}	public void setAwardsName(String awardsName) {	    this.awardsName=awardsName;	}	public String getPrizesState() {	    return this.prizesState;	}	public void setPrizesState(String prizesState) {	    this.prizesState=prizesState;	}	public String getCode() {	    return this.code;	}	public void setCode(String code) {	    this.code=code;	}
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

