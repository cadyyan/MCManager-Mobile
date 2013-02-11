package com.theisleoffavalon.mcmanager_mobile.helpers;

public class Convert {

	public static String formatTime(Long milliseconds) {

		int days = (int) Math.floor(milliseconds / (1000 * 60 * 60 * 24));
		milliseconds -= days * 1000 * 60 * 60 * 24;

		int hours = (int) Math.floor(milliseconds / (1000 * 60 * 60));
		milliseconds -= hours * 1000 * 60 * 60;

		int minutes = (int) Math.floor(milliseconds / (1000 * 60));
		milliseconds -= minutes * 1000 * 60;

		int seconds = (int) Math.floor(milliseconds / 1000);
		milliseconds -= seconds * 1000;

		String format = days > 0 ? (days + " days ") : "";
		format += pad(hours, -1) + ':' + pad(minutes, -1) + ':'
				+ pad(seconds, -1) + ':' + pad(milliseconds.intValue(), 3);

		return format;
	}

	public static String pad(int value, int zeroes) {
		if (zeroes == -1) {
			zeroes = 2;
		}

		String ret = "";
		int digits = (value == 0 ? 0 : (int) Math.floor(Math.log(value)
				/ Math.log(10))) + 1;

		for (int i = digits; i != zeroes; i++) {
			ret += '0';
		}

		return ret + value;
	}

	public static String formatMemory(long bytes) {
		long gigabytes = bytes / (1024 * 1024 * 1024);
		if (gigabytes >= 1) {
			String format = (Math.round(gigabytes * 100) / 100) + " GB";
			return format;
		}

		long megabytes = bytes / (1024 * 1024);
		if (megabytes >= 1) {
			String format = (Math.round(megabytes * 100) / 100) + " MB";
			return format;
		}

		long kilobytes = bytes / (1024);
		if (kilobytes >= 1) {
			String format = (Math.round(kilobytes * 100) / 100) + " KB";
			return format;
		}

		return bytes + " bytes";
	}
}
