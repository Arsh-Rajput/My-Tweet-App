package com.arsh.twitter.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

public class FileUtility {
	private static File userExceptionFile;
	private static File tweetExceptionFile;
	private static File loggerFile;
	private static File followExceptionFile;
	static {
		userExceptionFile = new File("UserExceptionFile.txt");
		if (!userExceptionFile.exists())
			try {
				userExceptionFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		tweetExceptionFile = new File("TweetExceptionFile.txt");
		if (!tweetExceptionFile.exists())
			try {
				tweetExceptionFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		loggerFile = new File("LoggerFile.txt");
		if (!loggerFile.exists())
			try {
				loggerFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		followExceptionFile = new File("FollowExceptionFile.txt");
		if (!followExceptionFile.exists()) {
			try {
				followExceptionFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static synchronized boolean writeToFile(File file, String content) {
		try {
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(content);
			bufferedWriter.newLine();
			bufferedWriter.close();
			fileWriter.close();
			return true;
		} catch (Exception ex) {
			System.out.println("Error Writing to file " + file.getName() + ", " + ex);
			return false;
		}
	}

	public static synchronized String readFromFile(File file) {
		try {
			FileReader fileReader = new FileReader(file);
			int c = 0;
			String content = "";
			while ((c = fileReader.read()) != -1) {
				content += (char) c;
			}
			fileReader.close();
			return content;
		} catch (Exception e) {
			System.out.println("Error reading from file " + file.getName() + ", " + e);
			return null;
		}
	}

	public static File getUserExceptionFile() {
		return userExceptionFile;
	}

	public static File getTweetExceptionFile() {
		return tweetExceptionFile;
	}

	public static File getLoggerFile() {
		// TODO Auto-generated method stub
		return loggerFile;
	}

	public static File getFollowExceptionFile() {
		return followExceptionFile;
	}

}
