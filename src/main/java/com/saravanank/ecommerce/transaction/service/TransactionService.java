package com.saravanank.ecommerce.transaction.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.saravanank.ecommerce.transaction.model.Transactions;

public interface TransactionService {

	public void makeTransaction(String data) throws JsonMappingException, JsonProcessingException, IllegalArgumentException;

	public List<Transactions> getUserTransactions(long userId);

	public List<Transactions> getOrderTransactions(long orderId);

	public List<Transactions> getAllTransactions();

}
