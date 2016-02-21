package com.nio.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * @author zoushaohua
 * @version 创建时间：2016年1月14日 上午10:35:07 
 * 类说明 
 */
public class Utils {
	
	public static void formatPrint(String msg) {
		StringBuilder info = new StringBuilder();

		info.append("当前执行[");
		info.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		info.append("]------->");
		info.append(msg);

		System.out.println(info.toString());
	}
	
	public static boolean isEmpty(String str){
		if(str == null || "".equals(str.trim()))
			return true ;
		
		return false ;
	}

}
