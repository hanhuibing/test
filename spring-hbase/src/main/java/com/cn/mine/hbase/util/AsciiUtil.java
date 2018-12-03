package com.cn.mine.hbase.util;

public class AsciiUtil {
	public static String modifySub1(String str){
		char[] ca = str.toCharArray();
        ca[ca.length - 1] = (char) (ca[ca.length - 1] - 1);
        return new String(ca);
	}
	
	public static String modifyAdd1(String str){
		char[] ca = str.toCharArray();
        ca[ca.length - 1] = (char) (ca[ca.length - 1] + 1);
        return new String(ca);
	}
}
