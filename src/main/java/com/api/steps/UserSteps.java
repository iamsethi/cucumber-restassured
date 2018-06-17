package com.api.steps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class UserSteps {

	public ValidatableResponse json;

	public RequestSpecification constructOAuth1Request(String consumerKey, String consumerSecret, String accessToken,
			String tokenSecret) {
		return given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).log().all();

	}

	public RequestSpecification constructOAuth2Request(String token) {
		return given().auth().oauth2(token).log().all();

	}

	public RequestSpecification constructBasicRequest(String username, String password) {
		return given().auth().preemptive().basic(username, password).log().all();

	}

	public RequestSpecification constructRequestAddAuthHeader(String token) {
		return given().header("Authorization", "Bearer " + token);
	}

	public RequestSpecification constructMultiPartFile(RequestSpecification request, File file) {
		return request.given().header("Content-Type", "multipart/form-data").and().multiPart(file);

	}

	public RequestSpecification constructRequestWithPath(RequestSpecification request, String path) {
		return request.given().basePath(RestAssured.basePath.concat(path)).log().all();

	}

	public RequestSpecification constructRequestWithQueryParam(RequestSpecification request, String queryParam,
			String code) {
		return request.queryParam(queryParam, code).log().all();

	}

	public RequestSpecification constructRequestWithParam(RequestSpecification request, String pathParam, String code) {
		return request.pathParam(pathParam, code).log().all();

	}

	public Response getRequest(RequestSpecification request) {
		return request.given().when().get();

	}

	public Response putRequest(RequestSpecification request) {
		return request.when().put().then().log().all().contentType(ContentType.JSON).extract().response();

	}

	public Response postRequest(RequestSpecification request) {
		return request.when().post().then().log().all().contentType(ContentType.JSON).extract().response();

	}

	public Response postWithJsonFile(RequestSpecification request, File file) {
		return request.contentType("application/json").body(file).log().all().when().post().then()
				.contentType(ContentType.JSON).extract().response();

	}

	public Response deleteRequest(RequestSpecification request) {
		return request.when().delete().then().contentType(ContentType.JSON).extract().response();

	}

	public ValidatableResponse searchIsExecutedSuccesfully(Response response, int statusCode) {
		return response.then().statusCode(statusCode);
	}

	public void iShouldFindResponse(Response response, String expected) {
		response.then().body("RestResponse.result.name", is(expected));
	}

	public void iShouldFindFollowingResponse(Response response, Map<String, String> responseFields) {
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			if (StringUtils.isNumeric(field.getValue())) {
				response.then().body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
			} else {
				response.then().body(field.getKey(), equalTo(field.getValue()));
			}
		}

	}

	public void iShouldFindFollowingResponseInAnyOrder(Response response, Map<String, String> responseFields) {
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			if (StringUtils.isNumeric(field.getValue())) {
				response.then().body(field.getKey(), containsInAnyOrder(Integer.parseInt(field.getValue())));
			} else {
				response.then().body(field.getKey(), containsInAnyOrder(field.getValue()));
			}
		}
	}

}