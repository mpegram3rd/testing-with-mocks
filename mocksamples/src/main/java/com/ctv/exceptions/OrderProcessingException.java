package com.ctv.exceptions;

public class OrderProcessingException extends Exception {

	public OrderProcessingException (String message) { super(message); }
	public OrderProcessingException (Throwable ex) { super(ex); }
	public OrderProcessingException (String message, Throwable ex) { super(message, ex); }
}
