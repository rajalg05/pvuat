package com.audit.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.audit.PvApplication;
import com.audit.exception.BadRequestException;
import com.audit.exception.ResourceNotFoundException;
import com.audit.exception.UnAutherizedException;
import com.audit.model.Associate;
import com.audit.model.Audit;
import com.audit.model.AuditAllocation;
import com.audit.model.AuditDate;
import com.audit.model.Job;
import com.audit.model.Resource;
import com.audit.model.ResponseMessage;
import com.audit.model.User;
import com.audit.repository.AssociateRepository;
import com.audit.repository.AuditAllocationRepository;
import com.audit.repository.AuditDateRepository;
import com.audit.repository.AuditRepository;
import com.audit.repository.JobRepository;
import com.audit.repository.ResourceRepository;
import com.audit.repository.UserRepository;
import com.audit.service.FilesStorageService;
import com.audit.utils.PvUtils;
import com.google.gson.Gson;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/audit")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8085","http://150.242.14.192:8085/","http://www.stockauditnow.com"})
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

	@Autowired
	FilesStorageService storageService;
	
	private static final Logger logger = LoggerFactory.getLogger(PvApplication.class);
	 
	@PostMapping("/login")

	ResponseEntity<User> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Inside login Started....{}",user.getUserName());
		Optional<User> o = userRepository.findByUserName(user.getUserName()); 
		if(o.isEmpty()) {
			user.setComments("Username not registered!!");
			return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
		}
		if (o.get() != null && pvUtils.decrypt(o.get().getPassword()).equals(pvUtils.decrypt(user.getPassword()))) {
			request.getSession(true);
			String token = getJWTToken(user.getUserName());
			user.setToken(token);
			user.setComments("Login successful");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			throw new UnAutherizedException("Username and/or Password incorrect");
		}
	}

	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

	@PostMapping("/saveUser")
	ResponseEntity<String> saveUser(@RequestBody User user) {
		logger.debug("Inside SaveUser Started....{}",user.getUserName());
		Optional<User> dbUser = userRepository.findByUserName(user.getUserName());

		boolean userExists = dbUser.isPresent();

		if (!userExists) {
			userRepository.save(user);
			return new ResponseEntity<String>(gson.toJson("Save User Successfull!!"), HttpStatus.OK);
		} else {
			throw new BadRequestException("Username "+user.getUserName()+ " exists");
			//return new ResponseEntity<String>(gson.toJson("user exists"), HttpStatus.OK);
		}
			
	}

	@PostMapping("/deleteUser")
	ResponseEntity<String> deleteUser(@RequestBody User user) {
		logger.debug("Inside deleteUser Started....{}",user.getUserName());
		userRepository.delete(user);
		return new ResponseEntity<String>(gson.toJson("Delete User Successfull!!"), HttpStatus.OK);
	}

	@GetMapping("/getUsers")
	ResponseEntity<List<User>> findAllUsers() {
		logger.debug("Inside findAllUsers Started....");
		List<User> l = userRepository.findAll();
		return ResponseEntity.ok(l);
 	}

	@PostMapping("/saveResource")
	ResponseEntity<String> saveResource(@RequestBody Resource resource) {
		logger.debug("Inside saveResource Started....{}",resource.id);
		resourceRepository.save(resource);
		return new ResponseEntity<String>(gson.toJson("Save Resource Successfull!!"), HttpStatus.OK);
	}

	@PostMapping("/deleteResource")
	ResponseEntity<String> deleteResource(@RequestBody Resource resource) {
		logger.debug("Inside deleteResource Started....{}",resource.id);
		resourceRepository.delete(resource);
		return new ResponseEntity<String>(gson.toJson("Delete Resource Successfull!!"), HttpStatus.OK);
	}

	@GetMapping("/getResources")
	ResponseEntity<List<Resource>> findAllResources() {
		logger.debug("Inside findAllResources Started....");
		List<Resource> l = resourceRepository.findAll();
		if(l.size() == 0) {
			throw new ResourceNotFoundException("No resource found");
		}
		return ResponseEntity.ok(l);
	}

	@GetMapping("/unAllocatedResources")
	ResponseEntity<List<Resource>> unAllocatedResources() {
		logger.debug("Inside unAllocatedResources Started....");
		List<Resource> l = resourceRepository.unAllocatedResources();
		return ResponseEntity.ok(l);
	}
	
	@PostMapping("/saveAssociate")
	ResponseEntity<String> saveAssociate(@RequestBody Associate associate) {
		logger.debug("Inside saveAssociate Started....{}",associate.getId());
		associateRepository.save(associate);
		return new ResponseEntity<String>(gson.toJson("Save Associate Successfull!!"), HttpStatus.OK);
	}

	@GetMapping("/findAllAssociates")
	ResponseEntity<List<Associate>> findAllAssociates() {
		logger.debug("Inside findAllAssociates Started....");
		List<Associate> l = associateRepository.findAll();
		return ResponseEntity.ok(l);
	}

	@PostMapping("/deleteAssociate")
	ResponseEntity<String> deleteAssociate(@RequestBody Associate associate) {
		logger.debug("Inside deleteAssociate Started....{}",associate.getId());
		associateRepository.delete(associate);
		return new ResponseEntity<String>(gson.toJson("Delete Resource Successfull!!"), HttpStatus.OK);
	}

	@PostMapping("/saveJob")
	ResponseEntity<Job> saveJob(@RequestBody Job job) {
		logger.debug("Inside saveJob Started....{}",job.getId());
		Job j = jobRepository.getJobByJobName(job.getJobName());
		if (j == null)
			j = jobRepository.save(job);
		return new ResponseEntity<Job>(j, HttpStatus.OK);
	}

	@PostMapping("/saveAudit")
	ResponseEntity<Audit> saveAudit(@RequestBody Audit audit) {
		logger.debug("Inside saveAudit Started....{}",audit.getId());
		Audit auditSaved = auditRepository.save(audit);
		audit.getAuditDates().forEach(auditDate -> {
			auditDate.setAuditId(auditSaved.getId());
			auditDateRepository.save(auditDate);
		});
		return new ResponseEntity<Audit>(auditSaved, HttpStatus.OK);
	}

	@GetMapping("/findAllJobs")
	ResponseEntity<List<Job>> findAllJobs() {
		logger.debug("Inside findAllJobs Started....");
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
		logger.debug("Inside deleteJob Started....{}",job.getId());
		jobRepository.delete(job);
		return new ResponseEntity<String>(gson.toJson("Delete Job Successfull!!"), HttpStatus.OK);
	}

	@GetMapping("/findAllAudits")
	ResponseEntity<List<Audit>> findAllAudits() {
		logger.debug("Inside findAllAudits Started....");
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
		logger.debug("Inside deleteAudit Started....{}",audit.getId());
		auditRepository.delete(audit);
		return new ResponseEntity<String>(gson.toJson("Delete Audit Successfull!!"), HttpStatus.OK);
	}

	@GetMapping("/findAllAllocatedAudits")
	ResponseEntity<List<AuditAllocation>> findAllAllocatedAudits() {
		logger.debug("Inside findAllAllocatedAudits Started....");
		List<AuditAllocation> l = auditAllocationRepository.findAll();
		return ResponseEntity.ok(l); 
	}

	@GetMapping("/findAllAuditDates")
	ResponseEntity<List<AuditDate>> findAllAuditDates() {
		logger.debug("Inside findAllAuditDates Started....");
		List<AuditDate> l = auditDateRepository.findAll();
		return ResponseEntity.ok(l); 
	}
	
	@PostMapping("/allocateAudits")
	ResponseEntity<String> allocateAudits(@RequestBody List<AuditAllocation> auditAllocations) {
		logger.debug("Inside allocateAudits Started....{}",auditAllocations.size());
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
		logger.debug("Inside unallocateAudits Started....{}",auditAllocations.size());
		saveResourceAndDeleteAudit(auditAllocations);
		return new ResponseEntity<String>(gson.toJson("Unallocate Audit Successfull!!"), HttpStatus.OK);
	}
	
	
	public void saveResourceAndDeleteAudit(List<AuditAllocation> auditAllocations) {
		logger.debug("Inside saveResourceAndDeleteAudit Started....{}",auditAllocations.size());
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
	
	 @PostMapping("/upload")
	  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		 logger.debug("Inside uploadFile Started....{}",file.getOriginalFilename());
	    String message = "";
	    try {
	      storageService.save(file);

	      message = "Uploaded the file successfully: " + file.getOriginalFilename();
	      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	    } catch (Exception e) {
	    	logger.error("Error while uploaing file {}",file.getOriginalFilename());
	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	    }
	  }

//	  @GetMapping("/files")
//	  public ResponseEntity<List<FileInfo>> getListFiles() {
//	    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
//	      String filename = path.getFileName().toString();
//	      String url = MvcUriComponentsBuilder
//	          .fromMethodName(AuditController.class, "getFile", path.getFileName().toString()).build().toString();
//
//	      return new FileInfo(filename, url);
//	    }).collect(Collectors.toList());
//
//	    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
//	  }
//
//	  @GetMapping("/files/{filename:.+}")
//	  @ResponseBody
//	  public ResponseEntity<org.springframework.core.io.Resource> getFile(@PathVariable String filename) {
//		  org.springframework.core.io.Resource  file = storageService.load(filename);
//	    return ResponseEntity.ok()
//	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//	  }
	}
