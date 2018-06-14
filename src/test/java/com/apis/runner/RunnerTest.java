package com.apis.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features", glue = { "com.apis.stepdefinitions" }, tags = {
		"@Auth" }, plugin = { "pretty", "html:target" })
public class RunnerTest {

}
