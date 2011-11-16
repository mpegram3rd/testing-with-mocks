package com.ctv.exceptions;

public class OrderModificationException extends Exception {

	public OrderModificationException (String message) { super(message); }
	public OrderModificationException (Throwable ex) { super(ex); }
	public OrderModificationException (String message, Throwable ex) { super(message, ex); }

}
