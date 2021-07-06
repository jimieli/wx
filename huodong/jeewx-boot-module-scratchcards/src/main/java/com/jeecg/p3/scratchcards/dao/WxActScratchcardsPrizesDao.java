package com.jeecg.p3.scratchcards.dao;

import com.jeecg.p3.scratchcards.entity.WxActScratchcardsPrizes;
import org.apache.ibatis.annotations.Param;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import java.util.List;

/**
 * 描述：</b>WxActScratchcardsPrizesDao<br>
 * @author：junfeng.zhou
 * @since：2016年07月13日 18时42分24秒 星期三 
 * @version:1.0
 */
public interface WxActScratchcardsPrizesDao extends GenericDao<WxActScratchcardsPrizes>{
	
	public Integer count(PageQuery<WxActScratchcardsPrizes> pageQuery);
	
	public List<WxActScratchcardsPrizes> queryPageList(PageQueryWrapper<WxActScratchcardsPrizes> wrapper);

	public List<WxActScratchcardsPrizes> queryPrizes(@Param("jwid")String jwid, @Param("createBy")String createBy);

	//update-begin--Author:zhangweijian  Date: 20180329 for：根据jwid，创建人，奖品名称查询奖品表
	/**
	 * 根据jwid，创建人，奖品名称查询奖品表
	 * @param jwid
	 * @param createBy
	 * @param name
	 * */
	public List<WxActScratchcardsPrizes> queryPrizesByName(@Param("jwid")String jwid, @Param("createBy")String createBy, @Param("name")String name);
	//update-end--Author:zhangweijian  Date: 20180329 for：根据jwid，创建人，奖品名称查询奖品表
}

