package com.topgun.model;

import java.util.Date;
import java.util.Scanner;

public class Experience extends Candidate {
	private int expInYear;
	private String proSkill;

	public Experience() {
		super();
		candidateType = 0;
	}

	public Experience(String candidateID, String fullName, Date birthDay, String phone, String email,
			int candidate_type, int expInYear, String proSkill) {
		super(candidateID, fullName, birthDay, phone, email, candidate_type);
		this.expInYear = expInYear;
		this.proSkill = proSkill;
	}

	public int getExpInYear() {
		return expInYear;
	}

	public void setExpInYear(int expInYear) {
		this.expInYear = expInYear;
	}

	public String getProSkill() {
		return proSkill;
	}

	public void setProSkill(String proSkill) {
		this.proSkill = proSkill;
	}

	@Override
	public void showMe() {
		System.out.println("Experience [expInYear=" + expInYear + ", proSkill=" + proSkill + "]");

	}

	@SuppressWarnings("resource")
	@Override
	public void inputData() {
		Scanner sc = new Scanner(System.in);
		super.inputData();
		System.out.print("ExpInYear : ");
		expInYear = sc.nextInt();
		sc.nextLine();
		System.out.print("ProSkill : ");
		proSkill = sc.nextLine();

	}

}
