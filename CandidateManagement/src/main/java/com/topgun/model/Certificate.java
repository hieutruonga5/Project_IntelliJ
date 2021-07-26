package com.topgun.model;

import java.util.Date;

public class Certificate {
	private String certificatedID;
	private String certificateName;
	private String certificateRank;
	private Date certificatedDate;

	public Certificate() {
	}

	public Certificate(String certificatedID, String certificateName, String certificateRank, Date certificatedDate) {
		this.certificatedID = certificatedID;
		this.certificateName = certificateName;
		this.certificateRank = certificateRank;
		this.certificatedDate = certificatedDate;
	}

	public String getCertificatedID() {
		return certificatedID;
	}

	public void setCertificatedID(String certificatedID) {
		this.certificatedID = certificatedID;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getCertificateRank() {
		return certificateRank;
	}

	public void setCertificateRank(String certificateRank) {
		this.certificateRank = certificateRank;
	}

	public Date getCertificatedDate() {
		return certificatedDate;
	}

	public void setCertificatedDate(Date certificatedDate) {
		this.certificatedDate = certificatedDate;
	}

}
