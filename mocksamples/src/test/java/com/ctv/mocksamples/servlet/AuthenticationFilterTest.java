package com.ctv.mocksamples.servlet;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import static org.junit.Assert.fail;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.ctv.mocksamples.servlet.AuthenticationFilter;
import com.ctv.mocksamples.servlet.AuthenticationProvider;

public class AuthenticationFilterTest extends EasyMockSupport {

	private AuthenticationFilter filter;
	private AuthenticationProvider mockAuthProvider;
	private FilterConfig mockFilterConfig;
	
	@Before
	public void setUp() throws Exception {
		mockFilterConfig = createMock(FilterConfig.class);
		mockAuthProvider = createMock(AuthenticationProvider.class);
		
		filter = new AuthenticationFilter(mockAuthProvider);
	}

	/**
	 * Scenario: Provide valid configuration
	 * - No Exception is thrown.
	 * 
	 * @throws Exception
	 */
	@Test
	public void validFilterConfigTest () throws Exception {

		// Define expected behaviors
		String expectedURL = "http://www.yahoo.com";
		
		// A very strict definition.  Expects a single call to this method with the specific string "authenticationURL"
		expect(mockFilterConfig.getInitParameter(eq("authenticationURL")))
		     .andReturn(expectedURL);
		
		// Prepare mocks for validation
		replayAll();
		
		// Execute test
		filter.init(mockFilterConfig);
		
		// No Exception thrown at this point, so functional validation is good
		
		// Problem(??) we can't check whether or not the Authentication URL 
		// actually got set because the getter is private.  EasyMock doesn't 
		// support mocking private behavior. 
		
	    // Validates expected behavior of mocks occurred.  
		// If getInitParam on the mockFilter did not get called this will fail.
		verifyAll();
	}

	/**
	 * Scenario: Provide valid configuration
	 * This uses a looser definition of the mock's behavior to 
	 * illustrate how strictly mocking has it's benefits. 
	 * 
	 * - No Exception is thrown.
	 * 
	 * @throws Exception
	 */
	@Test
	public void looserFilterConfigTest () throws Exception {

		// Define expected behaviors
		String expectedURL = "http://www.yahoo.com";
		
		// A slightly looser expectation.  Is it valid for testing??? 
		// We're not enforcing WHICH init parameter is checked.
		// Perhaps an invalid test case
		expect(mockFilterConfig.getInitParameter(anyObject(String.class)))
		     .andReturn(expectedURL);
		
		// Prepare mocks for validation
		replayAll();
		
		// Execute test
		filter.init(mockFilterConfig);

		// No Exception thrown at this point, so functional validation is good
		
		// Problem(??) we can't check whether or not the Authentication URL 
		// actually got set because the getter is private.  EasyMock doesn't 
		// support mocking private behavior. 
		
	    // Validates expected behavior of mocks occurred.  
		// If getInitParam on the mockFilter did not get called this will fail.
		verifyAll();
	}
	
	/**
	 * Scenario: No value is found for the URL 
	 * Expectation
	 * - ServletException is thrown.
	 * @throws Exception
	 */
	@Test
	public void invalidFilterConfigTest() throws Exception {
		
		// A very strict definition.  Expects a single call to this method with the specific string "authenticationURL"
		expect(mockFilterConfig.getInitParameter(eq("authenticationURL")))
		     .andReturn(null); // this time return a null 
		
		// Prepare mocks for validation
		replayAll();
		
		// Execute test
		try {
			filter.init(mockFilterConfig);
			fail("Expected a ServletException since the authenticationURL parameter was null");
		}
		catch (ServletException ex) { } // This is the expected outcome 
		
	    // Validates expected behavior of mocks occurred.  
		// If getInitParam on the mockFilter did not get called this will fail.
		verifyAll();
	}
	
	/**
	 * Scenario: This will demonstrate how mocks can catch unexpected behaviors. 
	 * - In this case the test case does NOT define a behavior for the FilterConfig mock
	 * - When the class under test uses this mock, it triggers a failure.  
	 * Expected Outcome:
	 * - Failure due to unexpected access of a mock method.
	 */
	@Test
	public void unexpectedStrictMockAccess() throws Exception {
		
		// We have not defined any behaviors for any mocks.
		
		// Reset mocks for usage/validation
		replayAll();
		
		try {
			filter.init(mockFilterConfig);
			fail("Expected a ServletException since the authenticationURL parameter was null");
		}
		catch (ServletException ex) {} // This the expected outcome (or is it with a strict mock???)
		
		// Validate that the expected behavior of the mocks occurred
		// Technically we'll fail before getting here because a strict mock was accessed with a defined beahvior.
		verifyAll();
	}
	
	/**
	 * Scenario: This will demonstrate how mocks nice mocks change the testing
	 * - In this case the test case does NOT define a behavior for the FilterConfig mock
	 * - The FilterConfig mock has been defined as a "nice" mock so unexpected behavior is ok. 
	 * - When the class under test uses this mock, it triggers a failure.  
	 * Expected Outcome:
	 * - Failure due to unexpected access of a mock method.
	 */
	@Test
	public void unexpectedNiceMockAccess() throws Exception {
		
		// Redefine the mock as a nice mock.  This makes incidental access ok. 
		mockFilterConfig = createNiceMock(FilterConfig.class);
		
		// Reset mocks for usage/validation
		replayAll();
		
		try {
			filter.init(mockFilterConfig);
			fail("Expected a ServletException since the authenticationURL parameter was null");
		}
		catch (ServletException ex) {} // This the expected outcome (or is it with a strict mock???)
		
		// Validate that the expected behavior of the mocks occurred
		// A nice mock allows for unexpected access and merely returns natural default values (in this case a null).
		verifyAll();
	}
	
}
