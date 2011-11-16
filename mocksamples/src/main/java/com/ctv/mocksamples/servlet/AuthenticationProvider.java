package com.ctv.mocksamples.servlet;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;

public interface AuthenticationProvider {
	
	public boolean validatePrincipal(Principal userPrincipal);
	
	public Principal authenticateUser(HttpServletRequest req);
}
