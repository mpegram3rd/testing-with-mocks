package com.ctv.orders;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ctv.model.Item;
import com.ctv.model.Order;
import com.ctv.payments.PaymentService;
import com.ctv.warehouse.WarehouseService;

public class OrderProcessingServiceImplMockito {

	// Mocked objects
	private OrderDAO<Order> orderDao;
	private PaymentService paymentService;
	private WarehouseService warehouseService;
	
	// Object under test
	private OrderProcessingServiceImpl orderProcessing;
	
	// Extra data elements for testing.
	private Order defaultOrder;
	private Item defaultItem;
	
	@Before
	public void setUp() throws Exception {
		orderDao = mock(OrderDAO.class);
		paymentService = mock(PaymentService.class);
		warehouseService = mock(WarehouseService.class);
		
		orderProcessing = new OrderProcessingServiceImpl(orderDao, warehouseService, paymentService);
		
		defaultItem = new Item("Test Item", 1.00);
		
		defaultOrder = new Order();
		defaultOrder.addItem(defaultItem);
	}

	@Test
	public void testFindOrderWithResult() {
		
		int expectedOrderNum = 1234;
		
		// Stub behavior
		when(orderDao.find(expectedOrderNum))
			.thenReturn(defaultOrder);
		
		Order result = orderProcessing.findOrder(expectedOrderNum);
		
		// Verify state
		assertThat (result, sameInstance(defaultOrder));
		
		// Verify behavior
		verify(orderDao, times(1)).find(expectedOrderNum);
		
	}

	@Test
	public void testFindOrderWithNoResult() {
		
		int expectedOrderNum = 1234;
		 
		// Stub behavior
		when(orderDao.find(expectedOrderNum))
			.thenReturn(null);
		
		
		Order result = orderProcessing.findOrder(expectedOrderNum);
		
		// Verify state
		assertThat (result, nullValue());
		
		// Verify behavior
		verify(orderDao, times(1)).find(expectedOrderNum);
		
		
	}
	
	
}
