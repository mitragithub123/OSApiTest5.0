package testcases.milestones_tasks;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import endpoints.MilestoneTasksEndPoints;
import endpoints.LoginEndPoints;
import payload.Login;

public class GetAllMilestoneTaskTestCases {

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

	// Test case: Positive scenario for getting all milestone tasks
	@Test(priority = 1)
	public void testGetAllMilestoneTasksPositiveCase() {
		Response response = MilestoneTasksEndPoints.getAllMilestoneTasks(accessToken, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for valid request");
	}

	// Test case: Get all milestone tasks without authorization
	@Test(priority = 2)
	public void testGetAllMilestoneTasksWithoutAuthorization() {
		Response response = MilestoneTasksEndPoints.getAllMilestoneTasks(null, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for missing authorization");
	}

	// Test case: Get all milestone tasks without tenant ID
	@Test(priority = 3)
	public void testGetAllMilestoneTasksWithoutTenantId() {
		Response response = MilestoneTasksEndPoints.getAllMilestoneTasks(accessToken, "");
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for missing tenant ID");
	}
}
