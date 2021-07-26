package com.topgun.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import com.topgun.exception.BirthdayException;
import com.topgun.exception.EmailException;
import com.topgun.utils.DateTimeUtils;
import com.topgun.utils.Validation;

public abstract class Candidate implements Comparable<Candidate> {
	private String candidateID;
	private String fullName;
	private Date birthDay;
	private String phone;
	private String email;
	protected int candidateType;
	private static int canidate_count;
	private List<Certificate> certificates = new ArrayList<>();

	public Candidate() {
		canidate_count++;
	}

	public Candidate(String candidateID, String fullName, Date birthDay, String phone, String email,
			int candidate_type) {
		this.candidateID = candidateID;
		this.fullName = fullName;
		this.birthDay = birthDay;
		this.phone = phone;
		this.email = email;
		this.candidateType = candidate_type;
	}

	public String getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(String candidateID) {
		this.candidateID = candidateID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCandidateType() {
		return candidateType;
	}

	public void setCandidateType(int candidate_type) {
		this.candidateType = candidate_type;
	}

	public int getCanidate_count() {
		return canidate_count;
	}

	public List<Certificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<Certificate> certificates) {
		this.certificates = certificates;
	}

	public abstract void showMe();

	public void showInfo() {
		System.out.println("Candidate [candidateID=" + candidateID + ", fullName=" + fullName + ", birthDay=" + birthDay
				+ ", phone=" + phone + ", email=" + email + ", candidate_type=" + candidateType + ", certificates="
				+ certificates + "]");
	}

	public void showCount() {
		System.out.println("So ung vien da nhap : " + canidate_count);
	}

	@SuppressWarnings("resource")
	public void inputData() {
		Scanner sc = new Scanner(System.in);
		System.out.print("CandidateID : ");
		candidateID = sc.nextLine();
		System.out.print("FullName : ");
		fullName = sc.nextLine();
		String date;
		String emailInput;
		boolean loop = false;
		do {
			System.out.print("BirthDay : ");
			date = sc.nextLine();
			loop = Validation.birthDayValidation(date);
			try {
				if (!loop) {
					throw new BirthdayException("BirthDay not valid . Input again please !");
				}
			} catch (BirthdayException e) {
				e.getMessageException();

			}
		} while (!loop);
		birthDay = DateTimeUtils.dateFormat1(date);
		System.out.print("Phone : ");
		phone = sc.nextLine();
		do {
			System.out.print("Email : ");
			emailInput = sc.nextLine();
			loop = Validation.emailValidation(emailInput);
			try {
				if (!loop) {
					throw new EmailException("Email not valid . Input again please !");
				}
			} catch (EmailException e) {
				e.getMessageException();
			}
		} while (!loop);
		email = emailInput;
	}

	public void inputCertificate() {
		Certificate certificate = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhap so bang cap : ");
		int n = Integer.parseInt(sc.nextLine());
		for (int i = 0; i < n; i++) {
			certificate = new Certificate();
			System.out.println("---------- Certificate " + i + 1 + " --------");
			System.out.print("CertificatedID : ");
			certificate.setCertificatedID(sc.nextLine());
			System.out.print("CertificateName : ");
			certificate.setCertificateName(sc.nextLine());
			System.out.print("CertificateRank : ");
			certificate.setCertificateRank(sc.nextLine());
			certificates.add(certificate);
			String date;
			boolean loop = false;
			do {
				System.out.print("CertificatedDate : ");
				date = sc.nextLine();
				loop = Validation.birthDayValidation(date);
				try {
					if (!loop) {
						throw new BirthdayException("CertificatedDate not valid . Input again please !");
					}
				} catch (BirthdayException e) {
					e.getMessageException();

				}
			} while (!loop);
			certificate.setCertificatedDate(DateTimeUtils.dateFormat1(date));
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((candidateID == null) ? 0 : candidateID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Candidate other = (Candidate) obj;
		if (candidateID == null) {
			if (other.candidateID != null)
				return false;
		} else if (!candidateID.equals(other.candidateID))
			return false;
		return true;
	}

	@Override
	public int compareTo(Candidate candidate) {
		if (candidateType == candidate.getCandidateType()) {
			return candidate.getBirthDay().compareTo(birthDay);
		} else {
			return candidateType - candidate.candidateType;
		}
	}

	public static void main(String[] args) {
		HashSet<Candidate> h = new HashSet<Candidate>();
		Candidate e1 = new Experience();
		e1.setCandidateID("01");
		e1.setCandidateType(1);
		e1.setBirthDay(new Date("01/01/2010"));
		h.add(e1);
		Candidate e2 = new Experience();
		e2.setCandidateID("02");
		e2.setCandidateType(3);
		e2.setBirthDay(new Date("01/01/2009"));
		h.add(e2);
		Candidate e3 = new Experience();
		e3.setCandidateID("03");
		e3.setCandidateType(2);
		e3.setBirthDay(new Date("01/01/2015"));
		h.add(e3);
		System.out.println(h.size());
		List<Candidate> s = new ArrayList<>(h);
		Collections.sort(s);
		for (Candidate candidate : s) {
			System.out.println(candidate.candidateType + " " + candidate.birthDay);
		}
	}

}
