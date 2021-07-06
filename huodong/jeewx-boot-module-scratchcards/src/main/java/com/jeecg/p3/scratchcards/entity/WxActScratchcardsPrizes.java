package com.jeecg.p3.scratchcards.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>WxActScratchcardsPrizes:奖品表<br>
 * @author junfeng.zhou
 * @since：2016年07月13日 18时42分24秒 星期三 
 * @version:1.0
 */
public class WxActScratchcardsPrizes implements Entity<String> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private String id;	/**	 *奖品名称	 */	private String name;	/**	 *奖品图片	 */	private String img;	/**	 *微信原始id	 */	private String jwid;	/**	 *创建人	 */	private String createBy;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getName() {	    return this.name;	}	public void setName(String name) {	    this.name=name;	}	public String getImg() {	    return this.img;	}	public void setImg(String img) {	    this.img=img;	}	public String getJwid() {	    return this.jwid;	}	public void setJwid(String jwid) {	    this.jwid=jwid;	}	public String getCreateBy() {	    return this.createBy;	}	public void setCreateBy(String createBy) {	    this.createBy=createBy;	}
}

