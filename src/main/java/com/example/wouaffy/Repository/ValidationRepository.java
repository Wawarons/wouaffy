package com.example.wouaffy.Repository;

import org.springframework.data.repository.CrudRepository;

import com.example.wouaffy.entity.Validation;
import java.util.Optional;

public interface ValidationRepository extends CrudRepository<Validation, Long> {

  Optional<Validation> findByUserId(long id);

  Optional<Validation> findByCode(String code);

}
