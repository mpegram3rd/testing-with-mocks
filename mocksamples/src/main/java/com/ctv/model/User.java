package com.ctv.model;

import com.ctv.singleton.EncryptionSingleton;

// Very contrived case to demo internal Singelton creation and interception
// via powermock.
public class User {

	private String name;
	private String password;
	private boolean storePasswordEncrypted;
	
	public User(boolean storePasswordEncrypted) {
		this.storePasswordEncrypted = storePasswordEncrypted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public boolean isPasswordEncrypted () {
		return storePasswordEncrypted;
	}
	
	public void setPassword(String password) {
		if (isPasswordEncrypted())
			this.password = EncryptionSingleton.getInstance().flipThatString(password);
		else
			this.password = password;
	}
	
	public boolean validatePassword(String passwordIn) {
		
		if (isPasswordEncrypted()) 
			passwordIn = EncryptionSingleton.getInstance().flipThatString(passwordIn);
		
		return getPassword().equals(passwordIn);
	}
	
}
