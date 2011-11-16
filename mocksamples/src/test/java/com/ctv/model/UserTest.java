package com.ctv.model;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ctv.singleton.EncryptionSingleton;

@RunWith(PowerMockRunner.class)
@PrepareForTest(EncryptionSingleton.class)
public class UserTest {

	private EncryptionSingleton instanceES;
	
	@Before
	public void setUp() throws Exception {
		mockStatic(EncryptionSingleton.class);
		
		// Now we have to create an instance to return.
		instanceES = createMock(EncryptionSingleton.class);
		
	}

	// there should be a lot more tests here but we're 
	// just trying to illustrate PowerMock's unique features.
	
	/**
	 * Test that setting unencrypted password works. 
	 */
	@Test
	public void unencryptedSetPassword() throws Exception {
		boolean encrypt = false;
		String expectedValue = "supersecret";
		
		User user = new User(encrypt);
		
		// We don't expect that the encryption method will be called
		// So we don't set any expectations
		
		// We could also shorthand this to: replayAll()
		replay(EncryptionSingleton.class);
		replay(instanceES);
		
		user.setPassword(expectedValue);
		
		assertThat(expectedValue, equalTo(user.getPassword()));
		
		// Let's confirm the behavior assumptions.   
		// This will make sure no encryption call occurred.
		verifyAll();
	}
	
	/**
	 * Test that setting a password encrypts it when
	 * encryption is turned on. 
	 */
	@Test
	public void encryptedSetPassword() throws Exception {
		boolean encrypt = true;
		String originalValue = "supersecret";
		
		// Note what we care about is that OUR function calls encryption, 
		// we are not validating that the Encryption process actually did 
		// what it's supposed to do.  
		// That's the responsibility of the EncryptionSingleton unit tests
		String expectedValue = "[[ENCRYPTED]supersecret]"; 
		
		User user = new User(encrypt);
		
		// Define expected behavior for static mock
		expect(EncryptionSingleton.getInstance())
		     .andReturn(instanceES);
		
		// Define expected behavior for the instance that was returned
		expect(instanceES.flipThatString(originalValue))
			.andReturn(expectedValue);
		
		// We could also shorthand this to: replayAll()
		replay(EncryptionSingleton.class);
		replay(instanceES);
		
		user.setPassword(originalValue);
		
		// Compare the "encrypted" password to the expected outcome.
		assertThat(expectedValue, equalTo(user.getPassword()));
		
		// Let's confirm the behavior assumptions.   
		// This will make sure no encryption call occurred.
		verifyAll();
	}

}
