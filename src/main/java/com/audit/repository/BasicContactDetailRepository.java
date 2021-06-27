package com.audit.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.audit.model.BasicContactDetail;

@Repository
public interface BasicContactDetailRepository extends CrudRepository<BasicContactDetail, String> {

    List<BasicContactDetail> findAll();
 
    BasicContactDetail save(BasicContactDetail basicContactDetail);
    
    void delete(BasicContactDetail basicContactDetail);
}
