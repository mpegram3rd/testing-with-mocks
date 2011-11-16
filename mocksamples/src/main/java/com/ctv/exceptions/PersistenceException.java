package com.ctv.exceptions;

public class PersistenceException extends Exception {

	public PersistenceException (String message) { super(message); }
	public PersistenceException (Throwable ex) { super(ex); }
	public PersistenceException (String message, Throwable ex) { super(message, ex); }
}
