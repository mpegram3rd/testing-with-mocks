package com.ctv.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ctv.exceptions.OrderModificationException;

public class Order {

	private Map<Item, OrderItem> items;
	private OrderStatusEnum status;
	
	public Order() { items = new HashMap<Item, OrderItem>(); status = OrderStatusEnum.NEW; }

	public Order addItem(Item item) throws OrderModificationException {
		return addItem(item, 1);
	}
	
	// Returns an order to allow for simple chaining
	public Order addItem(Item item, int quantity) throws OrderModificationException {
		if (getStatus() == OrderStatusEnum.NEW) {
			OrderItem orderItem = items.get(item);
			
			// Update quantity if already present.
			if (orderItem != null) {
				orderItem.setQuantity(orderItem.getQuantity() + quantity);
			}
			else {
				orderItem = new OrderItem(item);
				orderItem.setQuantity(quantity);
			}
	
			items.put(item, orderItem);
		}
		else
			throw new OrderModificationException("You may only add items to new orders");
		
		return this;
	}
	
	public Order removeItem(Item item) {
		
		items.remove(item);
		return this;
	}
	
	public int getItemCount() {
		int count = 0;
		for (OrderItem orderItem : items.values()) {
			count += orderItem.getQuantity();
		}
		
		return count;		
	}
	
	public double getTotalCost() {
		double totalCost = 0;
		for (OrderItem orderItem : items.values()) {
			int quantity = orderItem.getQuantity();
			totalCost += (quantity * orderItem.getItem().getPrice());
		}
		
		return totalCost;
	}
	public OrderStatusEnum getStatus() {
		return status;
	}

	public Collection<OrderItem> getOrderItems() {
		return Collections.unmodifiableCollection(items.values());
	}
	
	private boolean checkValidStatusStateChange(OrderStatusEnum status)  {

		boolean valid = false;
		
		if (status != null) {
			Set<OrderStatusEnum> validStatuses = new HashSet<OrderStatusEnum>();
			
			switch (this.status) {
				case NEW     		: validStatuses.add(OrderStatusEnum.CANCELLED); 
									  validStatuses.add(OrderStatusEnum.COMPLETED);
			       			  	  	  validStatuses.add(OrderStatusEnum.IN_PROCESS);
	   	  		  			  	  	  break;
				case IN_PROCESS		: validStatuses.add(OrderStatusEnum.CANCELLED);
									  validStatuses.add(OrderStatusEnum.COMPLETED);
			          	  		  	  break;
			}
			valid = validStatuses.contains(status);
		}
		return valid;
	}
	
	// Enforces only valid status state changes.
	public void setStatus(OrderStatusEnum status) throws OrderModificationException {
		if (checkValidStatusStateChange(status)) {
			this.status = status;
		}
		else
			throw new OrderModificationException("You cannot change order status from " + this.status + " to " + status);
	}

}
