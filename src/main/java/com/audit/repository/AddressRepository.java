package com.audit.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.audit.model.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, String> {

    List<Address> findAll();
 
    Address save(Address address);
   
    void delete(Address address);
}
