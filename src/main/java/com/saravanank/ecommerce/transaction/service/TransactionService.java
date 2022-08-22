package com.saravanank.ecommerce.transaction.service;

import java.util.List;

import com.saravanank.ecommerce.transaction.model.Transactions;

public interface TransactionService {

	public Transactions makeTransaction(Transactions transaction);

	public List<Transactions> getUserTransactions(long userId);

	public List<Transactions> getOrderTransactions(long orderId);
	
	public List<Transactions> getAllTransactions();

}
