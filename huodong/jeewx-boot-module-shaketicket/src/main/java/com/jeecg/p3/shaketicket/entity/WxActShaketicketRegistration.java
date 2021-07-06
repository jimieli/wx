package com.jeecg.p3.shaketicket.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>摇一摇注册表<br>
 * @author junfeng.zhou
 * @since：2018年10月12日 15时26分06秒 星期五 
 * @version:1.0
 */
public class WxActShaketicketRegistration implements Entity<String> {
	private static final long serialVersionUID = 1L;
		/**	 *序号	 */	private String id;	/**	 *活动id	 */	private String actId;	/**	 *openID	 */	private String openid;	/**	 *昵称	 */	private String nickname;	/**	 *头像	 */	private String headimg;	/**	 *摇一摇总次数	 */	private Integer shakeCount;	/**	 *当天摇一摇次数	 */	private Integer dayShakeNum;	/**	 *上次摇一摇日期	 */	private Date lastShakeDate;	/**	 *中奖状态：'1'：已中奖，'0'：未中奖	 */	private String awardStatus;	/**	 *创建时间	 */	private Date createTime;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getActId() {	    return this.actId;	}	public void setActId(String actId) {	    this.actId=actId;	}	public String getOpenid() {	    return this.openid;	}	public void setOpenid(String openid) {	    this.openid=openid;	}	public String getNickname() {	    return this.nickname;	}	public void setNickname(String nickname) {	    this.nickname=nickname;	}	public String getHeadimg() {	    return this.headimg;	}	public void setHeadimg(String headimg) {	    this.headimg=headimg;	}	public Integer getShakeCount() {	    return this.shakeCount;	}	public void setShakeCount(Integer shakeCount) {	    this.shakeCount=shakeCount;	}	public Integer getDayShakeNum() {	    return this.dayShakeNum;	}	public void setDayShakeNum(Integer dayShakeNum) {	    this.dayShakeNum=dayShakeNum;	}	public Date getLastShakeDate() {	    return this.lastShakeDate;	}	public void setLastShakeDate(Date lastShakeDate) {	    this.lastShakeDate=lastShakeDate;	}	public String getAwardStatus() {	    return this.awardStatus;	}	public void setAwardStatus(String awardStatus) {	    this.awardStatus=awardStatus;	}	public Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(Date createTime) {	    this.createTime=createTime;	}
}

