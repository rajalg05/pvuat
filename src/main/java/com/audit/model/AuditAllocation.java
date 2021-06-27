package com.audit.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "auditAllocation")
public class AuditAllocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long auditAllocationid; 
	
	public Long getAuditAllocationid() {
		return auditAllocationid;
	}
	public void setAuditAllocationid(Long auditAllocationid) {
		this.auditAllocationid = auditAllocationid;
	}
	public AuditAllocation() {
		super();
	}

 	@OneToOne
    @JoinColumn(name = "auditDateId")
    AuditDate auditDate;

    @ManyToOne
    @JoinColumn(name = "resourceId")
    Resource resource;

    LocalDateTime allocatedAt;

    String allocatedBy;
    
 	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public LocalDateTime getAllocatedAt() {
		return allocatedAt;
	}
	public void setAllocatedAt(LocalDateTime allocatedAt) {
		this.allocatedAt = allocatedAt;
	}
	public String getAllocatedBy() {
		return allocatedBy;
	}
	public void setAllocatedBy(String allocatedBy) {
		this.allocatedBy = allocatedBy;
	}
	public AuditDate getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(AuditDate auditDate) {
		this.auditDate = auditDate;
	}
    
}
