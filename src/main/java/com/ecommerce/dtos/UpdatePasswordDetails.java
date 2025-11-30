package com.ecommerce.dtos;

public class UpdatePasswordDetails {
	
	private int userId;
	private String newPassword;
	private String oldPassword;
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public UpdatePasswordDetails(int userId, String newPassword, String oldPassword) {
		super();
		this.userId = userId;
		this.newPassword = newPassword;
		this.oldPassword = oldPassword;
	}
	public UpdatePasswordDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
