package com.ctv.exceptions.payments;

import com.ctv.exceptions.PaymentException;

public class InsufficientFundsException extends PaymentException {

	public InsufficientFundsException (String message) { super(message); }
	public InsufficientFundsException (Throwable ex) { super(ex); }
	public InsufficientFundsException (String message, Throwable ex) { super(message, ex); }
}
