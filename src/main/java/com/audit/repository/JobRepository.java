package com.audit.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.audit.model.Job;

@Repository
public interface JobRepository extends CrudRepository<Job, String> {

    List<Job> findAll();
 
    //List<Job> findAllJobsForAssociate(Long associateId);
    
    Job save(Job job);
    
    void delete(Job job);
    
	Job getJobByJobName(String jobName);
}
