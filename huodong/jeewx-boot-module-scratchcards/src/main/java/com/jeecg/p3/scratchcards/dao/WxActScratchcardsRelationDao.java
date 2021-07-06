package com.jeecg.p3.scratchcards.dao;

import com.jeecg.p3.scratchcards.entity.WxActScratchcardsRelation;
import org.apache.ibatis.annotations.Param;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import java.util.List;

/**
 * 描述：</b>WxActScratchcardsRelationDao<br>
 * @author：junfeng.zhou
 * @since：2016年07月13日 18时42分26秒 星期三 
 * @version:1.0
 */
public interface WxActScratchcardsRelationDao extends GenericDao<WxActScratchcardsRelation>{
	
	public Integer count(PageQuery<WxActScratchcardsRelation> pageQuery);
	
	public List<WxActScratchcardsRelation> queryPageList(PageQueryWrapper<WxActScratchcardsRelation> wrapper);

	public List<WxActScratchcardsRelation> queryByActId(@Param("actId")String actId);

	public List<WxActScratchcardsRelation> queryPrizeAndAward(@Param("actId")String actId);

	public void bactchDeleteOldAwards(@Param("ids")List<String> ids, @Param("actId")String actId);
	public void batchDeleteByActId( String actid) ;
	public List<WxActScratchcardsRelation> queryByActIdAndJwid(@Param("actId")String actId,
															   @Param("jwid")String jwid);

	public WxActScratchcardsRelation queryId(@Param("id")String id);

	/**
	 * 修改奖品剩余数量和奖品数量
	 */
	public Integer updateAmountAndRemaiNum(@Param("id")String id,@Param("newRemainNum")Integer newRemainNum);

	//update-begin--Author:zhangweijian  Date: 20180330 for：根据奖项id在奖项，奖品关系表查询水果机奖品配置表中奖品是否存在
	/**
	 * 根据奖项id在奖项，奖品关系表查询水果机奖品配置表中奖品是否存在
	 * @param awardId
	 * @param prizeId
	 * */
	public Integer validUsed(@Param("awardId")String awardId, @Param("prizeId")String prizeId);
	//update-end--Author:zhangweijian  Date: 20180330 for：根据奖项id在奖项，奖品关系表查询水果机奖品配置表中奖品是否存在
	
}

