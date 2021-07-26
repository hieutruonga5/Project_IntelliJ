package com.topgun.utils;

public class Validation {
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

	private static final String PHONE_NUMBER_REGEX = "^[0]{1}[1-9]{1}[0-9]{8}$";

	private static final String BIRTHDAY_REGEX = "^(0[1-9]|1[012])[-/.](0[1-9]|[12][0-9]|3[01])[-/.](19|20)\\d\\d$" ;

	public static boolean emailValidation(String email) {
		return email == null ? false : email.matches(EMAIL_REGEX);
	}

	public static boolean phoneNumberValidation(String phoneNumber) {
		return phoneNumber == null ? false : phoneNumber.matches(PHONE_NUMBER_REGEX);
	}

	public static boolean birthDayValidation(String birthDay) {
		return birthDay == null ? false : birthDay.matches(BIRTHDAY_REGEX);
	}
	public static void main(String[] args) {
		System.out.println("a@123".matches(EMAIL_REGEX));
	}
}
