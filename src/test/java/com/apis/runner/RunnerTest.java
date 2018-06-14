package com.apis.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features", glue = { "com.apis.stepdefinitions" }, tags = {
		"@BasicAuth,@OAuth1,@OAuth2,@ISBN,@ISO,@PostTweet,@Upload" }, plugin = { "pretty", "html:target" })
public class RunnerTest {

}
