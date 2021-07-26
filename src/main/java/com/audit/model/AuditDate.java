package com.audit.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "auditDate")
public class AuditDate {
	@ManyToOne
    @JoinColumn(name = "auditId", insertable = false, updatable = false)
    Audit audit;

	@OneToMany(mappedBy = "auditAllocationid")
	Set<AuditAllocation> auditAllocations;
		
	public Long auditId; 
	
	@JsonFormat(timezone = "IST")
    Date auditDate;

    Integer auditDay;
 
    String name;
    
    String code;
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id; 
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public AuditDate() {
		super();
	}
    public Long getAuditId() {
		return auditId;
	}
	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	} 
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public Integer getAuditDay() {
		return auditDay;
	}
	public void setAuditDay(Integer auditDay) {
		this.auditDay = auditDay;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
