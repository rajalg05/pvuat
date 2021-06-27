//package com.audit.controller;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import com.audit.model.Associate;
//import com.audit.model.Audit;
//import com.audit.model.AuditAllocation;
//import com.audit.model.Job;
//import com.audit.model.Resource;
//import com.audit.model.User;
//import com.audit.repository.AssociateRepository;
//import com.audit.repository.AuditAllocationRepository;
//import com.audit.repository.AuditRepository;
//import com.audit.repository.JobRepository;
//import com.audit.repository.ResourceRepository;
//import com.audit.repository.UserRepository;
//
//@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
//public class AuditControllerTest {
//    @InjectMocks
//    AuditController auditController;
//     
//    @Mock
//    ResourceRepository resourceRepository;
//    
//    @Mock
//    UserRepository userRepository;
//    
//    @Mock
//    JobRepository jobRepository;
//    
//    @Mock
//    AuditRepository auditRepository;
//    
//    @Mock
//    AssociateRepository associateRepository;
//    
//    @Mock
//    AuditAllocationRepository auditAllocationRepository;
//     
//    @Test
//    public void testSaveResource() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        when(resourceRepository.save(any(Resource.class))).thenReturn(any(Resource.class));
//         
//        Resource resource = new Resource(null, null, null, null, null, null, null, null, null, null, null, null);
//        ResponseEntity<String> responseEntity = auditController.saveResource(resource);
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getBody().equals("Save Resource Successfull!!"));
//    }
//    
//    @Test
//    public void testDeleteResource() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//                  
//        Resource resource = new Resource(null, null, null, null, null, null, null, null, null, null, null, null);
//        ResponseEntity<String> responseEntity = auditController.deleteResource(resource);
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getBody().equals("Delete Resource Successfull!!"));
//    }
//    
//    @Test
//    public void testFindAllResourcesForNoResource() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        List<Resource> resources = new ArrayList<>();
//        when(resourceRepository.findAll()).thenReturn(resources);
//
//        ResponseEntity<List<Resource>> responseEntity = auditController.findAllResources();
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
//    }
//    
//    @Test
//    public void testFindAllResourcesForFewResource() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        List<Resource> resources = new ArrayList<>();
//        resources.add(new Resource(null, null, null, null, null, null, null, null, null, null, null, null));
//        when(resourceRepository.findAll()).thenReturn(resources);
//
//        ResponseEntity<List<Resource>> responseEntity = auditController.findAllResources();
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//    }
//    
//    @Test
//    public void testSaveAssociate() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        when(associateRepository.save(any(Associate.class))).thenReturn(any(Associate.class));
//         
//        Associate Associate = new Associate(null, null, null, null, null);
//        ResponseEntity<String> responseEntity = auditController.saveAssociate(Associate);
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getBody().equals("Save Associate Successfull!!"));
//    }
//    
//    @Test
//    public void testDeleteAssociate() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//                  
//        Associate Associate = new Associate(null, null, null, null, null);
//        ResponseEntity<String> responseEntity = auditController.deleteAssociate(Associate);
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getBody().equals("Delete Associate Successfull!!"));
//    }
//    
//    @Test
//    public void testFindAllAssociatesForNoAssociate() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        List<Associate> associates = new ArrayList<>();
//        when(associateRepository.findAll()).thenReturn(associates);
//
//        ResponseEntity<List<Associate>> responseEntity = auditController.findAllAssociates();
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
//    }
//    
//    @Test
//    public void testFindAllAssociatesForFewAssociate() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        List<Associate> associates = new ArrayList<>();
//        associates.add(new Associate(null, null, null, null, null));
//        when(associateRepository.findAll()).thenReturn(associates);
//
//        ResponseEntity<List<Associate>> responseEntity = auditController.findAllAssociates();
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//    }
//    @Test
//    public void testSaveUser() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        when(userRepository.save(any(User.class))).thenReturn(any(User.class));
//       // when(userRepository.findByUserName(any(String.class)))
//        	//.thenReturn(null);
//
//        User User = new User();
//        ResponseEntity<String> responseEntity = auditController.saveUser(User);
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getBody().equals("Save User Successfull!!"));
//    }
//    
//    @Test
//    public void testDeleteUser() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//                  
//        User User = new User();
//        ResponseEntity<String> responseEntity = auditController.deleteUser(User);
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getBody().equals("Delete User Successfull!!"));
//    }
//    
//    @Test
//    public void testFindAllUsersForNoUser() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        List<User> Users = new ArrayList<>();
//        when(userRepository.findAll()).thenReturn(Users);
//
//        ResponseEntity<List<User>> responseEntity = auditController.findAllUsers();
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
//    }
//    
//    @Test
//    public void testFindAllUsersForFewUser() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        List<User> Users = new ArrayList<>();
//        Users.add(new User());
//        when(userRepository.findAll()).thenReturn(Users);
//
//        ResponseEntity<List<User>> responseEntity = auditController.findAllUsers();
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//    }
//    
//    @Test
//    public void testSaveJob() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        when(jobRepository.save(any(Job.class))).thenReturn(any(Job.class));
//         
//        Job Job = new Job();
//        ResponseEntity<String> responseEntity = auditController.saveJob(Job);
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getBody().equals("Save Job Successfull!!"));
//    }
//    
//    @Test
//    public void testDeleteJob() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//                  
//        Job Job = new Job();
//        ResponseEntity<String> responseEntity = auditController.deleteJob(Job);
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getBody().equals("Delete Job Successfull!!"));
//    }
//    
//    @Test
//    public void testFindAllJobsForNoJob() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        List<Job> jobs = new ArrayList<>();
//        when(jobRepository.findAll()).thenReturn(jobs);
//
//        ResponseEntity<List<Job>> responseEntity = auditController.findAllJobs();
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//    }
//    
//    @Test
//    public void testFindAllJobsForFewJob() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        List<Job> jobs = new ArrayList<>();
//        Job j = new Job();
//        j.setId(Long.valueOf("1"));
//        j.setAudits(new ArrayList<Audit>());
//        jobs.add(j);
//        when(jobRepository.findAll()).thenReturn(jobs);
//        when(auditRepository.getAuditByjobId(any(Long.class))).thenReturn(new ArrayList<Audit>());
//
//        ResponseEntity<List<Job>> responseEntity = auditController.findAllJobs();
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//    }
//    
//    @Test
//    public void testSaveAudit() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        when(auditRepository.save(any(Audit.class))).thenReturn(any(Audit.class));
//         
//        Audit Audit = new Audit(null, null, null, null, null, null, null, null, null, null, null);
//        ResponseEntity<String> responseEntity = auditController.saveAudit(Audit);
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getBody().equals("Save Audit Successfull!!"));
//    }
//    
//    @Test
//    public void testDeleteAudit() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//                  
//        Audit audit = new Audit(null, null, null, null, null, null, null, null, null, null, null);
//        ResponseEntity<String> responseEntity = auditController.deleteAudit(audit);
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getBody().equals("Delete Audit Successfull!!"));
//    }
//    
//    @Test
//    public void testFindAllAuditsForNoAudit() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        List<Audit> audits = new ArrayList<>();
//        when(auditRepository.findAll()).thenReturn(audits);
//
//        ResponseEntity<List<Audit>> responseEntity = auditController.findAllAudits();
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
//    }
//    
//    @Test
//    public void testFindAllAuditsForFewAudit() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        List<Audit> Audits = new ArrayList<>();
//        Audits.add(new Audit(null, null, null, null, null, null, null, null, null, null, null));
//        when(auditRepository.findAll()).thenReturn(Audits);
//
//        ResponseEntity<List<Audit>> responseEntity = auditController.findAllAudits();
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//    }
//    
//    @Test
//    public void testAuditAllocations() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//         
//        //when(auditAllocationRepository.save(any(AuditAllocation.class))).thenReturn(any(AuditAllocation.class));
//         
//        List<AuditAllocation> auditAllocations = new ArrayList<>();
//        ResponseEntity<String> responseEntity = auditController.allocateAudits(auditAllocations);
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getBody().equals("Allocated Audit Successfull!!"));
//    }
//    
//    @Test
//    public void testDeleteAuditAllocations() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//                  
//        List<AuditAllocation> auditAllocations = new ArrayList<>();
//        ResponseEntity<String> responseEntity = auditController.unallocateAudits(auditAllocations);
//         
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getBody().equals("Unallocated Audit Successfull!!"));
//    }
//    
//}