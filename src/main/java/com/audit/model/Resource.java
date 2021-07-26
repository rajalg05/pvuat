package com.audit.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "resource")
public class Resource {
	 
	public Resource() {
		super();
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	public String allocated;
	
	@OneToOne( cascade = CascadeType.ALL) 
	public BasicContactDetail basicContactDetail;  
	
	@OneToOne( cascade = CascadeType.ALL) 
	public KYC kyc;
	
	@OneToOne( cascade = CascadeType.ALL)
	public Address address;
	
	@OneToMany(mappedBy = "resource")
	Set<AuditAllocation> auditAllocations;
	
	public java.util.Date dateOfBirth;
	
	public String qualification;
	
	public String excelSkills;
	
	public String stockAuditExp;
	
	public String resourceType;
	
	public String bike; 
	
	public String paymentType;
	
	public int paymentAmount;
	
	public java.util.Date createdTs;
	
	public java.util.Date updatedTs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	 

	public String getBike() {
		return bike;
	}

	public void setBike(String bike) {
		this.bike = bike;
	}

	public String getAllocated() {
		return allocated;
	}

	public void setAllocated(String allocated) {
		this.allocated = allocated;
	}

	public BasicContactDetail getBasicContactDetail() {
		return basicContactDetail;
	}

	public void setBasicContactDetail(BasicContactDetail basicContactDetail) {
		this.basicContactDetail = basicContactDetail;
	}

	public KYC getKyc() {
		return kyc;
	}

	public void setKyc(KYC kyc) {
		this.kyc = kyc;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public java.util.Date getDateofbirth() {
		return dateOfBirth;
	}

	public void setDateofbirth(java.util.Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getExcelskills() {
		return excelSkills;
	}

	public void setExcelskills(String excelSkills) {
		this.excelSkills = excelSkills;
	}

	public String getStockauditexp() {
		return stockAuditExp;
	}

	public void setStockauditexp(String stockAuditExp) {
		this.stockAuditExp = stockAuditExp;
	}

	public String getResourcetype() {
		return resourceType;
	}

	public void setResourcetype(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public int getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(int paymentAmount) {
		this.paymentAmount = paymentAmount;
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