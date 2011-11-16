package com.ctv.mocksamples.servlet;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

/**
 * Does nothing useful.  
 * @author mpegram
 */
public class NoOpProvider implements AuthenticationProvider {

	/** 
	 * All principals are considered valid regardless of whether they exist.
	 */
	public boolean validatePrincipal(Principal userPrincipal) {
		return true;
	}

	/**
	 * Returns a null principal
	 */
	public Principal authenticateUser(HttpServletRequest req) {
		return null;
	}

}
