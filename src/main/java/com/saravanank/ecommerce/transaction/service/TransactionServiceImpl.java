package com.saravanank.ecommerce.transaction.service;

import java.util.Date;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.saravanank.ecommerce.transaction.exceptions.BadRequestException;
import com.saravanank.ecommerce.transaction.model.Json;
import com.saravanank.ecommerce.transaction.model.Transactions;
import com.saravanank.ecommerce.transaction.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepo;

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${transaction.application.backend-url}")
	private String backendUrl;

	@RabbitListener(queues = "my-transactions")
	public void makeTransaction(String data) throws JsonMappingException, JsonProcessingException, IllegalArgumentException {
		System.out.println(data);
		Transactions transaction = Json.fromJson(Json.parse(data), Transactions.class);
		if (transaction.getAmount() <= 0)
			throw new BadRequestException("Amount should be greater than 0");
		transaction.setTransactionDate(new Date());
		transaction.setSuccess(true);
		transaction.setReceived(true);
		transactionRepo.saveAndFlush(transaction);
		try {			
			ResponseEntity<String> response = restTemplate.postForEntity(backendUrl + "/order/received/payment", transaction, String.class);
			if(response.getStatusCode().value() != 202) {
				System.out.println("Failed to send transaction received status");
			}
		} catch(Exception exp) {
			System.out.println("Failed to send transaction received status");
		}
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
