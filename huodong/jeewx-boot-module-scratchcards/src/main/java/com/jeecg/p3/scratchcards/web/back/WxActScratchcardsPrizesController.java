package com.jeecg.p3.scratchcards.web.back;

import com.jeecg.p3.baseApi.util.OSSBootUtil;
import com.jeecg.p3.scratchcards.def.ScratchcardsProperties;
import com.jeecg.p3.scratchcards.entity.WxActScratchcardsPrizes;
import com.jeecg.p3.scratchcards.service.WxActScratchcardsPrizesService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

 /**
 * 描述：</b>WxActScratchcardsPrizesController<br>奖品表
 * @author junfeng.zhou
 * @since：2016年07月13日 18时42分24秒 星期三 
 * @version:1.0
 */
@Controller
@RequestMapping("/scratchcards/back/wxActScratchcardsPrizes")
public class WxActScratchcardsPrizesController extends BaseController{
  @Autowired
  private WxActScratchcardsPrizesService wxActScratchcardsPrizesService;

/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute WxActScratchcardsPrizes query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(required = true, value = "showReturnFlag" ) String showReturnFlag) throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "scratchcards/back/wxActScratchcardsPrizes-list.vm";
	 	try {
	 		PageQuery<WxActScratchcardsPrizes> pageQuery = new PageQuery<WxActScratchcardsPrizes>();
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
			velocityContext.put("wxActScratchcardsPrizes",query);
			//update-begin--Author:zhangweijian  Date: 20180319 for：增加一个返回按钮是否显示的字段
			velocityContext.put("showReturnFlag",showReturnFlag);
			//update-end--Author:zhangweijian  Date: 20180319 for：增加一个返回按钮是否显示的字段
			pageQuery.setQuery(query);
			velocityContext.put("doMain", "/upload/img/scratchcards");
			velocityContext.put("pageInfos",SystemTools.convertPaginatedList(wxActScratchcardsPrizesService.queryPageList(pageQuery)));
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
public void wxActScratchcardsPrizesDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "scratchcards/back/wxActScratchcardsPrizes-detail.vm";
		WxActScratchcardsPrizes wxActScratchcardsPrizes = wxActScratchcardsPrizesService.queryById(id);
		velocityContext.put("wxActScratchcardsPrizes",wxActScratchcardsPrizes);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String viewName = "scratchcards/back/wxActScratchcardsPrizes-add.vm";
	 velocityContext.put("doMain", "/upload/img/scratchcards");
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute WxActScratchcardsPrizes wxActScratchcardsPrizes){
	AjaxJson j = new AjaxJson();
	try {
		//update-begin-alex Date:20170316 for:保存奖品奖项时记录创建人和当前jwid
		String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
		String createBy = ContextHolderUtils.getSession().getAttribute("system_userid").toString();
		wxActScratchcardsPrizes.setCreateBy(createBy);
		wxActScratchcardsPrizes.setJwid(jwid);
		//update-end-alex Date:20170316 for:保存奖品奖项时记录创建人和当前jwid
		//update-begin-sunkai Date:20180928 for:判断是否存在该名称奖品
		String flag = findPrizesByName(wxActScratchcardsPrizes.getName());
		if(StringUtils.isEmpty(flag) || flag.equals(wxActScratchcardsPrizes.getId())){
			wxActScratchcardsPrizesService.doAdd(wxActScratchcardsPrizes);
			j.setMsg("保存成功");
		}else{
			j.setSuccess(false);
			j.setMsg("奖品名称已存在，请重新输入");
		}
		//update-begin-sunkai Date:20180928 for:判断是否存在该名称奖品
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
		 WxActScratchcardsPrizes wxActScratchcardsPrizes = wxActScratchcardsPrizesService.queryById(id);
		 velocityContext.put("wxActScratchcardsPrizes",wxActScratchcardsPrizes);
		 String viewName = "scratchcards/back/wxActScratchcardsPrizes-edit.vm";
		 velocityContext.put("doMain", "/upload/img/scratchcards");
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute WxActScratchcardsPrizes wxActScratchcardsPrizes){
	AjaxJson j = new AjaxJson();
	try {
		//update-begin-sunkai Date:20180928 for:判断是否存在该名称奖品
		String flag = findPrizesByName(wxActScratchcardsPrizes.getName());
		if(StringUtils.isEmpty(flag) || flag.equals(wxActScratchcardsPrizes.getId())){
			wxActScratchcardsPrizesService.doEdit(wxActScratchcardsPrizes);
			j.setMsg("编辑成功");
		}else{
			j.setSuccess(false);
			j.setMsg("奖品名称已存在，请重新输入");
		}
		//update-end-sunkai Date:20180928 for:判断是否存在该名称奖品
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
			//update-begin--Author:zhangweijian  Date: 20180330 for：判断奖品是否被使用
			//判断奖品是否被使用
			Boolean used=wxActScratchcardsPrizesService.validUsed(id);
			if(used){
				j.setSuccess(false);
				j.setMsg("该奖项已经被活动使用，不能删除");
			}else{
				wxActScratchcardsPrizesService.doDelete(id);
				j.setMsg("删除成功");
			}
			//update-end--Author:zhangweijian  Date: 20180330 for：判断奖品是否被使用
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}
//update-begin-sunkai Date:20180928 for:判断是否存在该名称奖品
public String findPrizesByName(String name){
	String jwid =  ContextHolderUtils.getSession().getAttribute("jwid").toString();
	String createBy = "";
	String defaultJwid = ScratchcardsProperties.defaultJwid;
	if(defaultJwid.equals(jwid)){
		createBy = ContextHolderUtils.getSession().getAttribute("system_userid").toString();
	}
	//判断数据库是否存在奖品名称
	List<WxActScratchcardsPrizes> queryPrizesByName =wxActScratchcardsPrizesService.queryPrizesByName(jwid, createBy, name);
	if(queryPrizesByName.size()>0){
		return queryPrizesByName.get(0).getId();
	}else{
		return "";
	}
}
//update-end-sunkai Date:20180928 for:判断是否存在该名称奖品
/**
 * 上传图片
 * 
 */
@RequestMapping(value = "doUpload", method = { RequestMethod.POST,RequestMethod.GET })
@ResponseBody
public AjaxJson doUpload(MultipartHttpServletRequest request,
    HttpServletResponse response) {
  AjaxJson j = new AjaxJson();
  try {
    MultipartFile uploadify = request.getFile("file");
    /*String realFilename=uploadify.getOriginalFilename();
    String fileExtension = realFilename.substring(realFilename.lastIndexOf("."));
    String filename=UUID.randomUUID().toString().replace("-", "")+fileExtension;
	String uploadDir = upLoadPath + "/upload/img/scratchcards/";
	//压缩上传方式
	  File dirPath = new File(uploadDir);
	  if (!dirPath.exists()) {
		  dirPath.mkdirs();
	  }
	  String sep = System.getProperty("file.separator");
	  File uploadedFile = new File(uploadDir + sep
			  + filename);
	  OutputStream out = new FileOutputStream(dirPath + sep+ filename);
	  ImgUtil.scale(uploadify.getInputStream(), out, 0.7f);*/
	  String sep = System.getProperty("file.separator");
	  String filename = OSSBootUtil.upload(uploadify , "upload/img/scratchcards");
    j.setObj(filename);
    j.setSuccess(true);
    j.setMsg("保存成功");
  } catch (Exception e) {
    j.setSuccess(false);
    j.setMsg("保存失败");
    e.printStackTrace();
  }
  return j;
}
}

