package com.harsahaat.repository;

import com.harsahaat.model.Cart;
import com.harsahaat.model.CartItem;
import com.harsahaat.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}
