package testcases.tasks;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import endpoints.LoginEndPoints;
import endpoints.TaskEndPoints;
import payload.Login;
import utilities.ConfigReader;

public class GetSubTasksTestCases {
	public String accessToken;
	public ConfigReader configReader;
	public String xTenantId;

	@BeforeClass
	public void setupTestData() {
		configReader = new ConfigReader();

		String email = configReader.getProperty("email");
		String password = configReader.getProperty("password");
		xTenantId = configReader.getProperty("xTenantId");
		Login loginPayload = new Login(email, password);
		accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);
	}

	// Positive case
	@Test(priority = 1)
	public void getSubTasksPositiveCase() {
		String taskId = "134063"; // Use a valid task ID for the positive case
		Response response = TaskEndPoints.getSubTasks(accessToken, xTenantId, taskId, 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve subtasks");
	}

	// Without authorization
	@Test(priority = 2)
	public void getSubTasksWithoutAuthorization() {
		String taskId = "134063"; // Use a valid task ID for the positive case
		Response response = TaskEndPoints.getSubTasks(null, xTenantId, taskId, 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Subtasks should not be retrieved without authorization");
	}

	// Invalid task ID: "12345"
	@Test(priority = 3)
	public void getSubTasksInvalidTaskId12345() {
		String taskId = "12345";
		Response response = TaskEndPoints.getSubTasks(accessToken, xTenantId, taskId, 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404,
				"Subtasks should not be retrieved with invalid task ID: " + taskId);
	}

	// Invalid task ID: "-20"
	@Test(priority = 4)
	public void getSubTasksInvalidTaskIdMinus20() {
		String taskId = "-20";
		Response response = TaskEndPoints.getSubTasks(accessToken, xTenantId, taskId, 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404,
				"Subtasks should not be retrieved with invalid task ID: " + taskId);
	}

	// Invalid task ID: "abc"
	@Test(priority = 5)
	public void getSubTasksInvalidTaskIdABC() {
		String taskId = "abc";
		Response response = TaskEndPoints.getSubTasks(accessToken, xTenantId, taskId, 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404,
				"Subtasks should not be retrieved with invalid task ID: " + taskId);
	}

	// Invalid tenant ID
	@Test(priority = 6)
	public void getSubTasksInvalidTenantId() {
		String taskId = "134063"; // Use a valid task ID for the positive case
		Response response = TaskEndPoints.getSubTasks(accessToken, "invalid-tenant-id", taskId, 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Subtasks should not be retrieved with invalid tenant ID");
	}
}
