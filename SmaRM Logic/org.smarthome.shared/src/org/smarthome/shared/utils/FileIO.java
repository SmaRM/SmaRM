package org.smarthome.shared.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.smarthome.shared.Constants;

public class FileIO {

	public static String readFileContent(File file) throws IOException {
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		StringBuffer stringBuffer = new StringBuffer();
		String currentLine = null;
		while ((currentLine = bufferedReader.readLine()) != null) {
			stringBuffer.append(currentLine);
			stringBuffer.append(Constants.NEWLINE);
		}
		bufferedReader.close();

		return stringBuffer.toString();
	}

	public static String readFileContent(String filepath) throws IOException {
		return readFileContent(new File(filepath));
	}

	public static void writeFileContent(File file, String content) throws IOException {
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		bufferedWriter.write(content);

		bufferedWriter.close();
	}

	public static void writeFileContent(String filepath, String content) throws IOException {
		writeFileContent(new File(filepath), content);
	}
}
