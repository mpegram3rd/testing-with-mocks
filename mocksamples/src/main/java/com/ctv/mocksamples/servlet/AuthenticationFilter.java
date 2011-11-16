/**
 * 
 */
package com.ctv.mocksamples.servlet;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * Simple Servlet filter
 * @author mpegram
 */
public class AuthenticationFilter implements Filter {

	private FilterConfig filterConfig;
	private AuthenticationProvider authProvider;

	// This is being added for testing purposes... Flame War: Is that a good idea?
	protected AuthenticationFilter (AuthenticationProvider provider) {
		authProvider = provider;
	}
	
	/**
	 * Simply captures the authentication URL.  
	 */
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
		if (filterConfig.getInitParameter("authenticationURL") == null)
			throw new ServletException ("AuthenticationFilter improperly initialized.  You must provide an authenticationURL parameter");
	}
	
	public void destroy() {
		filterConfig = null;		
	}

	private String getAuthenticationURL() {
		return (filterConfig != null ? filterConfig.getInitParameter("authenticationURL") : null);
	}

	// will be used to illustrate partial mocking. 
	protected AuthenticationProvider getAuthProvider() { 
		return (authProvider == null ? new NoOpProvider() : authProvider);
	}
	
	/**
	 * First looks for a principal.  
	 * - If not found, tries to retrieve principal based on request
	 * - If no principal can be retrieved the user is redirected to the authentication URL
	 * - If principal is found, it's validity is checked.  
	 * - If principal is not valid then user is redirected to the authentication URL
	 */
	public void doFilter(ServletRequest req, ServletResponse resp,	FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpReq = (HttpServletRequest)req;
		Principal principal = httpReq.getUserPrincipal();
		
		if (principal == null) {
			principal = getAuthProvider().authenticateUser(httpReq);
			
			// Add User principal into request
			if (principal != null)
				req = new UserPrincipalRequestWrapper(principal, httpReq);
		}
		
		if (principal == null || getAuthProvider().validatePrincipal(principal))
			requireAuthentication(req, resp, chain);
		else
			chain.doFilter(req, resp);
	}
	
	
	private void requireAuthentication (ServletRequest req, ServletResponse resp,	FilterChain chain) throws IOException, ServletException {
		filterConfig.getServletContext().getRequestDispatcher(getAuthenticationURL()).forward(req, resp);
	}

}

