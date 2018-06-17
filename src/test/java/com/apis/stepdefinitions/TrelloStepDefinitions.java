package com.apis.stepdefinitions;

import static io.restassured.RestAssured.given;

import com.amazon.cucumber.TestContext;
import com.api.managers.FileReaderManager;
import com.api.steps.UserSteps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TrelloStepDefinitions {

	TestContext testContext;
	UserSteps userSteps;
	public static String petId;
	private String trelloKey;
	private String trellToken;
	private RequestSpecification request;
	private Response responseBody;
	private Object boardId;
	private Object listId;
	private Object cardId;

	public TrelloStepDefinitions(TestContext context) {
		testContext = context;
		userSteps = testContext.getUserSteps();
		RestAssured.baseURI = FileReaderManager.getInstance().getServiceFileReader()
				.getServiceEndPoint("TRELLO_BASEURI");
		RestAssured.basePath = FileReaderManager.getInstance().getServiceFileReader()
				.getServiceEndPoint("TRELLO_BASEPATH");

	}

	@Given("^a user exists with Trello key and Trello token with below details$")
	public void a_user_exists_with_Trello_key_and_Trello_token_with_below_details(DataTable accessFields) {
		this.trelloKey = accessFields.raw().get(0).get(1);
		this.trellToken = accessFields.raw().get(1).get(1);
	}

	@When("^user create a new \"([^\"]*)\" board in Trello$")
	public void user_create_a_new_board_in_Trello(String boardName) {
		// https://api.trello.com/1/boards?name={{boardName}}&key={{trelloKey}}&token={{trelloToken}}&defaultLists=false
		RequestSpecification request = userSteps.constructRequestWithPath(given(), "/boards/");
		request.contentType(ContentType.JSON);
		request = userSteps.constructRequestWithQueryParam(request, "name", boardName);
		request = userSteps.constructRequestWithQueryParam(request, "key", this.trelloKey);
		request = userSteps.constructRequestWithQueryParam(request, "token", this.trellToken);
		request = userSteps.constructRequestWithQueryParam(request, "defaultLists", "false");
		responseBody = userSteps.postRequest(request);
		responseBody.then().assertThat().statusCode(200).log().all();
		this.boardId = responseBody.jsonPath().get("id");

	}

	@When("^user create a new \"([^\"]*)\" list in Trello$")
	public void user_create_a_new_list_in_Trello(String listName) {
		// https://api.trello.com/1/lists?name=TODO&idBoard={{boardId}}&key={{trelloKey}}&token={{trelloToken}}
		RequestSpecification request = userSteps.constructRequestWithPath(given(), "/lists");
		request.contentType(ContentType.JSON);
		request = userSteps.constructRequestWithQueryParam(request, "name", listName);
		request = userSteps.constructRequestWithQueryParam(request, "idBoard", this.boardId.toString());
		request = userSteps.constructRequestWithQueryParam(request, "key", this.trelloKey);
		request = userSteps.constructRequestWithQueryParam(request, "token", this.trellToken);

		responseBody = userSteps.postRequest(request);
		responseBody.then().assertThat().statusCode(200).log().all();
		this.listId = responseBody.jsonPath().get("id");

	}

	@When("^user create a new \"([^\"]*)\" card in Trello$")
	public void user_create_a_new_card_in_Trello(String cardName) {
		// https://api.trello.com/1/cards?idList={{todoId}}&name=Learn
		// Postman&key={{trelloKey}}&token={{trelloToken}}
		RequestSpecification request = userSteps.constructRequestWithPath(given(), "/cards");
		request.contentType(ContentType.JSON);
		request = userSteps.constructRequestWithQueryParam(request, "idList", listId.toString());
		request = userSteps.constructRequestWithQueryParam(request, "name", this.boardId.toString());
		request = userSteps.constructRequestWithQueryParam(request, "key", this.trelloKey);
		request = userSteps.constructRequestWithQueryParam(request, "token", this.trellToken);

		responseBody = userSteps.postRequest(request);
		responseBody.then().assertThat().statusCode(200).log().all();
		this.cardId = responseBody.jsonPath().get("id");

	}

	@When("^user move the card from \"([^\"]*)\" list to \"([^\"]*)\" list$")
	public void user_move_the_card_from_list_to_list(String arg1, String arg2) {
		// https://api.trello.com/1/cards/{cardId}?key={{trelloKey}}&token={{trelloToken}}&idList={{doneId}}
		RequestSpecification request = userSteps.constructRequestWithPath(given(), "/cards/{cardId}");
		request = userSteps.constructRequestWithParam(request, "cardId", this.cardId.toString());
		request = userSteps.constructRequestWithQueryParam(request, "key", this.trelloKey);
		request = userSteps.constructRequestWithQueryParam(request, "token", this.trellToken);
		request = userSteps.constructRequestWithQueryParam(request, "idList", listId.toString());

		responseBody = userSteps.putRequest(request);
		responseBody.then().assertThat().statusCode(200).log().all();
		this.cardId = responseBody.jsonPath().get("id");

	}

	@When("^user delete the board$")
	public void user_delete_the_board() {

	}

}