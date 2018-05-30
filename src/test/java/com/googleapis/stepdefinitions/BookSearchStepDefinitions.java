package com.googleapis.stepdefinitions;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.googleapis.steps.UserSearchSteps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class BookSearchStepDefinitions {

	private String ISBN_REQ_URL = "https://www.googleapis.com/books/v1/volumes/";

	@Steps
	UserSearchSteps userSearchSteps;

	@Given("a book exists with an isbn of (.*)")
	public void a_book_exists_with_isbn(String isbn) {
		userSearchSteps.constructRequestQueryParam("isbn", isbn);
	}

	@When("a user retrieves the book by isbn")
	public void a_user_retrieves_the_book_by_isbn() {
		userSearchSteps.searchByCode(ISBN_REQ_URL);
	}

	@And("response includes the following$")
	public void response_equals(Map<String, String> responseFields) {
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			if (StringUtils.isNumeric(field.getValue())) {
				userSearchSteps.json.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
			} else {
				userSearchSteps.json.body(field.getKey(), equalTo(field.getValue()));
			}
		}
	}

	@And("response includes the following in any order")
	public void response_contains_in_any_order(Map<String, String> responseFields) {
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			if (StringUtils.isNumeric(field.getValue())) {
				userSearchSteps.json.body(field.getKey(), containsInAnyOrder(Integer.parseInt(field.getValue())));
			} else {
				userSearchSteps.json.body(field.getKey(), containsInAnyOrder(field.getValue()));
			}
		}
	}
}
