package com.sharedcloud.pc.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;


/**
 * sharedcloud项目通用工具 1.0
 * 
 * @author Kor_Zhang
 * 
 */
public class GUtils {
	/**
	 * 写入文件到本地磁盘
	 * @param sourceFile
	 * @param targetFile
	 */
	public void writeFileToDisk(File sourceFile,File targetFile){
		try {
			//获取文件流
			FileOutputStream fos = new FileOutputStream(targetFile);
			//获取数据
			byte[] data = IOUtils.toByteArray(new FileInputStream(sourceFile));
			//写入
			IOUtils.write(data, fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 写入文件到本地磁盘
	 * @param sourceFile
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public File writeFileToDisk(File sourceFile,String filePath,String fileName){
		File targetFile = null;
		try {
			//获取文件流
			targetFile = new File(filePath,fileName);
			
			//写入操作
			writeFileToDisk(sourceFile, targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return targetFile;
	}
	/**
	 * 字符串转化为Timestamp
	 * @param dateStr
	 * @return
	 */
	public static Timestamp stringToTimestamp(String dateStr) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		try {
			Date date = sdf.parse(dateStr);
			date.getTime();
			cal.setTime(date);
			return new Timestamp(cal.getTimeInMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}

		cal.setTime(new Date());
		return new Timestamp(cal.getTimeInMillis());
	}
	
	/**
	 * 获取文件名的后缀
	 * @param fileName：文件名，如d:\zhangke\javaee作业.zip或javaee作业.zip
	 * @return
	 * @throws Exception
	 */
	public static String getFileNameExt(String fileName) throws Exception {
		//获取文件后缀
		String fileExt = "";
		String[] strs = fileName.split("\\.");
		if (strs != null && strs.length >= 2) {
			fileExt = strs[strs.length - 1];
		}
		return fileExt;
	}
	/**
	 * 获取除去文件后缀的文件名
	 * @param fileName：文件名，如javaee.zip
	 * @return：javaee
	 * @throws Exception
	 */
	public static String getFileNameWihoutExt(String fileName) throws Exception {
		//获取文件后缀
		String fileNameWithoutExt = "";
		String[] strs = fileName.split("\\.");
		if (strs != null && strs.length >= 2) {
			fileNameWithoutExt = strs[0];
		}
		return fileNameWithoutExt;
	}
	/**
	 * 取得文件大小
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File file) throws Exception {
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			return fis.available();
		}
		return 0; 
	}
	/**
	 * copy文件内容
	 * @param src：源文件
	 * @param dst：目标文件
	 */
	public static void copyFiles(File src, File dst) {
		int BUFFER_SIZE = 2*1024;
		InputStream in = null;
		OutputStream out = null;
		try {
			if (dst.exists()) {
				out = new BufferedOutputStream(new FileOutputStream(dst, true),
						BUFFER_SIZE);
			} else {
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
			}
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);

			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 删除指定文件
	 * @param file
	 * @return
	 */
	public static Boolean deleteFile(File file){
		try{
			if(file!=null&&file.exists()){
				file.delete();
				return true;
			}
			return false;
		}catch (Exception e) {
			return false;
		}
	}
	/**
	 * 加载properties
	 * @param propsPath：propsPath开头不加斜杠
	 * @return
	 * @throws Exception
	 */
	public static Properties loadProps(String propsPath) {
		
		// 申明初始化properties
		Properties props = new Properties();
		// 获取类路径下的properties文件
		InputStream inStream = GUtils.class.getClassLoader()
				.getResourceAsStream(propsPath);
		try {
			props.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 关闭流
		GUtils.closeStream(inStream, null);
		// 返回参数
		return props;
	}
	/**
	 * 关闭流
	 * 
	 * @param is
	 * @param os
	 */
	public static void closeStream(InputStream is, OutputStream os) {
		/**
		 * 1.判断流是否为null 2.执行操作
		 */
		// 1.判断流是否为null
		if (is != null) {
			// 2.执行操作
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 1.判断流是否为null
		if (os != null) {
			// 2.执行操作
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 更具long型的生日获取年龄
	 * 
	 * @param birthdayTime
	 * @return
	 */
	public static int getAgeByYears(Long birthdayTime) {
		Integer age = new Date().getYear() - new Date(birthdayTime).getYear();
		if (age <= 0) {
			age = -1;
		}
		return age;
	}

	/**
	 * 验证正则
	 * 
	 * @param data
	 *            ：待验证数据
	 * @param reg
	 *            ：正则表达式
	 * @return
	 */
	public static Boolean check(String data, String reg) {
		Pattern regex = Pattern.compile(reg);
		Matcher matcher = regex.matcher(data);
		return matcher.matches();
	}


	/**
	 * 复制一个list中的元素到另一个list
	 * 
	 * @param list
	 * @return
	 */
	public static <T> List<T> copyProList(Class<T> targetClazz,
			List<?> sourceList) {
		// 验证待复制的list有效性
		if (null == sourceList) {
			return null;
		}
		List<T> newList = getList();
		if (sourceList.size() == 0) {
			return newList;
		}
		// 遍历复制list中对象属性
		int index = 0;
		while (index < sourceList.size()) {
			Object obj = null;
			try {
				obj = targetClazz.newInstance();
				BeanUtils.copyProperties(sourceList.get(index),obj );
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			newList.add((T) obj);
			index++;
		}
		return newList;
	}

	/**
	 * 快速获取一个ArrayList实例
	 * 
	 * @return
	 */
	public static <T> List<T> getList() {
		return new ArrayList<T>();
	}

	/**
	 * 快速获取一个HashMap实例
	 * 
	 * @return
	 */
	public static <K, V> Map<K, V> getMap() {
		return new HashMap<K, V>();
	}

	/**
	 * 快速获取一个HashMap实例，并且填入参数
	 * 
	 * @return
	 */
	public static <K, V> Map<K, V> getMap(K[] keys, V[] vals) {
		Map<K, V> map = getMap();
		if (null == keys || null == vals || 0 == keys.length
				|| 0 == vals.length || keys.length != vals.length) {
			return map;
		}
		int i = 0;
		while (i < keys.length) {
			map.put(keys[i], vals[i]);
			i++;
		}
		return map;
	}
	/**
	 * 加密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String e(String inputText) {
		return md5(inputText);
	}

	/**
	 * 二次加密，应该破解不了了吧？
	 * 
	 * @param inputText
	 * @return
	 */
	public static String md5AndSha(String inputText) {
		return sha(md5(inputText));
	}

	/**
	 * md5加密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String md5(String inputText) {
		return encrypt(inputText, "md5");
	}

	/**
	 * sha加密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String sha(String inputText) {
		return encrypt(inputText, "sha-1");
	}

	/**
	 * md5或者sha-1加密
	 * 
	 * @param inputText
	 *            要加密的内容
	 * @param algorithmName
	 *            加密算法名称：md5或者sha-1，不区分大小写
	 * @return
	 */
	private static String encrypt(String inputText, String algorithmName) {
		if (inputText == null || "".equals(inputText.trim())) {
			throw new IllegalArgumentException("请输入要加密的内容");
		}
		if (algorithmName == null || "".equals(algorithmName.trim())) {
			algorithmName = "md5";
		}
		String encryptText = null;
		try {
			MessageDigest m = MessageDigest.getInstance(algorithmName);
			m.update(inputText.getBytes("UTF8"));
			byte s[] = m.digest();
			// m.digest(inputText.getBytes("UTF8"));
			return hex(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptText;
	}

	/**
	 * 返回十六进制字符串
	 * 
	 * @param arr
	 * @return
	 */
	private static String hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}
}
