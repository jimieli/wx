package com.jeecg.p3.shaketicket.web.back;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.util.SystemTools;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeecg.p3.shaketicket.entity.WxActShaketicketRecord;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketRecordVo;
import com.jeecg.p3.shaketicket.service.WxActShaketicketRecordService;
import com.jeecg.p3.shaketicket.util.ContextHolderUtils;
import com.jeecg.p3.shaketicket.util.ExcelUtil;

 /**
 * 描述：</b>WxActShaketicketRecordController<br>抽奖记录表
 * @author pituo
 * @since：2015年12月22日 19时03分50秒 星期二 
 * @version:1.0
 */
@Controller
@RequestMapping("/shaketicket/back/wxActShaketicketRecord")
public class WxActShaketicketRecordController extends BaseController{
  @Autowired
  private WxActShaketicketRecordService wxActShaketicketRecordService;
  
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute WxActShaketicketRecord query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "shaketicket/back/wxActShaketicketRecord-list.vm";
		try {
			PageQuery<WxActShaketicketRecord> pageQuery = new PageQuery<WxActShaketicketRecord>();
		 	pageQuery.setPageNo(pageNo);
		 	pageQuery.setPageSize(pageSize);
			pageQuery.setQuery(query);
			String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
			query.setJwid(jwid);
			velocityContext.put("wxActShaketicketRecord",query);
			//中奖和抽奖页面的标识
			String rebackFlag=request.getParameter("rebackFlag");
			velocityContext.put("jwid",jwid);
			velocityContext.put("rebackFlag",rebackFlag);
			velocityContext.put("pageInfos",SystemTools.convertPaginatedList(wxActShaketicketRecordService.queryPageList(pageQuery)));
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
public void wxActShaketicketRecordDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "shaketicket/back/wxActShaketicketRecord-detail.vm";
		WxActShaketicketRecord wxActShaketicketRecord = wxActShaketicketRecordService.queryById(id);
		velocityContext.put("wxActShaketicketRecord",wxActShaketicketRecord);
		String backurl =  ContextHolderUtils.getRequest().getParameter("backurl");//返回时的url
		velocityContext.put("backurl",backurl);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String viewName = "shaketicket/back/wxActShaketicketRecord-add.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute WxActShaketicketRecord wxActShaketicketRecord){
	AjaxJson j = new AjaxJson();
	try {
		wxActShaketicketRecordService.doAdd(wxActShaketicketRecord);
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
		 WxActShaketicketRecord wxActShaketicketRecord = wxActShaketicketRecordService.queryById(id);
		 velocityContext.put("wxActShaketicketRecord",wxActShaketicketRecord);
		 String viewName = "shaketicket/back/wxActShaketicketRecord-edit.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute WxActShaketicketRecord wxActShaketicketRecord){
	AjaxJson j = new AjaxJson();
	try {
		//update-begin--Author:zhangweijian Date:20181021 for：设置领奖时间
		if(wxActShaketicketRecord!=null&&"1".equals(wxActShaketicketRecord.getReceiveStatus())){
			wxActShaketicketRecord.setReceiveTime(new Date());
		}
		//update-end--Author:zhangweijian Date:20181021 for：设置领奖时间
		wxActShaketicketRecordService.doEdit(wxActShaketicketRecord);
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
			wxActShaketicketRecordService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}

//update-begin--Author:zhangweijian  Date: 20180913 for：导出excel
/**
 * @功能：导出中奖纪录
 */
@RequestMapping(value = "/exportExcel")
public void exportExcel(HttpServletRequest request,HttpServletResponse response){
    try {
		String actId = request.getParameter("actId");
		String drawStatus = request.getParameter("drawStatus");
		List<WxActShaketicketRecord> listAwards = wxActShaketicketRecordService.queryBargainAwardsByActId(actId,drawStatus); 
		List<WxActShaketicketRecordVo> listAwardsVo=new ArrayList<WxActShaketicketRecordVo>();
		for(WxActShaketicketRecord listAward:listAwards){
			//编码字段类型转换文字显示
			if("1".equals(listAward.getDrawStatus())){
				listAward.setDrawStatusForExcel("中奖");
			}else{
				listAward.setDrawStatusForExcel("未中奖");
			}
			if("1".equals(listAward.getReceiveStatus())){
				listAward.setReceiveStatusForExcel("已领取");
			}else{
				listAward.setReceiveStatusForExcel("未领取");
			}
			//复制实体类
			WxActShaketicketRecordVo listAwardVo=new WxActShaketicketRecordVo();
			listAwardVo.setId(listAward.getId());
			listAwardVo.setOpenid(listAward.getOpenid());
			listAwardVo.setActName(listAward.getActName());
			listAwardVo.setAwardsName(listAward.getAwardsName());
			listAwardVo.setDrawStatus(listAward.getDrawStatus());
			listAwardVo.setDrawStatusForExcel(listAward.getDrawStatusForExcel());
			listAwardVo.setDrawTime(listAward.getDrawTime());
			listAwardsVo.add(listAwardVo);
		}
		if("1".equals(drawStatus)){
			ExcelUtil.exportExcel(request, response, listAwards, WxActShaketicketRecord.class, "摇一摇中奖记录");
		}else{
			ExcelUtil.exportExcel(request, response, listAwardsVo, WxActShaketicketRecordVo.class, "摇一摇抽奖记录");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
} 
//update-end--Author:zhangweijian  Date: 20180913 for：传入jwid
}

