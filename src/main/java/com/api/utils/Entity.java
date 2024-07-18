package com.api.utils;

public class Entity {

	private String TestCaseName;
	private String executable;
	private String endPoint;
	private String TestRequest;
	private String TestResource;
	private String httpCode;
	private int httpStatus;
	private String bookName;
	private String bookAuthor;
	private double bookPrice;
	private int id;
	private String errorMessage;

	
	public String getTestCaseName() {
		return TestCaseName;
	}
	
	public String getExecutable() {
		return executable;
	}
	
	public String getEndPoint() {
		return endPoint;
	}

	public String getTestRequest() {
		return TestRequest;
	}
	
	public String getTestResource() {
		return TestResource;
	}

	public String getHttpCode() {
		return httpCode;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public String getbookName() {
		return bookName;
	}

	public String getbookAuthor() {
		return bookAuthor;
	}

	public double getbookPrice() {
		return bookPrice;
	}

	public int getid() {
		return id;
	}

	public String geterrorMessage() {
		return errorMessage;
	}

}
