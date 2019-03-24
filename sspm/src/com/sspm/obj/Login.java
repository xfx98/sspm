package com.sspm.obj;

import java.io.Serializable;

public class Login implements Serializable {
	private static final long serialVersionUID = 1L;

	private int userID;
	private String password;

	public Login(int userID, String password) {
		this.setUserID(userID);
		this.setPassword(password);
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
