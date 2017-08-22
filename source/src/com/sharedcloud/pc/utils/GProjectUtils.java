package com.sharedcloud.pc.utils;
/**
 * 只是用于本项目的工具包
 * @author XiaoYu
 *
 */
public class GProjectUtils {
	
	/**
	 * 获取项目域名，即http://localhost:8080/sharedcloud;
	 * @param url:webProjcetPath为占位符，将被替换为域名；如果url为空，那么返回项目的域名
	 * @return
	 */
	public static String getWebProjectPath(String url){
		String path = GConfig.getProps().getProperty("www")+GActionUtils.getRequest().getContextPath();
		if(null != url){
			url = url.replace("webProjectPath",path);
		}else{
			url = path;
		}
		return url;
	}
}
