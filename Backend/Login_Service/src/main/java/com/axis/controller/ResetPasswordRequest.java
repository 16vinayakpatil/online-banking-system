package com.axis.controller;

public class ResetPasswordRequest {

	 private String username; // User's email
	 private String otp; // OTP sent to the user's email
	 private String newPassword; // New password for resetting the password
	public ResetPasswordRequest(String username, String otp, String newPassword) {
		super();
		this.username = username;
		this.otp = otp;
		this.newPassword = newPassword;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	 
	 
}
