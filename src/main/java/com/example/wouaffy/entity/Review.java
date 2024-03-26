package com.example.wouaffy.entity;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "review")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @DecimalMin(value = "0", inclusive = true, message = "Score must be between 0 & 5.")
  @DecimalMax(value = "5", inclusive = true, message = "Score must be between 0 & 5.")
  @NonNull
  @Column(name = "review_score")
  private float score;

  @Size(min = 5, max = 300)
  @Column(name = "review_comment")
  private String comment;

  @NonNull
  @Column(name = "review_movie_id")
  private long movieId;

  @NonNull
  @Column(name = "review_user_id")
  private long userId;

  public long getId() {
    return this.id;
  }

  public float getScore() {
    return score;
  }

  public void setScore(float score) {
    this.score = score;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public long getMovieId() {
    return movieId;
  }

  public void setMovieId(long movieId) {
    this.movieId = movieId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

}
