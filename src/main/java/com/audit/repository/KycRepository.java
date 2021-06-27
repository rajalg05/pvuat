package com.audit.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.audit.model.KYC;

@Repository
public interface KycRepository extends CrudRepository<KYC, String> {

    List<KYC> findAll();
 
    KYC save(KYC kyc);
    
    void delete(KYC kyc);
}
