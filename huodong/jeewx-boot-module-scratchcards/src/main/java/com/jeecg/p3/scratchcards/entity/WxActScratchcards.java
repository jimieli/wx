package com.jeecg.p3.scratchcards.entity;

import java.util.Date;
import java.util.List;
import org.jeecgframework.p3.core.utils.persistence.Entity;


/**
 * 描述：</b>WxActScratchcards:h5页面彩蛋<br>
 * @author junfeng.zhou
 * @since：2016年07月13日 18时42分22秒 星期三 
 * @version:1.0
 */
public class WxActScratchcards implements Entity<String> {
	private static final long serialVersionUID = 1L;
	private List<WxActScratchcardsRelation> awarsList;
		return awarsList;
	}
	public void setAwarsList(List<WxActScratchcardsRelation> awarsList) {
		this.awarsList = awarsList;
	}
	/**
	/**
	 * 短链接
	 */
	private String shortUrl;
	 *是否关注可参加
	 *创建人
	 */
	private Date createTime;
	/**
	 *分享可得次数 0：否  1：是
	 */
	private String shareStatus;
	
	/**
	 *主题
	 */
	private String templateCode;
	
	public String getShareStatus() {
		return shareStatus;
	}
	public void setShareStatus(String shareStatus) {
		this.shareStatus = shareStatus;
	}
	public Date getCreateTime() {
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getId() {
	    return this.id;
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public String getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
