package com.audit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kyc")
public class KYC {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	public String firstKycId;
	
	public String firstKycType;
	
	public String secondKycId;
	
	public String secondKycType;
	
	@Column(length = 1000)
	public byte[] firstKycFrontImagePath;
	
	@Column(length = 1000)
	public byte[] firstKycBackImagePath;
	
	@Column(length = 1000)
	public byte[] secondKycFrontImagePath;
	
	@Column(length = 1000)
	public byte[] secondKycBackImagePath;
	
	public java.util.Date createdTs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstkycid() {
		return firstKycId;
	}

	public void setFirstkycid(String firstKycId) {
		this.firstKycId = firstKycId;
	}

	public String getFirstkyctype() {
		return firstKycType;
	}

	public void setFirstkyctype(String firstKycType) {
		this.firstKycType = firstKycType;
	}

	public String getSecondkycid() {
		return secondKycId;
	}

	public void setSecondkycid(String secondKycId) {
		this.secondKycId = secondKycId;
	}

	public String getSecondkyctype() {
		return secondKycType;
	}

	public void setSecondkyctype(String secondKycType) {
		this.secondKycType = secondKycType;
	}
 
	public byte[] getFirstKycFrontImagePath() {
		return firstKycFrontImagePath;
	}

	public void setFirstKycFrontImagePath(byte[] firstKycFrontImagePath) {
		this.firstKycFrontImagePath = firstKycFrontImagePath;
	}

	public byte[] getFirstKycBackImagePath() {
		return firstKycBackImagePath;
	}

	public void setFirstKycBackImagePath(byte[] firstKycBackImagePath) {
		this.firstKycBackImagePath = firstKycBackImagePath;
	}

	public byte[] getSecondKycFrontImagePath() {
		return secondKycFrontImagePath;
	}

	public void setSecondKycFrontImagePath(byte[] secondKycFrontImagePath) {
		this.secondKycFrontImagePath = secondKycFrontImagePath;
	}

	public byte[] getSecondKycBackImagePath() {
		return secondKycBackImagePath;
	}

	public void setSecondKycBackImagePath(byte[] secondKycBackImagePath) {
		this.secondKycBackImagePath = secondKycBackImagePath;
	}

	public java.util.Date getCreatedts() {
		return createdTs;
	}

	public void setCreatedts(java.util.Date createdTs) {
		this.createdTs = createdTs;
	}
}