package com.topgun.model;

import java.util.Date;
import java.util.Scanner;

public class Intern extends Candidate {
	private String majors;
	private String semester;
	private String universityName;

	public Intern() {
		super();
		candidateType = 2;
	}

	public Intern(String candidateID, String fullName, Date birthDay, String phone, String email, int candidate_type,
			String majors, String semester, String universityName) {
		super(candidateID, fullName, birthDay, phone, email, candidate_type);
		this.majors = majors;
		this.semester = semester;
		this.universityName = universityName;
	}

	public String getMajors() {
		return majors;
	}

	public void setMajors(String majors) {
		this.majors = majors;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	@Override
	public void showMe() {
		System.out.println(
				"Intern [majors=" + majors + ", semester=" + semester + ", universityName=" + universityName + "]");

	}

	@SuppressWarnings("resource")
	@Override
	public void inputData() {
		Scanner sc = new Scanner(System.in);
		super.inputData();
		System.out.print("Majors : ");
		majors = sc.nextLine();
		System.out.print("Semester : ");
		semester = sc.nextLine();
		System.out.print("universityName : ");
		universityName = sc.nextLine();

	}
}
