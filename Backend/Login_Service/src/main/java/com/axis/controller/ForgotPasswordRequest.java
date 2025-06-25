package com.axis.controller;

public class ForgotPasswordRequest {

	private String username;

	  // Default constructor (required for deserialization)
    public ForgotPasswordRequest() {
    }
	public ForgotPasswordRequest(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
