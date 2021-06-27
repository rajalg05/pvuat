package com.audit.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.audit.model.AuditDate;

@Repository
public interface AuditDateRepository extends CrudRepository<AuditDate, String> {

    AuditDate save(AuditDate auditDate);
    
    List<AuditDate> findByAuditId(Long auditId);
    
    List<AuditDate> findAll();
    
    void delete(AuditDate auditDate);
    
}
