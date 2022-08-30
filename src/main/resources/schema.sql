CREATE TABLE IF NOT EXISTS `transactions` (
  `transaction_id` bigint NOT NULL AUTO_INCREMENT,
  `amount` float NOT NULL,
  `order_id` bigint DEFAULT NULL,
  `received` bit(1) NOT NULL,
  `success` bit(1) NOT NULL,
  `transaction_date` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`transaction_id`)
);