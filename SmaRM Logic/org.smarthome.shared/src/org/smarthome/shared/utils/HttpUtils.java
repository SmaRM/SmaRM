package org.smarthome.shared.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.smarthome.shared.Constants;

public class HttpUtils {

	public static InputStreamReader openUrl(String url) throws IOException {
		// Create service call
		URL serviceUrl = new URL(url);
		// Create new HTTP connection
		URLConnection urlConnection = serviceUrl.openConnection();
		// Set connection timeout [ms]
		urlConnection.setConnectTimeout(Constants.HTTP_CONNECTION_TIMEOUT_MS);

		// Get content type and charset
		String contentType = urlConnection.getContentType();
		String charset = HttpUtils.getCharset(contentType);
		
		// Open input stream for reading
		InputStream inputStream = urlConnection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
		return inputStreamReader;
	}
	
	public static String readFromUrl(String url) throws IOException {
		// Get input stream
		InputStreamReader inputStreamReader =  openUrl(url);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		
		StringBuffer stringBuffer = new StringBuffer();
		String currentLine = null;
		
		while ( (currentLine = bufferedReader.readLine()) != null ) {
			stringBuffer.append(currentLine);
			stringBuffer.append(Constants.NEWLINE);
		}
		
		return stringBuffer.toString();
	}

	/**
	 * Detect character set from HTTP content-type header field
	 * 
	 * @param contentType
	 *            HTTP content-type header field
	 * @return Detected character set (e.g. UTF-8)
	 */
	public static String getCharset(String contentType) {
		Matcher matcher = Pattern.compile("charset\\s*=\\s*([^ ;]+)").matcher(contentType);
		String charset = "UTF-8";
		if (matcher.find()) {
			charset = matcher.group(1);
		}
		return charset;
	}


}
