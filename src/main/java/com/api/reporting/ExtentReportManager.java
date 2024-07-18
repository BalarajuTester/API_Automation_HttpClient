package com.api.reporting;

import org.apache.http.Header;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
	
	static ExtentReports extentReports;
	
	public static ExtentReports createInstance(String filePath) {
		
		ExtentSparkReporter extentSparkReporter=new ExtentSparkReporter(filePath);
		extentSparkReporter.config().setReportName("Extent Report");
		extentSparkReporter.config().setDocumentTitle("Title");
		extentSparkReporter.config().setTheme(Theme.DARK);
		extentSparkReporter.config().setEncoding("utf-8");
		
		extentReports = new ExtentReports(); 
		
		extentReports.attachReporter(extentSparkReporter);
		
		return extentReports;
	}
	
	public static void logPassed(String log) {
		Setup.extentTest.get().pass(MarkupHelper.createLabel(log, ExtentColor.GREEN));
	}
	public static void logFailure(String log) {
		Setup.extentTest.get().fail(MarkupHelper.createLabel(log, ExtentColor.RED));
	}
	public static void logException(String log) {
		Setup.extentTest.get().fail(log);
	}
	public static void logInfo(String log) {
		Setup.extentTest.get().info(MarkupHelper.createLabel(log, ExtentColor.ORANGE));
	}
	public static void logWarning(String log) {
		Setup.extentTest.get().warning(MarkupHelper.createLabel(log, ExtentColor.YELLOW));
	}
	public static void logjson(String json) {
	
	    // Format the JSON string as a code block with JSON syntax highlighting
	    String formattedJson = MarkupHelper.createCodeBlock(json, CodeLanguage.JSON).getMarkup();

	    // Create a collapsible section with the label and formatted JSON
	    String formattedSection = "<details>\n"
	            + "  <summary>Click Here</summary>\n"
	            + "  " + formattedJson + "\n"
	            + "</details>";

	    // Add the collapsible section to your Extent Report
	    Setup.extentTest.get().info(formattedSection);

	}
	public static void logHeaders(Header[] allHeaders) {
		String[][] arr = new String[allHeaders.length][2];
		for (int i = 0; i < allHeaders.length; i++) {
		    Header header = allHeaders[i];
		    // Extract header name and value
		    String headerName = header.getName();
		    String headerValue = header.getValue();
		    
		    // Store them in the array
		    arr[i][0] = headerName;
		    arr[i][1] = headerValue;
		    
		}
		StringBuilder tableHtml = new StringBuilder();
	    tableHtml.append("<table border=\"1\">");
	    tableHtml.append("<tr><th>Header Name</th><th>Header Value</th></tr>");
	    for (String[] header : arr) {
	        tableHtml.append("<tr><td>").append(header[0]).append("</td><td>").append(header[1]).append("</td></tr>");
	    }
	    tableHtml.append("</table>");

	    // Create a formatted HTML block with a summary
	    String formattedHeaders = "<details>\n"
	            + "  <summary>Click Here</summary>\n"
	            + "  " + tableHtml.toString() + "\n"
	            + "</details>";

	    // Add the HTML block to the Extent Report
	    Setup.extentTest.get().info(formattedHeaders);
        
	}

}
