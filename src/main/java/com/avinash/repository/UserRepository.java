package com.avinash.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.avinash.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
