package com.audit.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.audit.model.Associate;
import com.audit.model.Audit;
import com.audit.model.AuditAllocation;
import com.audit.model.AuditDate;
import com.audit.model.Job;
import com.audit.model.Resource;
import com.audit.model.User;
import com.audit.repository.AssociateRepository;
import com.audit.repository.AuditAllocationRepository;
import com.audit.repository.AuditDateRepository;
import com.audit.repository.AuditRepository;
import com.audit.repository.JobRepository;
import com.audit.repository.ResourceRepository;
import com.audit.repository.UserRepository;
import com.audit.utils.PvUtils;
import com.google.gson.Gson;

@RestController
@RequestMapping("/audit")
@CrossOrigin(origins = { "http://localhost:4200", "http://ec2-18-224-109-184.us-east-2.compute.amazonaws.com:8080" } )
@Transactional
public class AuditController {

	private static final Gson gson = new Gson();

	@Autowired
	DataSource dataSource;

	@Autowired
	ResourceRepository resourceRepository;

	@Autowired
	AssociateRepository associateRepository;

	@Autowired
	JobRepository jobRepository;

	@Autowired
	AuditRepository auditRepository;

	@Autowired
	AuditDateRepository auditDateRepository;
	
	@Autowired
	AuditAllocationRepository auditAllocationRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PvUtils pvUtils;

	@PostMapping("/login")
	ResponseEntity<String> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
		Optional<User> o = userRepository.findByUserName(user.getUserName()); 
		if(o.isEmpty()) {
			return new ResponseEntity<String>(gson.toJson("Username not registered!!"),
					HttpStatus.NOT_ACCEPTABLE);
		}
		if (o.get() != null && pvUtils.decrypt(o.get().getPassword()).equals(pvUtils.decrypt(user.getPassword()))) {
			request.getSession(true);
			return new ResponseEntity<String>(gson.toJson("Login successful"), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(gson.toJson("Username and/or Password incorrect"),
					HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PostMapping("/saveUser")
	ResponseEntity<String> saveUser(@RequestBody User user) {
		Optional<User> dbUser = userRepository.findByUserName(user.getUserName());

		boolean userExists = dbUser.isPresent();

		if (!userExists) {
			userRepository.save(user);
			return new ResponseEntity<String>(gson.toJson("Save User Successfull!!"), HttpStatus.OK);
		} else
			return new ResponseEntity<String>(gson.toJson("user exists"), HttpStatus.OK);
	}

	@PostMapping("/deleteUser")
	ResponseEntity<String> deleteUser(@RequestBody User user) {
		userRepository.delete(user);
		return new ResponseEntity<String>(gson.toJson("Delete User Successfull!!"), HttpStatus.OK);
	}

	@GetMapping("/getUsers")
	ResponseEntity<List<User>> findAllUsers() {
		List<User> l = userRepository.findAll();
		return ResponseEntity.ok(l);
 	}

	@PostMapping("/saveResource")
	ResponseEntity<String> saveResource(@RequestBody Resource resource) {
		resourceRepository.save(resource);
		return new ResponseEntity<String>(gson.toJson("Save Resource Successfull!!"), HttpStatus.OK);
	}

	@PostMapping("/deleteResource")
	ResponseEntity<String> deleteResource(@RequestBody Resource resource) {
		resourceRepository.delete(resource);
		return new ResponseEntity<String>(gson.toJson("Delete Resource Successfull!!"), HttpStatus.OK);
	}

	@GetMapping("/getResources")
	ResponseEntity<List<Resource>> findAllResources() {
		List<Resource> l = resourceRepository.findAll();
		return ResponseEntity.ok(l);
	}

	@GetMapping("/unAllocatedResources")
	ResponseEntity<List<Resource>> unAllocatedResources() {
		List<Resource> l = resourceRepository.unAllocatedResources();
		return ResponseEntity.ok(l);
	}
	
	@PostMapping("/saveAssociate")
	ResponseEntity<String> saveAssociate(@RequestBody Associate associate) {
		associateRepository.save(associate);
		return new ResponseEntity<String>(gson.toJson("Save Associate Successfull!!"), HttpStatus.OK);
	}

	@GetMapping("/findAllAssociates")
	ResponseEntity<List<Associate>> findAllAssociates() {
		List<Associate> l = associateRepository.findAll();
		return ResponseEntity.ok(l);
	}

	@PostMapping("/deleteAssociate")
	ResponseEntity<String> deleteAssociate(@RequestBody Associate associate) {
		associateRepository.delete(associate);
		return new ResponseEntity<String>(gson.toJson("Delete Resource Successfull!!"), HttpStatus.OK);
	}

	@PostMapping("/saveJob")
	ResponseEntity<Job> saveJob(@RequestBody Job job) {
		Job j = jobRepository.getJobByJobName(job.getJobName());
		if (j == null)
			j = jobRepository.save(job);
		return new ResponseEntity<Job>(j, HttpStatus.OK);
	}

	@PostMapping("/saveAudit")
	ResponseEntity<Audit> saveAudit(@RequestBody Audit audit) {
		
		Audit auditSaved = auditRepository.save(audit);
		audit.getAuditDates().forEach(auditDate -> {
			auditDate.setAuditId(auditSaved.getId());
			auditDateRepository.save(auditDate);
		});
		return new ResponseEntity<Audit>(auditSaved, HttpStatus.OK);
	}

	@GetMapping("/findAllJobs")
	ResponseEntity<List<Job>> findAllJobs() {
		List<Job> l = jobRepository.findAll();
		for (Job j : l) {
			List<Audit> audits = auditRepository.getAuditByjobId(j.getId());
			
			audits.forEach(audit -> {
				List<AuditDate> auditDates = auditDateRepository.findByAuditId(audit.getId());	
				audit.setAuditDates(new HashSet<AuditDate>(auditDates));
			}); 
			if (null != audits && audits.size() > 0) {
				j.setAudits(audits);
			}
		}
		if (l.size() > 0) {
			return ResponseEntity.ok(l);
		} else {
			return ResponseEntity.ok(null);
		}
	}

	@PostMapping("/deleteJob")
	ResponseEntity<String> deleteJob(@RequestBody Job job) {
		jobRepository.delete(job);
		return new ResponseEntity<String>(gson.toJson("Delete Job Successfull!!"), HttpStatus.OK);
	}

	@GetMapping("/findAllAudits")
	ResponseEntity<List<Audit>> findAllAudits() {
		List<Audit> audits = auditRepository.findAll();
		
		List<AuditAllocation> allocatedAudits = auditAllocationRepository.findAll();
		allocatedAudits.forEach(allocatedAudit -> {
			audits.forEach(audit -> {
				if(audit.id.equals(allocatedAudit.getAuditDate().getAuditId()) && audit.getAllocatedResources() != null) {
					audit.getAllocatedResources().add(allocatedAudit.getResource());
				} else if(audit.id.equals(allocatedAudit.getAuditDate().getAuditId()) && audit.getAllocatedResources() == null) {
					audit.setAllocatedResources(new ArrayList<Resource>());
					audit.getAllocatedResources().add(allocatedAudit.getResource());
				}
			});
		});
		
		audits.forEach(audit -> {
			List<AuditDate> auditDates = auditDateRepository.findByAuditId(audit.getId());	
			audit.setAuditDates(new HashSet<AuditDate>(auditDates));
		}); 
		
		return ResponseEntity.ok(audits);
	} 
	
	@PostMapping("/deleteAudit")
	ResponseEntity<String> deleteAudit(@RequestBody Audit audit) {
		auditRepository.delete(audit);
		return new ResponseEntity<String>(gson.toJson("Delete Audit Successfull!!"), HttpStatus.OK);
	}

	@GetMapping("/findAllAllocatedAudits")
	ResponseEntity<List<AuditAllocation>> findAllAllocatedAudits() {
		List<AuditAllocation> l = auditAllocationRepository.findAll();
		return ResponseEntity.ok(l); 
	}

	@GetMapping("/findAllAuditDates")
	ResponseEntity<List<AuditDate>> findAllAuditDates() {
		List<AuditDate> l = auditDateRepository.findAll();
		return ResponseEntity.ok(l); 
	}
	
	@PostMapping("/allocateAudits")
	ResponseEntity<String> allocateAudits(@RequestBody List<AuditAllocation> auditAllocations) {
		List<AuditAllocation> aasSaved = auditAllocationRepository.findAll();
		// check if the existing save audit allocations has the same object sent from UI
		// is already stored
		if (aasSaved.size() > 0) {
			auditAllocations.forEach(auditAllocationSentFromUI -> {
				aasSaved.forEach(aaSaved -> {
					if (aaSaved.getAuditDate().getAuditId().equals(auditAllocationSentFromUI.getAuditDate().getAuditId())
							&& aaSaved.getResource().getId()
									.equals(auditAllocationSentFromUI.getResource().getId())) { 
						resourceRepository.save(auditAllocationSentFromUI.getResource());// update the allocate status in the resource
						auditDateRepository.save(auditAllocationSentFromUI.getAuditDate());
					} else {
						resourceRepository.save(auditAllocationSentFromUI.getResource());// update the allocate status in the resource
						auditAllocationRepository.save(auditAllocationSentFromUI);
						auditDateRepository.save(auditAllocationSentFromUI.getAuditDate());
					}
				});
			});
		} else if (aasSaved.size() == 0) {
			for (AuditAllocation auditAllocationSentFromUI : auditAllocations) {
				resourceRepository.save(auditAllocationSentFromUI.getResource());// update the allocate status in the resource
				auditDateRepository.save(auditAllocationSentFromUI.getAuditDate());
				auditAllocationRepository.save(auditAllocationSentFromUI);
				
			}
		}

		return new ResponseEntity<String>(gson.toJson("Allocated Audit Successfull!!"), HttpStatus.OK);
	}

	
	@PostMapping("/unallocateAudits")
	ResponseEntity<String> unallocateAudits(@RequestBody List<AuditAllocation> auditAllocations) {
		saveResourceAndDeleteAudit(auditAllocations);
		return new ResponseEntity<String>(gson.toJson("Unallocate Audit Successfull!!"), HttpStatus.OK);
	}
	
	
	public void saveResourceAndDeleteAudit(List<AuditAllocation> auditAllocations) {
		List<AuditAllocation> aasSaved = auditAllocationRepository.findAll();
		// check if the existing save audit allocations has the same object sent from UI
		// is already stored
		if (aasSaved.size() > 0) {
			auditAllocations.forEach(aa -> {
				aasSaved.forEach(aaSaved -> {
					if (aaSaved.getAuditDate().getAuditId().equals(aa.getAuditDate().getAuditId())
							&& aaSaved.getResource().getId()
									.equals(aa.getResource().getId())) { 
						resourceRepository.save(aa.getResource());// update the allocate status in the resource
						auditAllocationRepository.delete(aaSaved);
					} else {
						resourceRepository.save(aa.getResource());// update the allocate status in the resource
						auditAllocationRepository.delete(aaSaved);
					}
				});
			});
		}
	}
}
