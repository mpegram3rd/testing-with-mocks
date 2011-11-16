package com.ctv.orders;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.ctv.model.Item;
import com.ctv.model.Order;
import com.ctv.payments.PaymentService;
import com.ctv.warehouse.WarehouseService;

public class OrderProcessingServiceImplEasyMockTest extends EasyMockSupport {

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
		orderDao = createMock(OrderDAO.class);
		paymentService = createMock(PaymentService.class);
		warehouseService = createMock(WarehouseService.class);
		
		orderProcessing = new OrderProcessingServiceImpl(orderDao, warehouseService, paymentService);
		
		defaultItem = new Item("Test Item", 1.00);
		
		defaultOrder = new Order();
		defaultOrder.addItem(defaultItem);
	}

	@Test
	public void testFindOrderWithResult() {
		
		int expectedOrderNum = 1234;
		
		expect(orderDao.find(eq(expectedOrderNum)))
			.andReturn(defaultOrder);
		
		replayAll();
		
		Order result = orderProcessing.findOrder(expectedOrderNum);
		
		// Verify state
		assertThat (result, sameInstance(defaultOrder));
		
		// Verify behavior
		verifyAll();
		
	}

	@Test
	public void testFindOrderWithNoResult() {
		
		int expectedOrderNum = 1234;
		expect(orderDao.find(expectedOrderNum))
			.andReturn(null);
		
		replayAll();
		
		Order result = orderProcessing.findOrder(expectedOrderNum);
		
		// Verify state
		assertThat (result, nullValue());
		
		// Verify behavior
		verifyAll();
		
	}
	
	
}
