package com.example.wouaffy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.wouaffy.Data;
import com.example.wouaffy.ResponseData;
import com.example.wouaffy.entity.Review;
import com.example.wouaffy.service.ResponseService;
import com.example.wouaffy.service.ReviewService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ReviewController {

  @Autowired
  private ReviewService reviewService;

  @Autowired
  private ResponseService responseService;

  Logger log = LoggerFactory.getLogger(ReviewController.class);

  @GetMapping(path = "/reviews/{movie_id}")
  public ResponseEntity<ResponseData> getReviews(@PathVariable("movie_id") long id) {

    List<Review> reviews = this.reviewService.getReviewsById(id);

    Data<Review> data = new Data<Review>(reviews);

    return responseService.createResponse(200, "Reviews", data);

  }

  @PostMapping(path = "/add/review")
  public ResponseEntity<ResponseData> addReview(@RequestBody Review review, HttpServletRequest request) {
    this.reviewService.createReview(review, request);
    return responseService.createResponse(200, "Review added successfully.");
  }

}
