package com.jeecg.p3.scratchcards.dao;

import com.jeecg.p3.scratchcards.entity.WxActScratchcards;
import org.apache.ibatis.annotations.Param;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import java.util.List;

/**
 * 描述：</b>WxActScratchcardsDao<br>
 * @author：junfeng.zhou
 * @since：2016年07月13日 18时42分22秒 星期三 
 * @version:1.0
 */
public interface WxActScratchcardsDao extends GenericDao<WxActScratchcards>{
	
	public Integer count(PageQuery<WxActScratchcards> pageQuery);
	
	public List<WxActScratchcards> queryPageList(PageQueryWrapper<WxActScratchcards> wrapper);

	/**
	 * 修改短链接
	 * @param id
	 * @param shortUrl
	 */
	public void editShortUrl(@Param("id")String id, @Param("shortUrl")String shortUrl);
	
}

