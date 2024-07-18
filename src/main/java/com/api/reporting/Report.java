package com.api.reporting;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

public class Report {
	
	public static void printPostRequestLogInReport(HttpPost httpPost, String jsonPayload)
			throws ParseException, IOException {
		ExtentReportManager.logInfo("Endpoint : " + httpPost.getURI());
		ExtentReportManager.logInfo("Method : " + httpPost.getMethod());
		ExtentReportManager.logInfo("Request Headers : ");
		ExtentReportManager.logHeaders(httpPost.getAllHeaders());
		ExtentReportManager.logInfo("Request body : ");
		ExtentReportManager.logjson(jsonPayload.toString());
	}

	public static void printPostResponseLogInReport(HttpResponse response, String responseBody)
			throws ParseException, IOException {

		ExtentReportManager.logInfo("Response Status : " + (response.getStatusLine().getStatusCode()));
		ExtentReportManager.logInfo("Response Headers : ");
		ExtentReportManager.logHeaders(response.getAllHeaders());
		ExtentReportManager.logInfo("Response body : ");
		ExtentReportManager.logjson(responseBody);
	}

	public static void printGetRequestLogInReport(HttpGet httpGet) {
		ExtentReportManager.logInfo("Endpoint : " + httpGet.getURI());
		ExtentReportManager.logInfo("Method : " + httpGet.getMethod());
		ExtentReportManager.logInfo("Request Headers : ");
		ExtentReportManager.logHeaders(httpGet.getAllHeaders());
	}

	public static void printGetResponseLogInReport(HttpResponse response, String responseBody)
			throws ParseException, IOException {

		ExtentReportManager.logInfo("Response Status : " + response.getStatusLine().getStatusCode());
		ExtentReportManager.logInfo("Response Headers : ");
		ExtentReportManager.logHeaders(response.getAllHeaders());
		ExtentReportManager.logInfo("Response body : ");
		ExtentReportManager.logjson(responseBody);
	}

}
