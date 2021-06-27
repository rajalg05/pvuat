package com.audit.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.audit.model.AuditAllocation;

@Repository
public interface AuditAllocationRepository extends CrudRepository<AuditAllocation, String> {

    List<AuditAllocation> findAll();
 
    AuditAllocation save(AuditAllocation auditAllocation);

    void delete(AuditAllocation auditAllocation);
    
}
