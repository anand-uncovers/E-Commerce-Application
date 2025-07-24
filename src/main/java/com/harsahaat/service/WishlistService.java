package com.harsahaat.service;

import com.harsahaat.model.Product;
import com.harsahaat.model.User;
import com.harsahaat.model.Wishlist;

public interface WishlistService {

    Wishlist createWishlist(User user);
    Wishlist getWishlistByUserId(User user);
    Wishlist addProductToWishlist(User user
    , Product product);


}
