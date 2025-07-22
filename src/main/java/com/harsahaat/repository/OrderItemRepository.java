package com.harsahaat.repository;

import com.harsahaat.model.Order;
import com.harsahaat.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
