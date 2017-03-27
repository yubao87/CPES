package com.atguigu.cpes.util;

public class StringUtil {
	public static boolean isEmpty(String inStr) {
		return inStr == null || "".equals(inStr.trim());
	}

	public static boolean isNotEmpty(String inStr) {
		return !isEmpty(inStr);
	}

}
