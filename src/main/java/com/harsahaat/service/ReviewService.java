package com.harsahaat.service;

import com.harsahaat.model.Product;
import com.harsahaat.model.Review;
import com.harsahaat.model.User;
import com.harsahaat.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {

    Review createReview(CreateReviewRequest req,
                        User user, Product product);
    List<Review> getReviewByProductId(Long productId);
    Review updateReview(Long reviewId, String reviewText, double
                        rating, Long userId) throws Exception;

    void deleteReview(Long reviewId, Long userId) throws Exception;
    Review getReviewById(Long reviewId) throws Exception;

}
