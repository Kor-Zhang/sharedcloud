package com.sharedcloud.pc.utils;

import java.util.Properties;


/**
 * 获取项目配置文件
 * @author Kor_Zhang
 *
 */
public class GConfig {
	
	private static Properties props = null;
	private static String propsPath = "conf/pc/config.properties";
	static{
		props = GUtils.loadProps(propsPath);
	}
	public static Properties getProps() {
		return props;
	}

	public static void setProps(Properties props) {
		GConfig.props = props;
	}

	public static String getPropsPath() {
		return GConfig.propsPath;
	}
	public static void setPropsPath(String propsPath) {
		GConfig.propsPath = propsPath;
		props = GUtils.loadProps(GConfig.propsPath);
	}
	
	
	/**
	 * 获取属性文件值
	 * @param key
	 * @return
	 */
	public static Object getValue(String key){
		return getProps().getProperty(key);
	}
	
	 
}
