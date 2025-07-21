package com.harsahaat.service.impl;


import com.harsahaat.model.Cart;
import com.harsahaat.model.CartItem;
import com.harsahaat.model.Product;
import com.harsahaat.model.User;
import com.harsahaat.repository.CartItemRepository;
import com.harsahaat.repository.CartRepository;
import com.harsahaat.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;



    @Override
    public CartItem addCartItem(User user, Product product, String size, int quanity) {

        Cart cart = findUserCart(user);
        CartItem isPresent = cartItemRepository.findByCartAndProductAndSize(cart,product,size);

        if(isPresent==null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quanity);
            cartItem.setUserId(user.getId());
            cartItem.setSize(size);

            int totalPrice = quanity* product.getSellingPrice();
            cartItem.setSellingPrice(totalPrice);
            cartItem.setMrpPrice(quanity*product.getMrpPrice());

            cart.getCartItems().add(cartItem);
            cartItem.setCart(cart);

            return cartItemRepository.save(cartItem);
        }
        return isPresent;
    }

    @Override
    public Cart findUserCart(User user) {
        Cart cart = cartRepository.findByUserId(user.getId());


        // Crucial change: If no cart exists for the user, create a new one
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartItems(new HashSet<>()); // Initialize an empty set
            // Initialize other fields if necessary, like total prices to 0
            cart.setTotalMrpPrice(0);
            cart.setTotalSellingPrice(0);
            cart.setTotalItem(0);
            cart.setDiscount(0);
            //cartRepository.save(cart); // Persist the new cart
        }
        int totalPrice=0;
        int totalDiscountedPrice=0;
        int totalItem =0;

        for(CartItem cartItem: cart.getCartItems()){
            totalPrice +=cartItem.getMrpPrice();
            totalDiscountedPrice +=cartItem.getSellingPrice();
            totalItem +=cartItem.getQuantity();
        }

        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalSellingPrice(totalDiscountedPrice);
        cart.setDiscount(calculateDiscountPercentage(totalPrice,totalDiscountedPrice));
        cart.setTotalItem(totalItem);

       // cartRepository.save(cart);


        return cart;
    }
    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if(mrpPrice<=0){

            //return 0;
            throw new IllegalArgumentException("Actual price must be greater than 0");
        }
        double discount = mrpPrice- sellingPrice;
        double discountPercentage = (discount/mrpPrice)*100;

        return (int)discountPercentage;
    }

}
