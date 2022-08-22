package com.saravanank.ecommerce.transaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saravanank.ecommerce.transaction.model.Transactions;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {

	public List<Transactions> findByUserId(long userId);
	
	public List<Transactions> findByOrderId(long orderId);
	
}
