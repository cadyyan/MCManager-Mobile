/*
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE Version 2, December 2004
 * 
 * Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
 * 
 * Everyone is permitted to copy and distribute verbatim or modified copies of
 * this license document, and changing it is allowed as long as the name is
 * changed.
 * 
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE TERMS AND CONDITIONS FOR COPYING,
 * DISTRIBUTION AND MODIFICATION
 * 
 * 0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package com.theisleoffavalon.mcmanager.mobile.helpers;

/**
 * Class that converts the time and bytes into readable text.
 * 
 * @author eberta
 * @modified 2/14/13
 */
public class Convert {

	/**
	 * Converts time from milliseconds to Hours:Mins:Secs
	 * 
	 * @param milliseconds
	 *            Time in milliseconds
	 * @return Returns a String containing the time in a readable format.
	 */
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

	/**
	 * Method to pad a string with zeros
	 * 
	 * @param value
	 *            Value to pad
	 * @param zeroes
	 *            Number of zeros or place holders.
	 * @return Returns a string of readable text based on the padding.
	 */
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

	/**
	 * Method to format bytes into human readable text.
	 * 
	 * @param bytes
	 *            Bytes long to convert to string
	 * @return Returns a string with the bytes now in readable format.
	 */
	public static String formatMemory(long bytes) {
		double gigabytes = bytes / (1024.00 * 1024 * 1024);
		if (gigabytes >= 1) {
			String format = (Math.round(gigabytes * 100) / 100.00) + " GB";
			return format;
		}

		double megabytes = bytes / (1024 * 1024.00);
		if (megabytes >= 1) {
			String format = (Math.round(megabytes * 100) / 100.00) + " MB";
			return format;
		}

		double kilobytes = bytes / (1024.00);
		if (kilobytes >= 1) {
			String format = (Math.round(kilobytes * 100) / 100.00) + " KB";
			return format;
		}

		return bytes + " bytes";
	}
}
