package com.ctv.exceptions;

public class PaymentException extends Exception {

	public PaymentException (String message) { super(message); }
	public PaymentException (Throwable ex) { super(ex); }
	public PaymentException (String message, Throwable ex) { super(message, ex); }
}
