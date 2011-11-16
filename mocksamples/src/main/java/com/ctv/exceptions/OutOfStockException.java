package com.ctv.exceptions;

public class OutOfStockException extends Exception {
	public OutOfStockException (String message) { super(message); }
	public OutOfStockException (Throwable ex) { super(ex); }
	public OutOfStockException (String message, Throwable ex) { super(message, ex); }

}
