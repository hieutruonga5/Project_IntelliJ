package com.topgun.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {
	private static String dateFormat1 = "dd/MM/yyyy";
	private static String dateFormat2 = "yyy-MM-dd";

	private static SimpleDateFormat formatter;

	public static Date dateFormat1(String str) {
		formatter = new SimpleDateFormat(dateFormat1);
		Date date = null;
		try {
			date = formatter.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date dateFormat2(String str) {
		formatter = new SimpleDateFormat(dateFormat2);
		Date date = null;
		try {
			date = formatter.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String stringFormat(Date date) {
		return formatter.format(date);
	}

	public static boolean isValidBirthday(String birthday) {
		DateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		sdf.setLenient(false);
		try {
			sdf.parse(birthday);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(isValidBirthday("12/11/1890"));
		kc();
	}

	public static void kc() {
		// set the new date format
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date currentDate = new Date();
		// PLus 24h
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR, 24);

		Date date1 = null;
		Date date2 = null;

		try {
			// calculating the difference b/w startDate and endDate
			String startDate = "2017-07-12 20:40:00";
			String endDate = simpleDateFormat.format(currentDate);

			date1 = simpleDateFormat.parse(startDate);
			date2 = simpleDateFormat.parse(endDate);

			long getDiff = date2.getTime() - date1.getTime();

			// using TimeUnit class from java.util.concurrent package
			long getDaysDiff = TimeUnit.MILLISECONDS.toDays(getDiff);

			System.out.println(
					"Differance between date " + startDate + " and " + endDate + " is " + getDaysDiff + " days.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
