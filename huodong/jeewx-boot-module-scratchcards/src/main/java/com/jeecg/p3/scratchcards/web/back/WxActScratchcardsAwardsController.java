package com.jeecg.p3.scratchcards.web.back;

import com.jeecg.p3.scratchcards.def.ScratchcardsProperties;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsAwards;
import com.jeecg.p3.scratchcards.service.WxActScratchcardsAwardsService;
import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.util.SystemTools;
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
import java.util.List;

 /**
 * 描述：</b>WxActScratchcardsAwardsController<br>奖项表
 * @author junfeng.zhou
 * @since：2016年07月13日 18时42分23秒 星期三 
 * @version:1.0
 */
@Controller
@RequestMapping("/scratchcards/back/wxActScratchcardsAwards")
public class WxActScratchcardsAwardsController extends BaseController{
  @Autowired
  private WxActScratchcardsAwardsService wxActScratchcardsAwardsService;
  
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute WxActScratchcardsAwards query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(required = true, value = "showReturnFlag" ) String showReturnFlag) throws Exception{
		String viewName = "scratchcards/back/wxActScratchcardsAwards-list.vm";
		VelocityContext velocityContext = new VelocityContext();
	 	try {
	 		PageQuery<WxActScratchcardsAwards> pageQuery = new PageQuery<WxActScratchcardsAwards>();
		 	pageQuery.setPageNo(pageNo);
		 	pageQuery.setPageSize(pageSize);
		 	String defaultJwid = ScratchcardsProperties.defaultJwid;
			String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
			String createBy = ContextHolderUtils.getSession().getAttribute("system_userid").toString();
			if(jwid.equals(defaultJwid)){
				query.setCreateBy(createBy);
			}
		 	//update-begin-alex Date:20170316 for:检索列表加入当前jwid的条件
			query.setJwid(jwid);
			//update-end-alex Date:20170316 for:检索列表加入当前jwid的条件		pageQuery.setQuery(query);
			velocityContext.put("wxActScratchcardsAwards",query);
			//update-begin--Author:zhangweijian  Date: 20180319 for：增加一个返回按钮是否显示的字段
			velocityContext.put("showReturnFlag",showReturnFlag);
			//update-end--Author:zhangweijian  Date: 20180319 for：增加一个返回按钮是否显示的字段
			pageQuery.setQuery(query);
			velocityContext.put("pageInfos",SystemTools.convertPaginatedList(wxActScratchcardsAwardsService.queryPageList(pageQuery)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void wxActScratchcardsAwardsDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "scratchcards/back/wxActScratchcardsAwards-detail.vm";
		WxActScratchcardsAwards wxActScratchcardsAwards = wxActScratchcardsAwardsService.queryById(id);
		velocityContext.put("wxActScratchcardsAwards",wxActScratchcardsAwards);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String viewName = "scratchcards/back/wxActScratchcardsAwards-add.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute WxActScratchcardsAwards wxActScratchcardsAwards){
	AjaxJson j = new AjaxJson();
	try {
		//update-begin-alex Date:20170316 for:保存奖品奖项时记录创建人和当前jwid
		String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
		String createBy = ContextHolderUtils.getSession().getAttribute("system_userid").toString();
		wxActScratchcardsAwards.setCreateBy(createBy);
		wxActScratchcardsAwards.setJwid(jwid);
		//update-end-alex Date:20170316 for:保存奖品奖项时记录创建人和当前jwid
		//update-begin-sunkai Date:20180928 for:判断是否存在该名称奖项
		String flag = findAwardsByName(wxActScratchcardsAwards.getAwardsName());
		if(StringUtils.isEmpty(flag) || flag.equals(wxActScratchcardsAwards.getId())){
			wxActScratchcardsAwardsService.doAdd(wxActScratchcardsAwards);
			j.setMsg("保存成功");
		}else{
			j.setSuccess(false);
			j.setMsg("奖项名称已存在，请重新输入！");
		}
		//update-end-sunkai Date:20180928 for:判断是否存在该名称奖项
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
		 VelocityContext velocityContext = new VelocityContext();
		 WxActScratchcardsAwards wxActScratchcardsAwards = wxActScratchcardsAwardsService.queryById(id);
		 velocityContext.put("wxActScratchcardsAwards",wxActScratchcardsAwards);
		 String viewName = "scratchcards/back/wxActScratchcardsAwards-edit.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute WxActScratchcardsAwards wxActScratchcardsAwards){
	AjaxJson j = new AjaxJson();
	try {
		//update-begin-sunkai Date:20180928 for:判断是否存在该名称奖项
		String flag = findAwardsByName(wxActScratchcardsAwards.getAwardsName());
		if(StringUtils.isEmpty(flag) || flag.equals(wxActScratchcardsAwards.getId())){
			wxActScratchcardsAwardsService.doEdit(wxActScratchcardsAwards);
			j.setMsg("编辑成功");
		}else{
			j.setSuccess(false);
			j.setMsg("奖项名称已存在，请重新输入！");
		}
		//update-end-sunkai Date:20180928 for:判断是否存在该名称奖项
	} catch (Exception e) {
		e.printStackTrace();
		j.setSuccess(false);
		j.setMsg("编辑失败");
	}
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
			//update-begin--Author:zhangweijian  Date: 20180330 for：判断奖项是否被使用
			//判断奖项是否被使用
			Boolean used=wxActScratchcardsAwardsService.validUsed(id);
			if(used){
				j.setSuccess(false);
				j.setMsg("该奖项已经被活动使用，不能删除");
			}else{
				wxActScratchcardsAwardsService.doDelete(id);
				j.setMsg("删除成功");
			}
			//update-end--Author:zhangweijian  Date: 20180330 for：判断奖项是否被使用
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}

//update-begin-sunkai Date:20180928 for:判断是否存在该名称奖项
public String findAwardsByName(String awardsName){
	String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
	String defaultJwid = ScratchcardsProperties.defaultJwid;
	String createBy = "";
	//如果是H5活动汇
	if(defaultJwid.equals(jwid)){
		createBy = ContextHolderUtils.getSession().getAttribute("system_userid").toString();
	}
	List<WxActScratchcardsAwards> queryAwardsByName = wxActScratchcardsAwardsService.queryAwardsByName(jwid, createBy, awardsName);
	if(queryAwardsByName.size()>0){
		return queryAwardsByName.get(0).getId();
	}else{
		return "";
	}
}
//update-end-sunkai Date:20180928 for:判断是否存在该名称奖项
}

