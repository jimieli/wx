package com.jeecg.p3.scratchcards.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>WxActScratchcardsRelation:活动奖品明细表<br>
 * @author junfeng.zhou
 * @since：2016年07月13日 18时42分26秒 星期三 
 * @version:1.0
 */
public class WxActScratchcardsRelation implements Entity<String> {
	private static final long serialVersionUID = 1L;
	private String probabilitys;	public String getProbabilitys() {
		return probabilitys;
	}
	public void setProbabilitys(String probabilitys) {
		this.probabilitys = probabilitys;
	}
	/**	 *	 */	private String id;	/**	 *	 */	private String prizeId;	/**	 *	 */	private String actId;	/**	 *	 */	private String jwid;	/**	 *	 */	private String awardId;	/**	 *数量	 */	private Integer amount;	/**	 *剩余数量	 */	private Integer remainNum;	/**	 *概率	 */
	private String awardsName;
	private String name;
	/**
	 * 添加图片引用
	 */
    private String awardImg;	public String getAwardsName() {
		return awardsName;
	}
	public void setAwardsName(String awardsName) {
		this.awardsName = awardsName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private BigDecimal probability;	public BigDecimal getProbability() {
		return probability;
	}
	public void setProbability(BigDecimal probability) {
		this.probability = probability;
	}
	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getPrizeId() {	    return this.prizeId;	}	public void setPrizeId(String prizeId) {	    this.prizeId=prizeId;	}	public String getActId() {	    return this.actId;	}	public void setActId(String actId) {	    this.actId=actId;	}	public String getJwid() {	    return this.jwid;	}	public void setJwid(String jwid) {	    this.jwid=jwid;	}	public String getAwardId() {	    return this.awardId;	}	public void setAwardId(String awardId) {	    this.awardId=awardId;	}	public Integer getAmount() {	    return this.amount;	}	public void setAmount(Integer amount) {	    this.amount=amount;	}	public Integer getRemainNum() {	    return this.remainNum;	}	public void setRemainNum(Integer remainNum) {	    this.remainNum=remainNum;	}	
	//author：sunkai--date:2018-10-23--for:增加奖品数量修改-------
	private String prizeImg;
	private String prizeName;
	
	public String getPrizeImg() {
		return prizeImg;
	}
	public void setPrizeImg(String prizeImg) {
		this.prizeImg = prizeImg;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	//author：sunkai--date:2018-10-23--for:增加奖品数量修改-------
	public String getAwardImg() {
		return awardImg;
	}
	public void setAwardImg(String awardImg) {
		this.awardImg = awardImg;
	}
	
}

