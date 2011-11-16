package com.ctv.orders;

import com.ctv.exceptions.OrderModificationException;
import com.ctv.exceptions.OrderProcessingException;
import com.ctv.exceptions.PaymentException;
import com.ctv.exceptions.PersistenceException;
import com.ctv.model.Order;

public interface OrderProcessingService {

	public int placeOrder(String creditCard, Order order) throws OrderProcessingException, PaymentException;
	
	public Order findOrder(int orderNumber);
	
	public boolean cancelOrder(int orderNumber) throws OrderProcessingException;
	
}
