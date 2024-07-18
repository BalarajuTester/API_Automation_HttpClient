package com.api.run;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

import com.api.reporting.Report;
import com.api.utils.Entity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RestAPI {

	static JsonObject jsonObject;
	
	static JsonNode response = null;
	
		
	//specify the json file path
	static String jsonfilePath = "C:\\Users\\BalarajuGone\\eclipse-workspace\\API_Framework\\src\\test\\resources\\API Resources\\";
	
	
	public static JsonNode executeAPI(Entity entity) throws ClientProtocolException, IOException {

		if (!(entity.getHttpCode() == null) && entity.getHttpCode().equalsIgnoreCase("get")) {
			// Create an HTTP client
			HttpClient httpClient = HttpClients.createDefault();

			// Create an HTTP GET request
			HttpGet httpGet = new HttpGet(entity.getEndPoint());

			// Set the headers to indicate JSON data
			httpGet.setHeader("Accept", "application/json");

			// Execute the HTTP GET request

			HttpResponse response = httpClient.execute(httpGet);

			// Assert the status code
			Assert.assertEquals(response.getStatusLine().getStatusCode(), entity.getHttpStatus()," StatusCode is not correct ");

			// get the response in the form of jsonNode
			JsonNode ResponseBody = getAPIResponseAsjsonNode(response);
			
			Report.printGetRequestLogInReport(httpGet);
			Report.printGetResponseLogInReport(response, ResponseBody.toString());
			return ResponseBody;

		} else if (!(entity.getHttpCode() == null) && entity.getHttpCode().equalsIgnoreCase("post")) {
			
			// Create the jsonPayload
			String jsonPayload = JsonFileReader.requestBuilder(jsonfilePath +entity.getTestResource()+"\\"+ entity.getTestRequest());

			// Create an HTTP client
			HttpClient httpClient = HttpClients.createDefault();

			// Create an HTTP POST request
			HttpPost httpPost = new HttpPost(entity.getEndPoint());

			// Set the JSON payload for the POST request
			httpPost.setEntity(new StringEntity(jsonPayload));

			// Set the headers to indicate JSON data
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-Type", "application/json");

			// Execute the HTTP POST request
			HttpResponse response = httpClient.execute(httpPost);

			// Assert the status code
			Assert.assertEquals(response.getStatusLine().getStatusCode(), entity.getHttpStatus());

			// JsonObject ResponseBody = getAPIResponseAsjsonObject(response);
			JsonNode ResponseBody = getAPIResponseAsjsonNode(response);

			Report.printPostRequestLogInReport(httpPost, jsonPayload);
			Report.printPostResponseLogInReport(response, ResponseBody.toString());
			return ResponseBody;
			
		}else if(entity.getHttpCode() == null){
			
			throw new IllegalArgumentException("HTTP code is Null");
			
		}else {
			
			throw new IllegalArgumentException("Invalid HTTP code - " + entity.getHttpCode());
		}

	}
	
	public static JsonNode getAPIResponseAsjsonNode(HttpResponse response) throws ParseException, IOException {

		// Get the response entity
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			// Convert the entity content to a string
			String responseBody = EntityUtils.toString(entity);

			if (responseBody.startsWith("[")) {
				// Parse the JSON response as an array
				JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
				for (JsonElement jsonElement : jsonArray) {
					jsonObject = jsonElement.getAsJsonObject();
				}
			} else {
				// Parse the JSON response
				jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
			}
		}

		ObjectMapper objectMapper = new ObjectMapper();
		// convert the jsonObject into jsonNode by using objectMapper
		JsonNode jsonNode = objectMapper.readTree(jsonObject.toString());
		return jsonNode;
	}
}
