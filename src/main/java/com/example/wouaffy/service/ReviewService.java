package com.example.wouaffy.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wouaffy.Repository.ReviewRepository;
import com.example.wouaffy.entity.Review;
import com.example.wouaffy.entity.User;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ReviewService {

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private CookieService cookieService;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private UserService userService;

  public void createReview(Review review, HttpServletRequest request) {

    String token = cookieService.getCookieByName(request, "token").getValue();
    try {
      String email = jwtService.getClaimByName(token, "email");
      User user = (User) userService.loadUserByUsername(email);
      review.setUserId(user.getId());

      reviewRepository.save(review);

    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public void editReview(Review review) {
    this.reviewRepository.save(review);
  }

  public List<Review> getReviewsById(long id) {
    return this.reviewRepository.findAllByMovieId(id);
  }

}
