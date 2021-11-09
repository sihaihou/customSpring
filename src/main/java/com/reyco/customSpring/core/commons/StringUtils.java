package com.reyco.customSpring.core.commons;

import java.util.Collection;

public class StringUtils {
	
	public static String[] toStringArray(Collection<String> collection) {
		return collection.toArray(new String[0]);
	}
	
	
	public static boolean hasText(String str) {
		return (str != null && !str.isEmpty() && containsText(str));
	}
	private static boolean containsText(CharSequence str) {
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
}
