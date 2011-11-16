package com.ctv.model;

public class OrderItem {

	private Item item;
	private int quantity;
	private boolean fufilled;
	
	public OrderItem(Item item) {
		this.item = item;
		quantity = 0;
		fufilled = false;
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public boolean isFufilled() {
		return fufilled;
	}
	public void setFufilled(boolean fufilled) {
		this.fufilled = fufilled;
	}
}
