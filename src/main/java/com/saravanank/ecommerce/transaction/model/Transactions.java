package com.saravanank.ecommerce.transaction.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long transactionId;

	private Date transactionDate;
	private float amount;
	private boolean success = false;
	private boolean received = true;

	@Column(name = "order_id")
	private long orderId;
	
	@Column(name = "user_id")
	private long userId;

}
