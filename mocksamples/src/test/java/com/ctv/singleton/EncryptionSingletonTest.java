package com.ctv.singleton;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

public class EncryptionSingletonTest {

	private EncryptionSingleton singleton;
	
	@Before
	public void setUp() throws Exception {
		singleton = EncryptionSingleton.getInstance();
	}

	/**
	 * Provide a null value
	 * Expected outcome: empty string returned.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testNullValue() throws Exception {
		assertThat(singleton.flipThatString(null), equalTo(""));
	}
	
	/**
	 * Provide a valid string
	 * Expected outcome is the string reversed.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFlipped() throws Exception {
		String input = "abc123";
		String expected = "321cba";
		
		assertThat(singleton.flipThatString(input), equalTo(expected));
	}

}
