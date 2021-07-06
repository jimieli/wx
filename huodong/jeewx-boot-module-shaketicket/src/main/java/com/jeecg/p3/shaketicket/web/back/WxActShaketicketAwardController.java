package com.jeecg.p3.shaketicket.web.back;

import com.jeecg.p3.baseApi.util.OSSBootUtil;
import com.jeecg.p3.shaketicket.def.SystemShakProperties;
import com.jeecg.p3.shaketicket.entity.WxActShaketicketAward;
import com.jeecg.p3.shaketicket.service.WxActShaketicketAwardService;
import com.jeecg.p3.shaketicket.util.ContextHolderUtils;
import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.util.SystemTools;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

 /**
 * 描述：</b>WxActShaketicketAwardController<br>奖项表
 * @author pituo
 * @since：2015年12月24日 11时08分30秒 星期四 
 * @version:1.0
 */
@Controller
@RequestMapping("/shaketicket/back/wxActShaketicketAward")
public class WxActShaketicketAwardController extends BaseController{
  @Autowired
  private WxActShaketicketAwardService wxActShaketicketAwardService;
  private static String defaultJwid = SystemShakProperties.defaultJwid;

	 /**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute WxActShaketicketAward query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(required = true, value = "showReturnFlag" ) String showReturnFlag) throws Exception{
	 	PageQuery<WxActShaketicketAward> pageQuery = new PageQuery<WxActShaketicketAward>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	VelocityContext velocityContext = new VelocityContext();
	 	String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
	 	query.setJwid(jwid);
	 	if(defaultJwid.equals(jwid)){
	 		String createBy = request.getSession().getAttribute("system_userid").toString();
	 		query.setCreateBy(createBy);
	 	}
		pageQuery.setQuery(query);
		velocityContext.put("jwid",jwid);
		velocityContext.put("wxActShaketicketAward",query);
		//update-begin--Author:zhangweijian  Date: 20180319 for：增加一个返回按钮是否显示的字段
		velocityContext.put("showReturnFlag",showReturnFlag);
		//update-end--Author:zhangweijian  Date: 20180319 for：增加一个返回按钮是否显示的字段
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(wxActShaketicketAwardService.queryPageList(pageQuery)));
		String viewName = "shaketicket/back/wxActShaketicketAward-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void wxActShaketicketAwardDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "shaketicket/back/wxActShaketicketAward-detail.vm";
		WxActShaketicketAward wxActShaketicketAward = wxActShaketicketAwardService.queryById(id);
		velocityContext.put("wxActShaketicketAward",wxActShaketicketAward);
		 String jwid =  request.getSession().getAttribute("jwid").toString();
		 velocityContext.put("jwid",jwid);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String viewName = "shaketicket/back/wxActShaketicketAward-add.vm";
	 String jwid =  request.getSession().getAttribute("jwid").toString();
	 velocityContext.put("jwid",jwid);
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute WxActShaketicketAward wxActShaketicketAward){
	AjaxJson j = new AjaxJson();
	try {
		String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
	 	String createBy = ContextHolderUtils.getSession().getAttribute("system_userid").toString();
		//如果是H5活动汇
	 	if(defaultJwid.equals(jwid)){
	 		List<WxActShaketicketAward> queryAwardsByName = wxActShaketicketAwardService.queryAwardsByName(jwid, createBy, wxActShaketicketAward.getAwardsName());
	 		if (queryAwardsByName.size()>0) {
	 			j.setMsg("奖品已存在，无需重复增加");
				return j;
			}
	 	}else{
	 		List<WxActShaketicketAward> queryAwardsByName = wxActShaketicketAwardService.queryAwardsByName(jwid, createBy, wxActShaketicketAward.getAwardsName());
	 		if (queryAwardsByName.size()>0) {
	 			j.setMsg("奖品已存在，无需重复增加");
	 			return j;
	 		}
	 	}
		wxActShaketicketAwardService.doAdd(wxActShaketicketAward);
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
	 WxActShaketicketAward wxActShaketicketAward = wxActShaketicketAwardService.queryById(id);
	 velocityContext.put("wxActShaketicketAward",wxActShaketicketAward);
	 String viewName = "shaketicket/back/wxActShaketicketAward-edit.vm";
	 String jwid =  request.getSession().getAttribute("jwid").toString();
	 velocityContext.put("jwid",jwid);
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute WxActShaketicketAward wxActShaketicketAward){
	AjaxJson j = new AjaxJson();
	try {
		String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
	 	String createBy = ContextHolderUtils.getSession().getAttribute("system_userid").toString();
		//如果是H5活动汇
	 	if(defaultJwid.equals(jwid)){
	 		List<WxActShaketicketAward> queryAwardsByName = wxActShaketicketAwardService.queryAwardsByName(jwid, createBy, wxActShaketicketAward.getAwardsName());
	 		if (queryAwardsByName.size()>0&&!queryAwardsByName.get(0).getId().equals(wxActShaketicketAward.getId())) {
	 			j.setSuccess(false);
	 			j.setMsg("奖品已存在，请重新输入奖品名称");
				return j;
			}
	 	}else{
	 		List<WxActShaketicketAward> queryAwardsByName = wxActShaketicketAwardService.queryAwardsByName(jwid, createBy, wxActShaketicketAward.getAwardsName());
	 		if (queryAwardsByName.size()>0&&!queryAwardsByName.get(0).getId().equals(wxActShaketicketAward.getId())) {
	 			j.setSuccess(false);
	 			j.setMsg("奖品已存在，请重新输入奖品名称");
	 			return j;
	 		}
	 	}
		wxActShaketicketAwardService.doEdit(wxActShaketicketAward);
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
			//update-begin--Author:zhangweijian  Date: 20180329 for：//判断奖项是否被使用
			//判断奖项是否被使用
			Boolean used=wxActShaketicketAwardService.validUsed(id);
			if(used){
				j.setSuccess(false);
				j.setMsg("该奖项已经被活动使用，不能删除");
			}else{
				wxActShaketicketAwardService.doDelete(id);
				j.setMsg("删除成功");
			}
			//update-end--Author:zhangweijian  Date: 20180329 for：//判断奖项是否被使用
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}

/**
 * 上传照片
 * @return
 */
@RequestMapping(value = "/doUpload",method ={RequestMethod.POST})
@ResponseBody
public AjaxJson doUpload(MultipartHttpServletRequest request,HttpServletResponse response){
	AjaxJson j = new AjaxJson();
	try {
		MultipartFile uploadify = request.getFile("file");
		String jwid =  request.getSession().getAttribute("jwid").toString();
        /*byte[] bytes = uploadify.getBytes();
        String realFilename=uploadify.getOriginalFilename();
        String fileNoExtension = realFilename.substring(0,realFilename.lastIndexOf("."));
        String fileExtension = realFilename.substring(realFilename.lastIndexOf("."));
        String filename=*//*fileNoExtension+*//*System.currentTimeMillis()+fileExtension;
        //String uploadDir = request.getSession().getServletContext().getRealPath("upload/img/shaketicket/"+jwid);
		String uploadDir = upLoadPath + "/upload/img/shaketicket/" +jwid;
        File dirPath = new File(uploadDir);  
        if (!dirPath.exists()) {  
            dirPath.mkdirs();  
        }  
        String sep = System.getProperty("file.separator");  
        File uploadedFile = new File(uploadDir + sep  
                + filename);  
        FileCopyUtils.copy(bytes, uploadedFile);*/
		String filename = OSSBootUtil.upload(uploadify , "upload/img/shaketicket"+jwid);
        j.setObj(filename);
        j.setSuccess(true);
		j.setMsg("保存成功");
	} catch (Exception e) {
		e.printStackTrace();
		j.setSuccess(false);
		j.setMsg("保存失败");
	}
	return j;
}
}

