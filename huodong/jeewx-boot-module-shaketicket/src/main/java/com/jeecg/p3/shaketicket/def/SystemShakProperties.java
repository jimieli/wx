package com.jeecg.p3.shaketicket.def;

import org.jeecgframework.p3.core.util.PropertiesUtil;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemShakProperties {
	public static String domain;
	public final static String defaultJwid;
	/**签名密钥*/
	public final static String SIGN_KEY;
	/**活动文本的活动ID*/
	public final static String oldActCode;


	static {
		PropertiesUtil globalp = new PropertiesUtil("jeewx.properties");
		domain = globalp.readProperty("oAuthDomain");
		SIGN_KEY = globalp.readProperty("oAuthSignKey");
		defaultJwid = globalp.readProperty("defaultJwid");
	}
	
	static {
		PropertiesUtil globalp = new PropertiesUtil("shaketicket.properties");
		oldActCode= globalp.readProperty("txtActId");
	}
	
	

	
}
