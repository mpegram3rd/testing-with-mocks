package com.ctv.orders;

import com.ctv.dao.GenericDao;
import com.ctv.model.Order;

public interface OrderDAO<T extends Order> extends GenericDao<T> {

	public Order find(int orderNumber);
}
