package com.audit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	public String addressLine1;
	
	public String streetAddress1;
	
	public String streetAddress2;
	
	public String city;
	
	public String state;
	
	public String postalCode;
	
	public String country;
	
	public java.util.Date createdTs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddressline1() {
		return addressLine1;
	}

	public void setAddressline1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getStreetaddress1() {
		return streetAddress1;
	}

	public void setStreetaddress1(String streetAddress1) {
		this.streetAddress1 = streetAddress1;
	}

	public String getStreetaddress2() {
		return streetAddress2;
	}

	public void setStreetaddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalcode() {
		return postalCode;
	}

	public void setPostalcode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public java.util.Date getCreatedts() {
		return createdTs;
	}

	public void setCreatedts(java.util.Date createdTs) {
		this.createdTs = createdTs;
	}
}