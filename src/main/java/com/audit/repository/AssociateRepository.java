package com.audit.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.audit.model.Associate;

@Repository
public interface AssociateRepository extends CrudRepository<Associate, String> {

    List<Associate> findAll();
 
    Associate save(Associate associate);
    
    void delete(Associate associate);
}
