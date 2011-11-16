package com.ctv.model;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ctv.exceptions.OrderModificationException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Order.class)
public class OrderPowermockTest {
	private Item videoGame;
	private Item movie;
	private Item gadget;
	private Item networkCard;
	private Order order;
	
	@Before
	public void setUp() {
		videoGame = new Item("Call of Duty: Black Ops", 59.99);
		movie = new Item("Avatar Bluray", 24.99);
		gadget = new Item("Logitech wireless mouse", 45.88);
		networkCard = new Item("Linksys Wireless N", 27.42);
		
		order = new Order();
	}
	
	/**
	 * Traditional state based unit test.
	 */
	@Test
	public void testGetTotalCostTraditional() {
		try {
			order.addItem(videoGame);
			assertThat(order.getItemCount(), equalTo(1));
			assertThat(order.getTotalCost(), equalTo(videoGame.getPrice()));
		}
		catch (Exception ex) {
			fail("Unexpected Exception occurred");
		}
		
	}
	

	/**
	 * Mock based test verifying both state and behavior.
	 */
	@Test
	public void testGetTotalCostMocked () {
		double expectedPrice = 12.95;
		
		movie = createMock(Item.class);
		
		// define expectations
		movie.getPrice();
		expectLastCall()
			.andReturn(expectedPrice)
			.times(1);  
		
		// reset mocks for use in the test
		replayAll();
		
		try {
			order.addItem(movie);
			// verify state.
			assertThat(order.getItemCount(), equalTo(1));
			assertThat(order.getTotalCost(), equalTo(expectedPrice));
		}
		catch (Exception ex) {
			fail("Unexpected Exception occurred");
		}
		
		// Verify that the expected mock behavior occurred.
		verifyAll();
	}
	
	/**
	 * This is going to exercise the private method checkValidStatusStateChange with an invalid status change.
	 * This is a private method, and no mocking is used here so traditional test methodology requires us 
	 * to use the higher level public API to test it.
	 * 
	 */
	@Test
	public void testSetStatusTraditional() {
		try {
			order.setStatus(OrderStatusEnum.CANCELLED);
			order.setStatus(OrderStatusEnum.NEW);
			fail("Expected an OrderModificationException since order status is Completed");
		}
		catch(OrderModificationException ex) {
			assertThat(ex.getMessage(), containsString("cannot change order status")); // kind of a stupid validation but oh well.
		}
		
	}
	
	/**
	 * This method is to test the setStatus with an invalid status change by 
	 * mocking the private method so the test focuses only on the behavior of the method under test.
	 */
	@Test
	public void testSetStatusPartialMocked()  throws Exception {
		
		OrderStatusEnum expectedStatus = OrderStatusEnum.NEW;
		order = createPartialMock(Order.class, "checkValidStatusStateChange");
		expectPrivate(order,"checkValidStatusStateChange", expectedStatus)
				.andReturn(false)
				.times(1);

		replayAll();
		
		try {
			order.setStatus(expectedStatus);
			fail("Expected an OrderModificationException when partial mock was called");
		}
		catch(OrderModificationException ex) {
			assertThat(ex.getMessage(), containsString("cannot change order status")); // kind of a stupid validation but oh well.
		}
		
		verifyAll();
		
	}

}
