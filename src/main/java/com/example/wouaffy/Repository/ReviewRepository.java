package com.example.wouaffy.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.wouaffy.entity.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {

  List<Review> findAllByMovieId(long id);

}
