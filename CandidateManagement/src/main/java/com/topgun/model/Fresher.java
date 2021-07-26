package com.topgun.model;

import java.util.Date;
import java.util.Scanner;

import com.topgun.exception.BirthdayException;
import com.topgun.utils.DateTimeUtils;
import com.topgun.utils.Validation;

public class Fresher extends Candidate {
	private Date graduationDate;
	private String graduationRank;
	private String education;

	public Fresher() {
		super();
		candidateType = 1;
	}

	public Fresher(String candidateID, String fullName, Date birthDay, String phone, String email, int candidate_type,
			Date graduationDate, String graduationRank, String Education) {
		super(candidateID, fullName, birthDay, phone, email, candidate_type);
		this.graduationDate = graduationDate;
		this.graduationRank = graduationRank;
		this.education = Education;
	}

	public Date getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}

	public String getGraduationRank() {
		return graduationRank;
	}

	public void setGraduationRank(String graduationRank) {
		this.graduationRank = graduationRank;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Override
	public void showMe() {
		System.out.println("Fresher [graduationDate=" + graduationDate + ", graduationRank=" + graduationRank
				+ ", Education=" + education + "]");

	}

	@SuppressWarnings("resource")
	@Override
	public void inputData() {
		Scanner sc = new Scanner(System.in);
		super.inputData();
		System.out.print("GraduationRank : ");
		graduationRank = sc.nextLine();
		System.out.print("UniversityName : ");
		education = sc.nextLine();
		System.out.print("UniversityName : ");
		education = sc.nextLine();
		String date;
		boolean loop = false;
		do {
			System.out.print("GraduationDate : ");
			date = sc.nextLine();
			loop = Validation.birthDayValidation(date);
			try {
				if (!loop) {
					throw new BirthdayException("GraduationDate not valid . Input again please !");
				}
			} catch (BirthdayException e) {
				e.getMessageException();

			}
		} while (!loop);
		graduationDate = DateTimeUtils.dateFormat1(date);
	}

}
