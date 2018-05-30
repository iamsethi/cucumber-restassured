package com.googleapis.steps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class UserSearchSteps {

	private RequestSpecification request;
	private Response response;
	public ValidatableResponse json;

	@Step
	public void constructRequestQueryParam(String queryParam, String code) {
		request = given().param("q", "" + queryParam + ":" + code);

	}

	@Step
	public void constructRequestPathParam(String pathParam, String code) {
		request = given().pathParam("pathParam", code);

	}

	@Step
	public void searchByCode(String CODE_SEARCH) {
		response = request.when().get(CODE_SEARCH);
		// System.out.println("response: " + response.prettyPrint());
		// response = SerenityRest.when().get(ISO_CODE_SEARCH + code);

	}

	@Step
	public void searchIsExecutedSuccesfully(int statusCode) {
		json = response.then().statusCode(statusCode);
	}

	@Step
	public void iShouldFindResponse(String country) {
		response.then().body("RestResponse.result.name", is(country));
	}
}