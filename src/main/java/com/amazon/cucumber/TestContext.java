package com.amazon.cucumber;

import com.api.steps.UserSteps;

public class TestContext {
	private UserSteps userSteps;

	public TestContext() {
		userSteps = new UserSteps();

	}

	public UserSteps getUserSteps() {
		return userSteps;
	}

}