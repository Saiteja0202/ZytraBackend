package com.ecommerce.dtos;

public class LoginResponse {
	
	private int userId;
	private String userRole;
	private String token;
	private String email;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LoginResponse(int userId, String userRole, String token, String email) {
		super();
		this.userId = userId;
		this.userRole = userRole;
		this.token = token;
		this.email = email;
	}
	
	

}
