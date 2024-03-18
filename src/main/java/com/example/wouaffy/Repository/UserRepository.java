package com.example.wouaffy.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.wouaffy.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
	Optional<User> findByEmail(String email);
}
