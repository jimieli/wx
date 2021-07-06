package com.jeecg.p3.scratchcards.web.back;

import com.jeecg.p3.scratchcards.entity.WxActScratchcards;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsRelation;
import com.jeecg.p3.scratchcards.service.WxActScratchcardsRelationService;
import com.jeecg.p3.scratchcards.service.WxActScratchcardsService;
import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.util.SystemTools;
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

 /**
 * 描述：</b>WxActScratchcardsRelationController<br>活动奖品明细表
 * @author junfeng.zhou
 * @since：2016年07月13日 18时42分26秒 星期三 
 * @version:1.0
 */
@Controller
@RequestMapping("/scratchcards/back/wxActScratchcardsRelation")
public class WxActScratchcardsRelationController extends BaseController{
  @Autowired
  private WxActScratchcardsRelationService wxActScratchcardsRelationService;
  @Autowired
  private WxActScratchcardsService wxActScratchcardsService;
  
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute WxActScratchcardsRelation query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<WxActScratchcardsRelation> pageQuery = new PageQuery<WxActScratchcardsRelation>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	if(StringUtils.isEmpty(query.getActId())){
	 		query.setActId("-");
	 	}
	 	VelocityContext velocityContext = new VelocityContext();
		pageQuery.setQuery(query);
		WxActScratchcards wxActScratchcards = wxActScratchcardsService.queryById(query.getActId());
		velocityContext.put("wxActScratchcards",wxActScratchcards);
		velocityContext.put("jwid",query.getJwid());
		velocityContext.put("wxActScratchcardsRelation",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(wxActScratchcardsRelationService.queryPageList(pageQuery)));
		String viewName = "scratchcards/back/wxActScratchcardsPrizes-set.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void wxActScratchcardsRelationDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "scratchcards/back/wxActScratchcardsRelation-detail.vm";
		WxActScratchcardsRelation wxActScratchcardsRelation = wxActScratchcardsRelationService.queryById(id);
		velocityContext.put("wxActScratchcardsRelation",wxActScratchcardsRelation);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String viewName = "scratchcards/back/wxActScratchcardsRelation-add.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute WxActScratchcardsRelation wxActScratchcardsRelation){
	AjaxJson j = new AjaxJson();
	try {
		wxActScratchcardsRelationService.doAdd(wxActScratchcardsRelation);
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
		 VelocityContext velocityContext = new VelocityContext();
		 WxActScratchcardsRelation wxActScratchcardsRelation = wxActScratchcardsRelationService.queryById(id);
		 velocityContext.put("wxActScratchcardsRelation",wxActScratchcardsRelation);
		 String viewName = "scratchcards/back/wxActScratchcardsPrizes-setCount.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute WxActScratchcardsRelation wxActScratchcardsRelation){
	AjaxJson j = new AjaxJson();
	try {
		wxActScratchcardsRelationService.doEdit(wxActScratchcardsRelation);
		j.setMsg("编辑成功");
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
			wxActScratchcardsRelationService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}


}

