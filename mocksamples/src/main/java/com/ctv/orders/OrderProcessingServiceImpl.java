package com.ctv.orders;

import javax.annotation.Resource;

import com.ctv.exceptions.OrderModificationException;
import com.ctv.exceptions.OrderProcessingException;
import com.ctv.exceptions.OutOfStockException;
import com.ctv.exceptions.PaymentException;
import com.ctv.exceptions.PersistenceException;
import com.ctv.model.Item;
import com.ctv.model.Order;
import com.ctv.model.OrderItem;
import com.ctv.model.OrderStatusEnum;
import com.ctv.payments.PaymentService;
import com.ctv.warehouse.WarehouseService;

public class OrderProcessingServiceImpl implements OrderProcessingService {

	@Resource
	private OrderDAO<Order> orderDao;
	
	@Resource
	private WarehouseService warehouseService;
	
	@Resource
	private PaymentService paymentService;
	
	protected OrderProcessingServiceImpl (OrderDAO<Order> orderDao, WarehouseService warehouseService, PaymentService paymentService) {
		this.orderDao = orderDao;
		this.warehouseService = warehouseService;
		this.paymentService = paymentService;
	}
	
	public boolean cancelOrder(int orderNumber) throws OrderProcessingException {
		
		boolean cancelled = false;
		Order order = findOrder(orderNumber);
		if (order != null) {
			try {
				order.setStatus(OrderStatusEnum.CANCELLED);
				orderDao.save(order);
				cancelled = true;
			}
			catch (OrderModificationException ex) {
				// Should log
				// indicates order is in a state that cannot be modified
			}
			catch (PersistenceException ex) {
				throw new OrderProcessingException ("Could not complete cancellation due to system error", ex);
			}
		}
		
		return cancelled;
		
	}

	public Order findOrder(int orderNumber) {
		return orderDao.find(orderNumber);
		
	}

	private void restock (Order order) {
		// Need to place fufilled items back in the warehouse 
		for (OrderItem orderItem : order.getOrderItems()) {
			if (orderItem.isFufilled()) {
				warehouseService.addStock(orderItem.getItem(), orderItem.getQuantity());
				orderItem.setFufilled(false);
			}
		}

	}
	public int placeOrder(String creditCard, Order order) 	throws OrderProcessingException, PaymentException {
		int orderNumber = -1;
		
		if (creditCard == null) {
			throw new PaymentException("Invalid Credit Card Data");
		}
		
		if (order == null || order.getItemCount() == 0) {
			throw new OrderProcessingException ("Cannot process an empty order");
		}
		
		try {
			for (OrderItem orderItem : order.getOrderItems()) {
				warehouseService.removeStock(orderItem.getItem(), orderItem.getQuantity());
				orderItem.setFufilled(true);
				
			}
			paymentService.makePayment(creditCard, order.getTotalCost());
			orderNumber = orderDao.add(order);
		}
		catch (OutOfStockException ex) {
			restock(order);
			throw new OrderProcessingException("Some items were out of stock, your order could not be fufilled", ex);
		}
		catch (PaymentException ex) {
			restock(order);
			throw ex;
		}
		catch (PersistenceException ex) {
			restock(order);
			throw new OrderProcessingException("A system error prevented us from fufilling your order", ex);
		}
				
		
		return orderNumber;
	}

}
