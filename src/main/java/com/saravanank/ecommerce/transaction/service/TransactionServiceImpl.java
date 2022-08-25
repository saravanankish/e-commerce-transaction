package com.saravanank.ecommerce.transaction.service;

import java.util.Date;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saravanank.ecommerce.transaction.exceptions.BadRequestException;
import com.saravanank.ecommerce.transaction.model.Json;
import com.saravanank.ecommerce.transaction.model.Transactions;
import com.saravanank.ecommerce.transaction.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepo;

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public Transactions makeTransaction(Transactions transaction) {
		if (transaction.getAmount() <= 0)
			throw new BadRequestException("Amount should be greater than 0");
		transaction.setTransactionDate(new Date());
		transactionRepo.saveAndFlush(transaction);
		if (transaction.isSuccess()) {
			if (transaction.isReceived()) {
				rabbitTemplate.convertAndSend(Json.toJson(transaction).toString());
			}
		} else {
			throw new BadRequestException("Transaction failed");
		}
		return transaction;
	}

	@Override
	public List<Transactions> getUserTransactions(long userId) {
		return transactionRepo.findByUserId(userId);
	}

	@Override
	public List<Transactions> getOrderTransactions(long orderId) {
		return transactionRepo.findByOrderId(orderId);
	}

	@Override
	public List<Transactions> getAllTransactions() {
		return transactionRepo.findAll();
	}
}
