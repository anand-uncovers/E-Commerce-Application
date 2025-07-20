package com.harsahaat.service;

import com.harsahaat.model.Cart;
import com.harsahaat.model.CartItem;
import com.harsahaat.model.Product;
import com.harsahaat.model.User;

public interface CartService {

    public CartItem addCartItem(
            User user,
            Product product,
            String size,
            int quanity
    );
    public Cart findUserCart(User user);

}
