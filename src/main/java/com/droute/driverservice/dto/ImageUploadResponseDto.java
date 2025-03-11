package com.droute.driverservice.dto;

public class ImageUploadResponseDto {

	private int status;
	private String message;
	private String url;
	
	public ImageUploadResponseDto() {
		super();
	}
	public ImageUploadResponseDto(int status, String message, String url) {
		super();
		this.status = status;
		this.message = message;
		this.url = url;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "ImageUploadResponseDto [status=" + status + ", message=" + message + ", url=" + url + "]";
	}
	
	

}
