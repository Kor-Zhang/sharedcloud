package com.sharedcloud.pc.utils;

import java.io.FileNotFoundException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.util.Log4jConfigurer;

/**
 * 封装Log4j
 * @author Kor_Zhang
 *
 */
public class GLogger {
	/**
	 * logger对象
	 */
	private static Logger logger = null;
	private static String fileName = "classpath:conf/pc/config.properties";
	
	public String getFileName() {
		return fileName;
	}
	/**
	 * 修改log4j的配置文件路径；并且初始化log4j；
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
		try {
			Log4jConfigurer.initLogging(this.fileName);
			logger = Logger.getLogger(GLogger.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	/**
	 * 静态加载配置文件和初始化log4j
	 */
	static{
		try {
			Log4jConfigurer.initLogging(fileName);
			logger = Logger.getLogger(GLogger.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
	/**
	 * log4j输出日志
	 * @param msg
	 */
	public static void info(Object msg){
		logger.info(msg);
	}
}
