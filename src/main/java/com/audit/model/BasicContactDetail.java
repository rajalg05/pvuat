package com.audit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "basiccontactdetail")
public class BasicContactDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	public String firstName;
	
	public String lastName;
	
	public String whatsappCountryCode;
		
	public String whatsappMobileNumber;
	
	public String email;
	
	public java.util.Date createdTs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstName;
	}

	public void setFirstname(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastName;
	}

	public void setLastname(String lastName) {
		this.lastName = lastName;
	}

	public String getWhatsappcountrycode() {
		return whatsappCountryCode;
	}

	public void setWhatsappcountrycode(String whatsappCountryCode) {
		this.whatsappCountryCode = whatsappCountryCode;
	}
 
	public String getWhatsappMobileNumber() {
		return whatsappMobileNumber;
	}

	public void setWhatsappMobileNumber(String whatsappMobileNumber) {
		this.whatsappMobileNumber = whatsappMobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public java.util.Date getCreatedts() {
		return createdTs;
	}

	public void setCreatedts(java.util.Date createdTs) {
		this.createdTs = createdTs;
	}
}