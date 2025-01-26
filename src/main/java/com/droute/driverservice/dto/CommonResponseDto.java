package com.droute.driverservice.dto;

public class CommonResponseDto<T> {
	
	private String message;
	private T entity;
	public CommonResponseDto() {
		super();
		
	}
	public CommonResponseDto(String message, T entity) {
		super();
		this.message = message;
		this.entity = entity;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	@Override
	public String toString() {
		return "CommonResponseDto [message=" + message + ", entity=" + entity + "]";
	}
	
	

}
