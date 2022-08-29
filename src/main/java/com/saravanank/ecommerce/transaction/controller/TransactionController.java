package com.saravanank.ecommerce.transaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saravanank.ecommerce.transaction.model.Transactions;
import com.saravanank.ecommerce.transaction.service.TransactionService;

@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {
	@Autowired
	private TransactionService transactionService;

	@GetMapping
	public ResponseEntity<List<Transactions>> getAllTransactions() {
		return new ResponseEntity<List<Transactions>>(transactionService.getAllTransactions(), HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Transactions>> getUserTransactions(@PathVariable("userId") long userId) {
		return new ResponseEntity<List<Transactions>>(transactionService.getUserTransactions(userId), HttpStatus.OK);
	}

	@GetMapping("/order/{orderId}")
	public ResponseEntity<List<Transactions>> getOrderTransactions(@PathVariable("orderId") long orderId) {
		return new ResponseEntity<List<Transactions>>(transactionService.getOrderTransactions(orderId), HttpStatus.OK);
	}

}
