package com.jeecg.p3.scratchcards.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>WxActScratchcardsRegistration:新人大礼活动参与人员表<br>
 * @author junfeng.zhou
 * @since：2016年07月13日 18时42分25秒 星期三 
 * @version:1.0
 */
public class WxActScratchcardsRegistration implements Entity<String> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private String id;	/**	 *活动id	 */	private String actId;	/**	 *活动所属人openid	 */	private String openid;	/**	 *活动所属人昵称	 */	private String nickname;	/**	 *抽奖次数	 */	private Integer awardsNum;	/**	 *创建时间	 */	private Date createTime;	/**	 *更新时间	 */	private String updateTime;	/**	 *抽奖状态0:未抽奖;1:已抽奖;	 */	private String awardsStatus;	/**	 *	 */	private String jwid;	/**	 *每天的剩余次数	 */	private Integer remainNumDay;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getActId() {	    return this.actId;	}	public void setActId(String actId) {	    this.actId=actId;	}	public String getOpenid() {	    return this.openid;	}	public void setOpenid(String openid) {	    this.openid=openid;	}	public String getNickname() {	    return this.nickname;	}	public void setNickname(String nickname) {	    this.nickname=nickname;	}	public Integer getAwardsNum() {	    return this.awardsNum;	}	public void setAwardsNum(Integer awardsNum) {	    this.awardsNum=awardsNum;	}	public Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(Date createTime) {	    this.createTime=createTime;	}	public String getUpdateTime() {	    return this.updateTime;	}	public void setUpdateTime(String updateTime) {	    this.updateTime=updateTime;	}	public String getAwardsStatus() {	    return this.awardsStatus;	}	public void setAwardsStatus(String awardsStatus) {	    this.awardsStatus=awardsStatus;	}	public String getJwid() {	    return this.jwid;	}	public void setJwid(String jwid) {	    this.jwid=jwid;	}	public Integer getRemainNumDay() {	    return this.remainNumDay;	}	public void setRemainNumDay(Integer remainNumDay) {	    this.remainNumDay=remainNumDay;	}
}

