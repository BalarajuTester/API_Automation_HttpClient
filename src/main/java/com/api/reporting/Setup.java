package com.api.reporting;

import java.util.Arrays;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.api.utils.Entity;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class Setup implements ITestListener {

	private static ExtentReports extinstance;
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	static ExtentTest test;

	public void onStart(ITestContext context) {
		String reportPath = "API Report/extent-report.html";

		extinstance = ExtentReportManager.createInstance(reportPath);
	}
	
	public void onFinish(ITestContext context) {
		extinstance.flush();
	}

	public void onTestStart(ITestResult result) {
		Entity entity = (Entity) result.getParameters()[0];
		// Get the test class name
		String className = result.getTestClass().getName();
		test = extinstance.createTest(className +"<br> TestCaseName: "+ entity.getTestCaseName());
		extentTest.set(test);
	}
	
	public void onTestSkipped(ITestResult result) {
		ExtentReportManager.logWarning(result.getThrowable().getMessage());
		String stackTrac = Arrays.toString(result.getThrowable().getStackTrace());
		stackTrac = stackTrac.replaceAll(",", "<br>");
		String formatedTrac = "<details>\r\n" + "  <summary>Click Here To See Logs</summary>\r\n" + "  "
				+ stackTrac + "\n" + "</details>";

		ExtentReportManager.logWarning(formatedTrac);
	}

	public void onTestFailure(ITestResult result) {
		String fail = result.getThrowable().getMessage();
		if (!(fail.contains("Cannot"))) {
			ExtentReportManager.logFailure(fail.replaceAll(":", ":<br>").replaceAll(",", "<br>"));

			String stackTrac = Arrays.toString(result.getThrowable().getStackTrace());
			stackTrac = stackTrac.replaceAll(",", "<br>");
			String formatedTrac = "<details>\r\n" + "  <summary>Click Here To See Exception Logs</summary>\r\n" + "  "
					+ stackTrac + "\n" + "</details>";

			ExtentReportManager.logException(formatedTrac);

		}
	}
	
}
