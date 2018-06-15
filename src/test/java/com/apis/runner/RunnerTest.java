package com.apis.runner;

import java.io.File;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.api.managers.FileReaderManager;
import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = { "com.apis.stepdefinitions" }, tags = {
		"@PostTweet,~@Swagger,@ISO,@ISBN,@BasicAuth,@OAuth1,@Auth2" }, plugin = {
				"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html" })
public class RunnerTest {

	@AfterClass
	public static void writeExtentReport() {
		Reporter.loadXMLConfig(new File(FileReaderManager.getInstance().getConfigReader().getReportConfigPath()));
	}

}
