package com.api.steps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

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

	public RequestSpecification constructRequestQueryParam(RequestSpecification request, String queryParam,
			String code) {
		return request.queryParam(queryParam, code).log().all();

	}

	public RequestSpecification constructRequestQueryParam(String queryParam, String code) {
		return given().queryParam(queryParam, code).log().all();

	}

	public RequestSpecification constructRequestParam(String pathParam, String code) {
		return given().pathParam(pathParam, code).log().all();

	}

	public Response postRequestWithPath(RequestSpecification request, String path) {
		return request.when().post(path).then().contentType(ContentType.JSON).extract().response();

	}

	public Response postRequestWithParamPath(RequestSpecification request, String param, String path) {
		return request.when().post("/{" + param + "}" + path).then().contentType(ContentType.JSON).extract().response();

	}

	public Response postJsonBodyRequest(File file) {
		return given().contentType("application/json").body(file).log().all().when().post().then()
				.contentType(ContentType.JSON).extract().response();

	}

	public Response getRequest(RequestSpecification request) {
		return request.given().when().get();

	}

	// request http://petstore.swagger.io/v2
	// path /store/inventory
	public Response getRequestWithPath(RequestSpecification request, String path) {
		return request.given().when().get(path);

	}

	// request http://petstore.swagger.io/v2
	// param {id}
	public Response getRequestWithParam(RequestSpecification request, String param) {
		return request.when().get("/{" + param + "}");

	}

	public Response getRequestWithParamPath(RequestSpecification request, String param, String path) {
		return request.when().get("/{" + param + "}" + path);

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