package com.jeecg.p3.scratchcards.dao;

import com.jeecg.p3.scratchcards.entity.WxActScratchcardsRecord;
import org.apache.ibatis.annotations.Param;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import java.util.List;

/**
 * 描述：</b>WxActScratchcardsRecordDao<br>
 * @author：junfeng.zhou
 * @since：2016年07月13日 18时42分25秒 星期三 
 * @version:1.0
 */
public interface WxActScratchcardsRecordDao extends GenericDao<WxActScratchcardsRecord>{
	
	public Integer count(PageQuery<WxActScratchcardsRecord> pageQuery);
	
	public List<WxActScratchcardsRecord> queryPageList(PageQueryWrapper<WxActScratchcardsRecord> wrapper);

	public Integer getMaxAwardsSeq(@Param("actId")String actId);

	public WxActScratchcardsRecord queryByCode(@Param("code")String code);

	public List<WxActScratchcardsRecord> queryList(@Param("actId")String actId);

	public List<WxActScratchcardsRecord> queryMyList(@Param("openid")String openid,@Param("actId")String actId);

	/**
	 * 导出中奖纪录
	 * @param jwid
	 * @param actId
	 */
	public List<WxActScratchcardsRecord> exportExcel(@Param("jwid")String jwid, @Param("actId")String actId);

	public List<WxActScratchcardsRecord> exportExcel1(@Param("jwid")String jwid, @Param("actId")String actId);

	public Integer countByActId(@Param("actId")String actId, @Param("jwid")String jwid, @Param("openid")String openid);

	public List<WxActScratchcardsRecord> queryListByActId(@Param("actId")String actId,
														  @Param("jwid")String jwid, @Param("openid")String openid, @Param("startRow")int startRow, @Param("pageSize")int pageSize);
	
}

