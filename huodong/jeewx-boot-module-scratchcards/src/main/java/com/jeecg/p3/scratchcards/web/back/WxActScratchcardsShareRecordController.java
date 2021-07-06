package com.jeecg.p3.scratchcards.web.back;

import com.jeecg.p3.scratchcards.entity.WxActScratchcardsShareRecord;
import com.jeecg.p3.scratchcards.service.WxActScratchcardsShareRecordService;
import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.util.SystemTools;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 /**
 * 描述：</b>分享记录表<br>
 * @author sunkai
 * @since：2018年10月17日 10时34分17秒 星期三 
 * @version:1.0
 */
@Controller
@RequestMapping("/scratchcards/back/wxActScratchcardsShareRecord")
public class WxActScratchcardsShareRecordController extends BaseController{

  public final static Logger log = LoggerFactory.getLogger(WxActScratchcardsShareRecordController.class);
  @Autowired
  private WxActScratchcardsShareRecordService wxActScratchcardsShareRecordService;
  
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute WxActScratchcardsShareRecord query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<WxActScratchcardsShareRecord> pageQuery = new PageQuery<WxActScratchcardsShareRecord>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	VelocityContext velocityContext = new VelocityContext();
		pageQuery.setQuery(query);
		velocityContext.put("wxActScratchcardsShareRecord",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(wxActScratchcardsShareRecordService.queryPageList(pageQuery)));
		String viewName = "scratchcards/back/wxActScratchcardsShareRecord-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void wxActScratchcardsShareRecordDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "scratchcards/back/wxActScratchcardsShareRecord-detail.vm";
		WxActScratchcardsShareRecord wxActScratchcardsShareRecord = wxActScratchcardsShareRecordService.queryById(id);
		velocityContext.put("wxActScratchcardsShareRecord",wxActScratchcardsShareRecord);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String viewName = "scratchcards/back/wxActScratchcardsShareRecord-add.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute WxActScratchcardsShareRecord wxActScratchcardsShareRecord){
	AjaxJson j = new AjaxJson();
	try {
		wxActScratchcardsShareRecordService.doAdd(wxActScratchcardsShareRecord);
		j.setMsg("保存成功");
	} catch (Exception e) {
		log.error(e.getMessage());
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
		 WxActScratchcardsShareRecord wxActScratchcardsShareRecord = wxActScratchcardsShareRecordService.queryById(id);
		 velocityContext.put("wxActScratchcardsShareRecord",wxActScratchcardsShareRecord);
		 String viewName = "scratchcards/back/wxActScratchcardsShareRecord-edit.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute WxActScratchcardsShareRecord wxActScratchcardsShareRecord){
	AjaxJson j = new AjaxJson();
	try {
		wxActScratchcardsShareRecordService.doEdit(wxActScratchcardsShareRecord);
		j.setMsg("编辑成功");
	} catch (Exception e) {
		log.error(e.getMessage());
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
			wxActScratchcardsShareRecordService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
		    log.error(e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}


}

