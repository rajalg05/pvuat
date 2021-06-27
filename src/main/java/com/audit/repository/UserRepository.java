package com.audit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.audit.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    List<User> findAll();
 
    User save(User user);
   
    void delete(User user);
     
    Optional<User> findByUserName(String userName);
}
