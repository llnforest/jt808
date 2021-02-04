package org.yzh.web.commons;


import org.apache.commons.lang3.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 字符串辅助工具类
 * 
 * @author:Lynn
 * 2017年3月27日
 */
public class StringUtil {

	/**
	 * 获取32位guid 
	 * 2016-3-11
	 * @return UUID
	 * author:Lynn
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 该方法用于将传入字符串按照指定的分隔符，
	 * 将该字符串转换成数组
	 * 2017年9月7日
	 * @param str
	 * @param split 注意特殊符号需要转义，如：$ 要传入\\$
	 * @return
	 * author:Lynn
	 */
	public static List<String> string2List(String str, String split) {
		if(StringUtils.isEmpty(str))
			return null;
	    String[] arrary = str.split(split);
	    List<String> list = Arrays.asList(arrary);
	    return list;
	}

	/**
	 * 该方法用于匹配目标字符串与是否与规则字符串相匹配 大多用于验证当前uri与web.xml配置的uri的匹配 
	 * 2017年3月27日
	 * @param pattern 规则字符串（可以含* 如：*.jsp,application*.xml等）
	 * @param source 目标字符串 （验证是否符合上述格式）
	 * @return 
	 * author:Lynn
	 */
	public static boolean uriMatches(String pattern, String source) {
		if ((pattern == null) || (source == null)) {
			return false;
		}
		pattern = pattern.trim();
		source = source.trim();
		if (pattern.endsWith("*")) {
			int length = pattern.length() - 1;
			if ((source.length() >= length)
					&& (pattern.substring(0, length).equals(source.substring(0,
							length)))) {
				return true;
			}
		} else if (pattern.startsWith("*")) {
			int length = pattern.length() - 1;
			if ((source.length() >= length)
					&& (source.endsWith(pattern.substring(1)))) {
				return true;
			}
		} else if (pattern.contains("*")) {
			int start = pattern.indexOf("*");
			int end = pattern.lastIndexOf("*");
			if ((source.startsWith(pattern.substring(0, start)))
					&& (source.endsWith(pattern.substring(end + 1)))) {
				return true;
			}

		} else if (pattern.equals(source)) {
			return true;
		}

		return false;
	}
	
	/**
	 * 对传入的对象进行空值判断，将空对象转换为空字符串
	 * 2018年3月13日
	 * @param obj
	 * @return
	 * author:Lynn
	 */
	public static String convertNull(Object obj){
    	if(obj == null || obj.toString().trim().toLowerCase().equals("null")){
    		return "";
    	}else{
    		return obj.toString();
    	}
	}
	
	/**
	 * 该方法用于根据传入的url和参数串获取正确请求地址
	 * 2018年9月16日
	 * @param url
	 * @param paraString
	 * @return
	 * author:Lynn
	 */
	public static String convertUrl(String url,String paraString){
		if(StringUtils.isNotEmpty(paraString)){
			if(url.indexOf("?")==-1){
				url += "?"+paraString;
			}else {
				url += "&"+paraString;
			}
		}
    	return url;
	}
	
	
	/**
	 * 去除一个url的参数部分
	 * 2018年9月16日
	 * @param url
	 * @return
	 * author:Lynn
	 */
	public static String romoveUrlPara(String url){
			if(url.indexOf("?")==-1){
				return url;
			}else {
				url = url.substring(0, url.indexOf("?"));
				return url;
			}
	}
	/**
	 * 根据传入的filepath获取filename
	 * 2018年9月20日
	 * @param filepath
	 * @return
	 * author:Lynn
	 */
	public static String getFileName(String filepath){
		if(filepath.lastIndexOf("/")==-1){
			return filepath;
		}else {
			filepath = filepath.substring(filepath.lastIndexOf("/")+1);
			return filepath;
		}
	}
	
	/**
	 * 使用js方法eval触发js方法字符串
	 * 2019年1月18日
	 * @param funcName js方法名
	 * @param param 替换值
	 * @return
	 * @author:Lynn
	 */
	public static String getJsEvalFunc(String funcName,String param){
		if(StringUtils.isNotEmpty(funcName) && StringUtils.isNotEmpty(param)){
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			String function = funcName.replace("#", param);
			try {
				return String.valueOf(engine.eval(function));
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return param;
			}
		}else{
			return param;
		}
		
	}

	/**
	 * 判断是否是纯数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		for (int i = 0; i < str.length(); i++){
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}


	public static boolean intArrLookupInt(int[] intArr,int num){
		for(int i:intArr){
			if(i == num){
				return true;
			}
		}
		return false;
	}

}
