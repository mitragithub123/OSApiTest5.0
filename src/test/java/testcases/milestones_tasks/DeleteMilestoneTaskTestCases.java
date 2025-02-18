package testcases.milestones_tasks;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import endpoints.MilestoneTasksEndPoints;
import endpoints.LoginEndPoints;
import payload.Login;

public class DeleteMilestoneTaskTestCases {

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

	// Test case: Delete milestone task with a valid milestone ID
	@Test(priority = 1)
	public void testDeleteMilestoneTaskPositiveCase() {
		String milestoneId = "291"; // Use a valid milestone ID
		Response response = MilestoneTasksEndPoints.deleteMilestoneTask(accessToken, xTenantId, milestoneId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for valid milestone ID");
	}

	// Test case: Try deleting the same milestone task again
	@Test(priority = 2)
	public void testDeleteMilestoneTaskAgain() {
		String milestoneId = "288"; // Use the previously deleted milestone ID
		Response response = MilestoneTasksEndPoints.deleteMilestoneTask(accessToken, xTenantId, milestoneId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404 for already deleted milestone ID");
	}

	// Test case: Delete milestone task with an invalid milestone ID (123456)
	@Test(priority = 3)
	public void testDeleteMilestoneTaskInvalidMilestoneId() {
		String milestoneId = "123456";
		Response response = MilestoneTasksEndPoints.deleteMilestoneTask(accessToken, xTenantId, milestoneId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404 for invalid milestone ID");
	}

	// Test case: Delete milestone task with a negative milestone ID
	@Test(priority = 4)
	public void testDeleteMilestoneTaskNegativeMilestoneId() {
		String milestoneId = "-20";
		Response response = MilestoneTasksEndPoints.deleteMilestoneTask(accessToken, xTenantId, milestoneId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404 for negative milestone ID");
	}

	// Test case: Delete milestone task with a milestone ID in an incorrect format
	// (non-numeric)
	@Test(priority = 5)
	public void testDeleteMilestoneTaskInvalidMilestoneIdFormat() {
		String milestoneId = "abc";
		Response response = MilestoneTasksEndPoints.deleteMilestoneTask(accessToken, xTenantId, milestoneId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for invalid milestone ID format");
	}

	// Test case: Delete milestone task without authorization
	@Test(priority = 6)
	public void testDeleteMilestoneTaskWithoutAuthorization() {
		String milestoneId = "289";
		Response response = MilestoneTasksEndPoints.deleteMilestoneTask(null, xTenantId, milestoneId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for missing authorization");
	}

	// Test case: Delete milestone task without tenant ID
	@Test(priority = 7)
	public void testDeleteMilestoneTaskWithoutTenantId() {
		String milestoneId = "289";
		Response response = MilestoneTasksEndPoints.deleteMilestoneTask(accessToken, "", milestoneId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for missing tenant ID");
	}
}
