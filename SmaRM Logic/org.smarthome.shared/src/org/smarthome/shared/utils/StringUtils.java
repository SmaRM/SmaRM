package org.smarthome.shared.utils;

public class StringUtils {
	public static String replaceNewLineCharacters(String content, String replacement) {
		String result = content.replaceAll("\n", replacement);
		result = result.replaceAll("\\n", replacement);
		result = result.replaceAll("\\\\n", replacement);
		result = result.replaceAll("\r", replacement);
		result = result.replaceAll("\\r", replacement);
		result = result.replaceAll("\\\\r", replacement);
		result = result.replaceAll("<br\\s*/>", replacement);
		
		while (result.contains("  "))
			result = result.replaceAll("  ", " ");
		
		return result.trim();
	}
	
	public static String replaceNewLineCharacters(String content) {
		return replaceNewLineCharacters(content, " ");
	}
	
	public static String removeQuotes(String content) {
		if (content.startsWith("\"")) content = content.substring(1);
		if (content.endsWith("\"")) content = content.substring(0, content.length() - 1);
		return content;
	}
}
