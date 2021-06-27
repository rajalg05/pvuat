package com.audit.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "associate")
public class Associate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@OneToOne( cascade = CascadeType.ALL) 
	public BasicContactDetail basicContactDetail;  
	
	@OneToOne( cascade = CascadeType.ALL)  
	public KYC kyc;
	
	@OneToOne( cascade = CascadeType.ALL)  
	public Address address;
		
	public java.util.Date createdTs;
	
	public java.util.Date updatedTs;

	public Associate() {
		super();
	}

	public Associate(BasicContactDetail basicContactDetail, KYC kyc, Address address, Date createdTs, Date updatedTs) {
		super();
		this.basicContactDetail = basicContactDetail;
		this.kyc = kyc;
		this.address = address;
		this.createdTs = createdTs;
		this.updatedTs = updatedTs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
 
	public java.util.Date getCreatedts() {
		return createdTs;
	}

	public void setCreatedts(java.util.Date createdTs) {
		this.createdTs = createdTs;
	}

	public java.util.Date getUpdatedts() {
		return updatedTs;
	}

	public void setUpdatedts(java.util.Date updatedTs) {
		this.updatedTs = updatedTs;
	}
}