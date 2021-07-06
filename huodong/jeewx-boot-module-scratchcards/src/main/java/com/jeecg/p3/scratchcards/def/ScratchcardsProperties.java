package com.jeecg.p3.scratchcards.def;

import org.jeecgframework.p3.core.util.PropertiesUtil;

public class ScratchcardsProperties {
	//默认活动文本模板ID
	public static final String TXT_ACTID_KEY;
	//默认公众号
	public final static String defaultJwid;
	//域名
	public final static String domain;
	
	static{
		PropertiesUtil globalp = new PropertiesUtil("jeewx.properties");
		defaultJwid = globalp.readProperty("defaultJwid");
		domain = globalp.readProperty("oAuthDomain");
		
  		PropertiesUtil p = new PropertiesUtil("scratchcards.properties");
  		TXT_ACTID_KEY =p.readProperty("txtActId");	
	}
}
