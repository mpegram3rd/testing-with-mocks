package com.ctv.exceptions.payments;

import com.ctv.exceptions.PaymentException;

public class OverLimitException extends PaymentException {

	public OverLimitException (String message) { super(message); }
	public OverLimitException (Throwable ex) { super(ex); }
	public OverLimitException (String message, Throwable ex) { super(message, ex); }
}
