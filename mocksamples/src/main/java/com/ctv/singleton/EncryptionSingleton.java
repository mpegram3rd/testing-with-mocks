package com.ctv.singleton;

import java.util.Stack;

/**
 * Really (not) superstrong encryption tool.
 *  
 * @author mpegram
 */
public class EncryptionSingleton {

	// Come on.. you know you've seen that code before.
	private static final EncryptionSingleton singleton = new EncryptionSingleton();

	private EncryptionSingleton() {};
	
	public static EncryptionSingleton getInstance() {
		return singleton;
	}
	
	public String flipThatString(String valueIn) {
		Stack<Character> charStack = new Stack<Character>();
		StringBuffer result = new StringBuffer();
		
		if (valueIn != null) {
			char [] characters = new char [valueIn.length()];
			valueIn.getChars(0,  valueIn.length(), characters, 0);
			
			// push on then pop off to reverse
			for (char character : characters) {
				charStack.push(character);
			}
			
			while (!charStack.isEmpty()) {
				result.append(charStack.pop());
			}
		}
		return result.toString();
	}
}
