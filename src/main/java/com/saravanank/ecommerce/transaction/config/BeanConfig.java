package com.saravanank.ecommerce.transaction.config;

import javax.servlet.Filter;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.saravanank.ecommerce.transaction.filter.AuthFilter;

@Configuration
public class BeanConfig {

	@Bean
	AmqpTemplate template(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		return rabbitTemplate;
	}
	
	@Bean
	FilterRegistrationBean<Filter> registerAuthFilter() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		registration.setFilter(getFilter());
		registration.addUrlPatterns("*");
		registration.setName("auth-filter");
		registration.setOrder(1);
		return registration;
	}
	
	private Filter getFilter() {
		return new AuthFilter();
	}

}
