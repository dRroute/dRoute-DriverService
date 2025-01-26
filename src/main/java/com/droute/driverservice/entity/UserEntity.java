package com.droute.driverservice.entity;

public class UserEntity {

	private Long userId;

	private String fullName;

	
	private String email;

	private String password;

	private String role;


	private String contactNo;

	
	private String colorHexValue;

	public UserEntity() {
	}

	public UserEntity(String fullName, String email, String password, String role, String contactNo) {
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.contactNo = contactNo;
	}

	public UserEntity(String fullName, String email, String password, String role, String contactNo,
			String colorHexValue) {
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.contactNo = contactNo;
		this.colorHexValue = colorHexValue;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getColorHexValue() {
		return colorHexValue;
	}

	public void setColorHexValue(String colorHexValue) {
		this.colorHexValue = colorHexValue;
	}

	@Override
	public String toString() {
		return "UserEntity [userId=" + userId + ", fullName=" + fullName + ", email=" + email + ", password=" + password
				+ ", role=" + role + ", contactNo=" + contactNo + ", colorHexValue=" + colorHexValue + "]";
	}

}
