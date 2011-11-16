package com.ctv.warehouse;

import com.ctv.exceptions.OutOfStockException;
import com.ctv.model.Item;

public interface WarehouseService {

	public void addStock(Item item, int quantity);
	public void removeStock(Item item, int quantity) throws OutOfStockException;
}
