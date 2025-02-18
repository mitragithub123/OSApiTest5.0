package testcases.milestones_tasks;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import endpoints.MilestoneTasksEndPoints;
import endpoints.LoginEndPoints;
import payload.Login;

public class GetMilestoneTaskTestCases {

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

	// Test case: Get milestone task without authorization
	@Test(priority = 1)
	public void testGetMilestoneTaskWithoutAuthorization() {
		String milestoneId = "32";
		Response response = MilestoneTasksEndPoints.getMilestoneTask(null, xTenantId, milestoneId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for missing authorization");
	}

	// Test case: Get milestone task with valid milestone ID
	@Test(priority = 2)
	public void testGetMilestoneTaskPositiveCase() {
		String milestoneId = "288"; // Use a valid milestone ID
		Response response = MilestoneTasksEndPoints.getMilestoneTask(accessToken, xTenantId, milestoneId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for valid milestone ID");
	}

	// Test case: Get milestone task with an invalid milestone ID (123456)
	@Test(priority = 3)
	public void testGetMilestoneTaskInvalidMilestoneId() {
		String milestoneId = "123456";
		Response response = MilestoneTasksEndPoints.getMilestoneTask(accessToken, xTenantId, milestoneId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404 for invalid milestone ID");
	}

	// Test case: Get milestone task with a negative milestone ID
	@Test(priority = 4)
	public void testGetMilestoneTaskNegativeMilestoneId() {
		String milestoneId = "-20";
		Response response = MilestoneTasksEndPoints.getMilestoneTask(accessToken, xTenantId, milestoneId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404 for negative milestone ID");
	}

	// Test case: Get milestone task with a milestone ID in an incorrect format
	// (non-numeric)
	@Test(priority = 5)
	public void testGetMilestoneTaskInvalidMilestoneIdFormat() {
		String milestoneId = "abc";
		Response response = MilestoneTasksEndPoints.getMilestoneTask(accessToken, xTenantId, milestoneId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for invalid milestone ID format");
	}

	// Test case: Get milestone task without tenant ID
	@Test(priority = 6)
	public void testGetMilestoneTaskWithoutTenantId() {
		String milestoneId = "32";
		Response response = MilestoneTasksEndPoints.getMilestoneTask(accessToken, "", milestoneId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for missing tenant ID");
	}
}
