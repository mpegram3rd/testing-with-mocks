package com.ctv.mocksamples.servlet;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;

import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.ctv.mocksamples.servlet.UserPrincipalRequestWrapper;

public class UserPrincipalRequestWrapperTest extends EasyMockSupport {

	private Principal mockPrincipal;
	private HttpServletRequest mockRequest;
	
	@Before
	public void setUp() throws Exception {
		mockPrincipal = createMock(Principal.class);
		mockRequest = createMock(HttpServletRequest.class);
	}

	/**
	 * Provide valid user principal on construction
	 * Expected Outcomes:
	 * - No Exception is thrown
	 * - User Principal is set in the wrapped request.
	 * 
	 * @throws Exception
	 */
	@Test
	public void validConstructorTest() throws Exception {
		
		UserPrincipalRequestWrapper wrapped = new UserPrincipalRequestWrapper(mockPrincipal, mockRequest);

		assertThat(wrapped.getUserPrincipal(), sameInstance(mockPrincipal));
	}
	
	/**
	 * Provide a null user principal on construction
	 * Expected Outcomes:
	 * - ServletException is thrown
	 * 
	 * @throws Exception
	 */
	@Test
	public void nullPrincipalConstructorTest() throws Exception {
		
		try {
			new UserPrincipalRequestWrapper(null, mockRequest);
			fail ("Expected a ServletException but no exception thrown");
		}
		catch (ServletException ex) {} // this is the expected outcome				
	}

}
