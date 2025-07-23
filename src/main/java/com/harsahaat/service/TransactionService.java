package com.harsahaat.service;

import com.harsahaat.model.Order;
import com.harsahaat.model.Seller;
import com.harsahaat.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Order order);
    List<Transaction> getTransactionsBySellerId(Seller seller);
    List<Transaction> getAllTransactions();

}
