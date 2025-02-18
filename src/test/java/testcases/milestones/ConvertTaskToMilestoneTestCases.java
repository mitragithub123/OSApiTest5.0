package testcases.milestones;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import endpoints.LoginEndPoints;
import endpoints.MilestoneEndPoints;
import payload.Login;
import utilities.ConfigReader;

public class ConvertTaskToMilestoneTestCases {

	private String accessToken;
	private String xTenantId;
	private ConfigReader configReader;

	@BeforeClass
	public void setupTestData() {
		// Initialize ConfigReader
		configReader = new ConfigReader();

		// Perform login and retrieve access token
		String email = configReader.getProperty("email");
		String password = configReader.getProperty("password");
		xTenantId = configReader.getProperty("xTenantId");
		Login loginPayload = new Login(email, password);
		accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);
	}

	// Positive case
	@Test(priority = 1)
	public void testConvertTaskToMilestoneValidTaskId() {
		int taskId = 59; // Valid task ID
		Response response = MilestoneEndPoints.convertTaskToMilestone(accessToken, xTenantId, taskId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
	}

	// Without authorization
	@Test(priority = 2)
	public void verifyConvertTaskToMilestoneWithoutAuthorization() {
		int taskId = 59;
		Response response = MilestoneEndPoints.convertTaskToMilestone(null, xTenantId, taskId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Unauthorized access should fail");
	}

	// Case: Valid sub-task ID
	@Test(priority = 3)
	public void testConvertTaskToMilestoneValidSubTaskId() {
		int subTaskId = 15343; // Valid sub-task ID
		Response response = MilestoneEndPoints.convertTaskToMilestone(accessToken, xTenantId, subTaskId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422");
	}

	// Invalid tenant ID
	@Test(priority = 4)
	public void verifyConvertTaskToMilestoneWithInvalidTenantId() {
		int taskId = 59;
		Response response = MilestoneEndPoints.convertTaskToMilestone(accessToken, "invalidTenantId", taskId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Access with invalid tenant ID should be forbidden");
	}

}
