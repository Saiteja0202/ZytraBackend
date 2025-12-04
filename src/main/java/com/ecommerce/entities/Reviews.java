package com.ecommerce.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "reviews")
public class Reviews {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reviewId;

	@NotNull(message = "Product Id is required")
	private int productId;

	@NotNull(message = "User Id is required")
	private int userId;

	@NotNull(message = "Rating is required")
	private int rating;

	@Lob
	@NotBlank(message = "Comment is required")
	private String comment;

	private LocalDate createdAt;

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public Reviews(int reviewId, @NotNull(message = "Product Id is required") int productId,
			@NotNull(message = "User Id is required") int userId, @NotNull(message = "Rating is required") int rating,
			@NotBlank(message = "Comment is required") String comment, LocalDate createdAt) {
		super();
		this.reviewId = reviewId;
		this.productId = productId;
		this.userId = userId;
		this.rating = rating;
		this.comment = comment;
		this.createdAt = createdAt;
	}

	public Reviews() {
		super();

	}

}
