package com.ctv.mocksamples.servlet;

import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class UserPrincipalRequestWrapper extends HttpServletRequestWrapper {

	private Principal userPrincipal;
	private HttpServletRequest wrappedReq;
	
	public UserPrincipalRequestWrapper (Principal principal, HttpServletRequest req) throws ServletException {
		super(req);
		if (principal == null)
			throw new ServletException ("The UserPrincipalRequestWrapper cannot be created without a Principal");
		
		wrappedReq = req;	
		userPrincipal = principal;
	}
	
	@Override
	public Principal getUserPrincipal() {
		return userPrincipal;
	}
	
	
}
