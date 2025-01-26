package com.droute.driverservice.dto;

public class RegisterUserRequestDto {
	
	private String fullName;
	private String email;
	private String password;
	private String contactNo;
	private String role;
	
	public RegisterUserRequestDto() {
		super();
	}
	public RegisterUserRequestDto(String fullName, String email, String password, String contactNo, String role) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.contactNo = contactNo;
		this.role = role;
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
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "RegisterUserRequestDto [fullName=" + fullName + ", email=" + email + ", password=" + password
				+ ", contactNo=" + contactNo + ", role=" + role + "]";
	}
	
	
	

}
