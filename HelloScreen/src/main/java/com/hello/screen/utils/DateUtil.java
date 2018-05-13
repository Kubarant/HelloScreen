package com.hello.screen.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {

	public static LocalDateTime dateOfUnixTimestamp(long seconds) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(seconds*1000),ZoneId.systemDefault());
	}
	
	
}
