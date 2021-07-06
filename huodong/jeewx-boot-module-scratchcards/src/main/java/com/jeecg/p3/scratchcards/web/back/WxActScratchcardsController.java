package com.jeecg.p3.scratchcards.web.back;

import com.jeecg.p3.baseApi.util.WxActReplaceUtil;
import com.jeecg.p3.scratchcards.def.ScratchcardsProperties;
import com.jeecg.p3.scratchcards.entity.*;
import com.jeecg.p3.scratchcards.service.*;
import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.util.SystemTools;
import org.jeecgframework.p3.core.util.WeiXinHttpUtil;
import org.jeecgframework.p3.core.util.plugin.ContextHolderUtils;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

 /**
 * 描述：</b>WxActScratchcardsController<br>h5页面彩蛋
 * @author junfeng.zhou
 * @since：2016年07月13日 18时42分22秒 星期三 
 * @version:1.0
 */
@Controller
@RequestMapping("/scratchcards/back/wxActScratchcards")
public class WxActScratchcardsController extends BaseController{
  @Autowired
  private WxActScratchcardsService wxActScratchcardsService;
  @Autowired
  private WxActScratchcardsAwardsService wxActScratchcardsAwardsService;
  @Autowired
  private WxActScratchcardsPrizesService wxActScratchcardsPrizesService;
  @Autowired
  private WxActScratchcardsRelationService wxActScratchcardsRelationService;
  @Autowired
  private WxActScratchcardsRecordService wxActScratchcardsRecordService;
  private static String domain = ScratchcardsProperties.domain;
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute WxActScratchcards query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<WxActScratchcards> pageQuery = new PageQuery<WxActScratchcards>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	VelocityContext velocityContext = new VelocityContext();
	 	String defaultJwid = ScratchcardsProperties.defaultJwid;
		String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
		String createBy = ContextHolderUtils.getSession().getAttribute("system_userid").toString();
		if(jwid.equals(defaultJwid)){
			query.setCreateBy(createBy);
		}
		query.setJwid(jwid);
		pageQuery.setQuery(query);
		velocityContext.put("wxActScratchcards",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(wxActScratchcardsService.queryPageList(pageQuery)));
		String viewName = "scratchcards/back/wxActScratchcards-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void wxActScratchcardsDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "scratchcards/back/wxActScratchcards-detail.vm";
		WxActScratchcards wxActScratchcards = wxActScratchcardsService.queryById(id);
		velocityContext.put("wxActScratchcards",wxActScratchcards);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
	 //author:sunkai--date:2018-09-28--for:奖品奖项数据隔离
	 String defaultJwid = ScratchcardsProperties.defaultJwid;
	 String createBy= "";
	 //判断是否是默认公众号，如果是，按创建人区分，如果不是，按公众号区分
	 if(jwid.equals(defaultJwid)){
		 createBy = ContextHolderUtils.getSession().getAttribute("system_userid").toString();
	 }
	 //author:sunkai--date:2018-09-28--for:奖品奖项数据隔离
	 List<WxActScratchcardsAwards> awards = wxActScratchcardsAwardsService.queryAwards(jwid,createBy);//查询奖项的集合
	 velocityContext.put("awards",awards);
	 List<WxActScratchcardsPrizes> prizes = wxActScratchcardsPrizesService.queryPrizes(jwid,createBy);//查询奖品的集合
	 velocityContext.put("prizes",prizes);
	 velocityContext.put("date",new Date().getTime());
	 String viewName = "scratchcards/back/wxActScratchcards-add.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute WxActScratchcards wxActScratchcards){
	AjaxJson j = new AjaxJson();
	try {
		//update-being-alex-----Date:2017-2-24----for:替换活动说明中非法代码------
		wxActScratchcards.setDescription(WxActReplaceUtil.replace(wxActScratchcards.getDescription()));
		//update-end-alex-----Date:2017-2-24----for:替换活动说明中非法代码------
		String defaultJwid = ScratchcardsProperties.defaultJwid;
		String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
		if(defaultJwid.equals(jwid)){
			String createBy = ContextHolderUtils.getSession().getAttribute("system_userid").toString();
			wxActScratchcards.setCreateBy(createBy);
		}
		wxActScratchcards.setTemplateCode("newTemplet");
		wxActScratchcards.setJwid(jwid);
		wxActScratchcardsService.doAdd(wxActScratchcards);
		j.setMsg("保存成功");
	} catch (Exception e) {
		e.printStackTrace();
		j.setSuccess(false);
		j.setMsg("保存失败");
	}
	return j;
}

/**
 * 跳转到编辑页面
 * @return
 */
@RequestMapping(value="toEdit",method = RequestMethod.GET)
public void toEdit(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
		 try {
			 VelocityContext velocityContext = new VelocityContext();
			 WxActScratchcards wxActScratchcards = wxActScratchcardsService.queryById(id);
			 velocityContext.put("wxActScratchcards",wxActScratchcards);
			//添加修改概率
			 String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
			 //author:sunkai--date:2018-09-28--for:奖品奖项数据隔离
			 String defaultJwid = ScratchcardsProperties.defaultJwid;
			 String createBy= "";
			 //判断是否是默认公众号，如果是，按创建人区分，如果不是，按公众号区分
			 if(jwid.equals(defaultJwid)){
				 createBy = ContextHolderUtils.getSession().getAttribute("system_userid").toString();
			 }
			 //author:sunkai--date:2018-09-28--for:奖品奖项数据隔离
			 List<WxActScratchcardsRelation> wxActScratchcardsRelations = wxActScratchcardsRelationService.queryByActIdAndJwid(id, jwid);
			 //将小数转为整数
			 for(WxActScratchcardsRelation list:wxActScratchcardsRelations){
				 BigDecimal precent = new BigDecimal("0.01");
				 list.setProbabilitys(list.getProbability().divide(precent).stripTrailingZeros().toPlainString());
			 }
			 
			 velocityContext.put("awarsDetailList",wxActScratchcardsRelations);
			 List<WxActScratchcardsAwards> awards = wxActScratchcardsAwardsService.queryAwards(jwid,createBy);
				velocityContext.put("awards",awards);
				List<WxActScratchcardsPrizes> prizes = wxActScratchcardsPrizesService.queryPrizes(jwid,createBy);
				velocityContext.put("prizes",prizes);	
			 //查询活动是否有参与记录
			 List<WxActScratchcardsRecord> recordList = wxActScratchcardsRecordService.queryList(id);
			 if(null != recordList && recordList.size() > 0){
				 velocityContext.put("hasuserjoin","1");
			 }
			 String viewName = "scratchcards/back/wxActScratchcards-edit.vm";
			 ViewVelocity.view(request,response,viewName,velocityContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute WxActScratchcards wxActScratchcards){
	AjaxJson j = new AjaxJson();
	try {
		//update-being-alex-----Date:2017-2-24----for:替换活动说明中非法代码------
		wxActScratchcards.setDescription(WxActReplaceUtil.replace(wxActScratchcards.getDescription()));
		//update-end-alex-----Date:2017-2-24----for:替换活动说明中非法代码------
	//update-being-sunkai-----Date:2018-09-12----for:修改奖品数量不合法提示------
		String msg = wxActScratchcardsService.doEdit(wxActScratchcards);
		if(msg.equals("")){
			j.setMsg("编辑成功");
		}else{
			j.setSuccess(false);
			j.setMsg(msg);
		}
	} catch (Exception e) {
		e.printStackTrace();
		j.setSuccess(false);
		j.setMsg("编辑失败");
	}
	//update-end-sunkai-----Date:2018-09-12----for:修改奖品数量不合法提示------
	return j;
}


/**
 * 删除
 * @return
 */
@RequestMapping(value="doDelete",method = RequestMethod.GET)
@ResponseBody
public AjaxJson doDelete(@RequestParam(required = true, value = "id" ) String id){
		AjaxJson j = new AjaxJson();
		try {
			wxActScratchcardsService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}

/**
 * 获取shortUrl
 * @param id
 * @return
 */
@RequestMapping(value="getShortUrl",method = RequestMethod.POST)
@ResponseBody
public AjaxJson getShortUrl(@RequestParam(required = true, value = "id" ) String id){
	AjaxJson j=new AjaxJson();
	try {
		WxActScratchcards wxActScratchcards = wxActScratchcardsService.queryById(id);
		String shortUrl = wxActScratchcards.getShortUrl();
		if(StringUtils.isEmpty(shortUrl)){
			String hdurl=wxActScratchcards.getHdurl();
			hdurl = hdurl.replace("${domain}", domain);
			shortUrl=WeiXinHttpUtil.getShortUrl(hdurl,ScratchcardsProperties.defaultJwid);
			if(StringUtils.isEmpty(shortUrl)){
				shortUrl=hdurl;
			}else{
				wxActScratchcardsService.editShortUrl(wxActScratchcards.getId(),shortUrl);
			}
		}
		if(StringUtils.isEmpty(shortUrl)){
			j.setMsg("获取地址失败！");
			j.setSuccess(false);
		}else{
			j.setObj(shortUrl);
			j.setSuccess(true);
			j.setMsg("获取地址成功！");
		}
	} catch (Exception e) {
		e.printStackTrace();
		j.setMsg("获取地址失败！");
		j.setSuccess(false);
	}
	return j;
}
}

