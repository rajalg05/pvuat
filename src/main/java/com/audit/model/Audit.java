package com.audit.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "audit")
public class Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id; 
	
	public Long jobId; 
	
	public String jobName; 
	
	@OneToOne( cascade = CascadeType.ALL) 
	public Address address;
	
	public String auditStatus;
	
	public String auditName;

	@OneToMany(mappedBy = "auditDate")
	Set<AuditDate> auditDates;
    
	@JsonIgnore
	@ManyToOne( cascade = CascadeType.ALL) 
	@JoinColumn(name = "jobId",referencedColumnName = "id",insertable = false, updatable = false)
	public Job job;
	 
	@Transient
	List<Resource> allocatedResources; 
	
	public Set<AuditDate> getAuditDates() {
		return auditDates;
	}

	public void setAuditDates(Set<AuditDate> auditDates) {
		this.auditDates = auditDates;
	}
 
	public Double paymentReceived;
	
	public String statusUpdatedBy;
    
	public LocalDateTime createdTs;
    
	public LocalDateTime updatedTs;
	
	public Audit() {
		super();
	}
 

	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	} 

	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}  

	public String getStatusUpdatedBy() {
		return statusUpdatedBy;
	}
	public void setStatusUpdatedBy(String statusUpdatedBy) {
		this.statusUpdatedBy = statusUpdatedBy;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
 
	public Double getPaymentReceived() {
		return paymentReceived;
	}
	public void setPaymentReceived(Double paymentReceived) {
		this.paymentReceived = paymentReceived;
	}
	public LocalDateTime getCreatedTs() {
		return createdTs;
	}
	public void setCreatedTs(LocalDateTime createdTs) {
		this.createdTs = createdTs;
	}
	public LocalDateTime getUpdatedTs() {
		return updatedTs;
	}
	public void setUpdatedTs(LocalDateTime updatedTs) {
		this.updatedTs = updatedTs;
	}

	public List<Resource> getAllocatedResources() {
		return allocatedResources;
	}

	public void setAllocatedResources(List<Resource> allocatedResources) {
		this.allocatedResources = allocatedResources;
	}

}
