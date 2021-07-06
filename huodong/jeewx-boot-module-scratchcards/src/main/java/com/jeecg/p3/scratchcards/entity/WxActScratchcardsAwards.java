package com.jeecg.p3.scratchcards.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>WxActScratchcardsAwards:奖项表<br>
 * @author junfeng.zhou
 * @since：2016年07月13日 18时42分23秒 星期三 
 * @version:1.0
 */
public class WxActScratchcardsAwards implements Entity<String> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private String id;	/**	 *奖项名称	 */	private String awardsName;	/**	 *	 */	private String jwid;	/**	 *	 */	private Integer awardsValue;	/**	 *创建人	 */	private String createBy;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getAwardsName() {	    return this.awardsName;	}	public void setAwardsName(String awardsName) {	    this.awardsName=awardsName;	}	public String getJwid() {	    return this.jwid;	}	public void setJwid(String jwid) {	    this.jwid=jwid;	}	public Integer getAwardsValue() {	    return this.awardsValue;	}	public void setAwardsValue(Integer awardsValue) {	    this.awardsValue=awardsValue;	}	public String getCreateBy() {	    return this.createBy;	}	public void setCreateBy(String createBy) {	    this.createBy=createBy;	}
}

