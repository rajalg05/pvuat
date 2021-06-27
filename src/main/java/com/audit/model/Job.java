package com.audit.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "job")
public class Job implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Job(Associate associate, String jobName, String clientName, String frequencyOfAudit, String paymentType,
			double totalPayment, int resourcesNeeded, Date createdTs, Date updatedTs) {
		super();
		this.associate = associate;
		this.jobName = jobName;
		this.clientName = clientName;
		this.frequencyOfAudit = frequencyOfAudit;
		this.paymentType = paymentType;
		this.totalPayment = totalPayment;
		this.resourcesNeeded = resourcesNeeded;
		this.createdTs = createdTs;
		this.updatedTs = updatedTs;
	}

	public Job() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	public Associate associate;

	@OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
	public List<Audit> audits;

	public String jobName;

	public String clientName;

	public String frequencyOfAudit;

	public String paymentType;

	public double totalPayment;

	public int resourcesNeeded;

	public java.util.Date createdTs;

	public java.util.Date updatedTs;
 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Audit> getAudits() {
		return audits;
	}

	public void setAudits(List<Audit> audits) {
		this.audits = audits;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getFrequencyofaudit() {
		return frequencyOfAudit;
	}

	public void setFrequencyofaudit(String frequencyOfAudit) {
		this.frequencyOfAudit = frequencyOfAudit;
	}

	public String getPaymenttype() {
		return paymentType;
	}

	public void setPaymenttype(String paymentType) {
		this.paymentType = paymentType;
	}

	public double getTotalpayment() {
		return totalPayment;
	}

	public void setTotalpayment(double totalPayment) {
		this.totalPayment = totalPayment;
	}

	public int getResourcesneeded() {
		return resourcesNeeded;
	}

	public void setResourcesneeded(int resourcesNeeded) {
		this.resourcesNeeded = resourcesNeeded;
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