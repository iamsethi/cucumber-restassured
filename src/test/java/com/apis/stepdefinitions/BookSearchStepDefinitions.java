package com.apis.stepdefinitions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.Map;

import com.amazon.cucumber.TestContext;
import com.api.steps.UserSteps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class BookSearchStepDefinitions {

	TestContext testContext;
	UserSteps userSteps;

	public BookSearchStepDefinitions(TestContext context) {
		testContext = context;
		userSteps = testContext.getUserSteps();
	}

	@Given("a book exists with an isbn of (.*)")
	public void a_book_exists_with_isbn(String isbn) {
		userSteps.constructRequestQueryParam("q", "isbn" + ":" + isbn);
	}

	@When("a user retrieves the book by isbn")
	public void a_user_retrieves_the_book_by_isbn() {
		userSteps.getRequest();
		userSteps.response.then().body(matchesJsonSchemaInClasspath("schema-json/isbn.json"));
	}

	@And("response includes the following$")
	public void response_equals(Map<String, String> responseFields) {
		userSteps.iShouldFindFollowingResponse(responseFields);
	}

	@And("response includes the following in any order")
	public void response_contains_in_any_order(Map<String, String> responseFields) {
		userSteps.iShouldFindFollowingResponseInAnyOrder(responseFields);
	}
}
