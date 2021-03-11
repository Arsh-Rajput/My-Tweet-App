package com.arsh.twitter.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChronologyUtility {
	public static String getCurrentFormattedTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

}
