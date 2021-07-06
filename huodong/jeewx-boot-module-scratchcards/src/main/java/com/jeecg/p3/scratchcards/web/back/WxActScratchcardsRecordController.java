package com.jeecg.p3.scratchcards.web.back;

import com.jeecg.p3.scratchcards.def.ScratchcardsProperties;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsAwards;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsRecord;
import com.jeecg.p3.scratchcards.service.WxActScratchcardsAwardsService;
import com.jeecg.p3.scratchcards.service.WxActScratchcardsRecordService;
import com.jeecg.p3.scratchcards.util.ExcelUtil;
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
 * 描述：</b>WxActScratchcardsRecordController<br>砍价帮砍记录表
 * @author junfeng.zhou
 * @since：2016年07月13日 18时42分25秒 星期三 
 * @version:1.0
 */
@Controller
@RequestMapping("/scratchcards/back/wxActScratchcardsRecord")
public class WxActScratchcardsRecordController extends BaseController{
  @Autowired
  private WxActScratchcardsRecordService wxActScratchcardsRecordService;
  @Autowired
  private WxActScratchcardsAwardsService wxActScratchcardsAwardsService;
  
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute WxActScratchcardsRecord query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<WxActScratchcardsRecord> pageQuery = new PageQuery<WxActScratchcardsRecord>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	VelocityContext velocityContext = new VelocityContext();
		pageQuery.setQuery(query);
		try {
			String jwid = ContextHolderUtils.getSession().getAttribute("jwid").toString();
			String defaultJwid = ScratchcardsProperties.defaultJwid;
			List<WxActScratchcardsAwards> awards = null;
			if (defaultJwid.equals(jwid)) {
				String createBy = ContextHolderUtils.getSession()
						.getAttribute("system_userid").toString();
				awards = wxActScratchcardsAwardsService.queryAwards(jwid,createBy);//奖项
			}else{
				awards = wxActScratchcardsAwardsService.queryAwards(jwid);//奖项
			}
			velocityContext.put("awards",awards);
			velocityContext.put("zj", request.getParameter("zj"));
			velocityContext.put("wxActScratchcardsRecord",query);
			velocityContext.put("pageInfos",SystemTools.convertPaginatedList(wxActScratchcardsRecordService.queryPageList(pageQuery)));
			String viewName = "scratchcards/back/wxActScratchcardsRecord-list.vm";
			ViewVelocity.view(request,response,viewName,velocityContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void wxActScratchcardsRecordDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "scratchcards/back/wxActScratchcardsRecord-detail.vm";
		WxActScratchcardsRecord wxActScratchcardsRecord = wxActScratchcardsRecordService.queryById(id);
		velocityContext.put("wxActScratchcardsRecord",wxActScratchcardsRecord);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String viewName = "scratchcards/back/wxActScratchcardsRecord-add.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute WxActScratchcardsRecord wxActScratchcardsRecord){
	AjaxJson j = new AjaxJson();
	try {
		if(wxActScratchcardsRecord.getPrizesState().equals("1")){
			wxActScratchcardsRecord.setRecieveStatus("0");
		}
		wxActScratchcardsRecordService.doAdd(wxActScratchcardsRecord);
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
		 WxActScratchcardsRecord wxActScratchcardsRecord = wxActScratchcardsRecordService.queryById(id);
		 velocityContext.put("wxActScratchcardsRecord",wxActScratchcardsRecord);
		 String viewName = "scratchcards/back/wxActScratchcardsRecord-edit.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute WxActScratchcardsRecord wxActScratchcardsRecord){
	AjaxJson j = new AjaxJson();
	try {
		wxActScratchcardsRecordService.doEdit(wxActScratchcardsRecord);
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
			wxActScratchcardsRecordService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}

/**
 * 导出中奖纪录
 */
@RequestMapping(value="/exportExcel")
public void exportExcel(HttpServletRequest request,HttpServletResponse response){
	String fileName="";
	String jwid = ContextHolderUtils.getSession().getAttribute("jwid").toString();
	String actId = request.getParameter("actId");
	String prizesState = request.getParameter("prizesState");
	List<WxActScratchcardsRecord> listUser = null;
	try {
		if(StringUtils.isNotBlank(prizesState) && prizesState.equals("1")){
			listUser = wxActScratchcardsRecordService.exportExcel1(actId,jwid);
			fileName="刮刮乐活动中奖纪录";
		}else{
			listUser = wxActScratchcardsRecordService.exportExcel(actId,jwid);
			fileName="刮刮乐活动抽奖纪录";
		}
		ExcelUtil.exportExcel(request, response, listUser, WxActScratchcardsRecord.class, fileName);
	} catch (Exception e) {
		e.printStackTrace();
	}
}

//author：sunkai--date:2018-10-15--for:核销功能--------------------------------------
/**
* 核销
* @return
*/
@RequestMapping(value="toRecieve",method={RequestMethod.GET,RequestMethod.POST})
@ResponseBody
public AjaxJson toRecieve(@RequestParam(required = true, value = "id" ) String id){
		AjaxJson j = new AjaxJson();
		try {
			WxActScratchcardsRecord record = wxActScratchcardsRecordService.queryById(id);
			if(null != record){
				record.setRecieveStatus("1");
				wxActScratchcardsRecordService.doEdit(record);
			}else{
				j.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
		}
		return j;
}
//author：sunkai--date:2018-10-15--for:核销功能--------------------------------------


}

