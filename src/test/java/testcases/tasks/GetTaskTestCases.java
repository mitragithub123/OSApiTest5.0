package testcases.tasks;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import endpoints.LoginEndPoints;
import endpoints.TaskEndPoints;
import payload.Login;
import utilities.ConfigReader;

public class GetTaskTestCases {
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
	public void getTaskPositiveCase() {
		String taskId = "15340"; // Use a valid task ID for the positive case
		Response response = TaskEndPoints.getTask(accessToken, xTenantId, taskId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve task");
	}

	// Without authorization
	@Test(priority = 2)
	public void getTaskWithoutAuthorization() {
		String taskId = "15340"; // Use a valid task ID for the positive case
		Response response = TaskEndPoints.getTask(null, xTenantId, taskId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Task should not be retrieved without authorization");
	}

	// Invalid task ID: "12345678"
	@Test(priority = 3)
	public void getTaskInvalidTaskId12345678() {
		String taskId = "12345678";
		Response response = TaskEndPoints.getTask(accessToken, xTenantId, taskId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404,
				"Task should not be retrieved with invalid task ID: " + taskId);
	}

	// Invalid task ID: "-20"
	@Test(priority = 4)
	public void getTaskInvalidTaskIdMinus20() {
		String taskId = "-20";
		Response response = TaskEndPoints.getTask(accessToken, xTenantId, taskId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404,
				"Task should not be retrieved with invalid task ID: " + taskId);
	}

	// Invalid task ID: "abc"
	@Test(priority = 5)
	public void getTaskInvalidTaskIdABC() {
		String taskId = "abc";
		Response response = TaskEndPoints.getTask(accessToken, xTenantId, taskId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 422,
				"Task should not be retrieved with invalid task ID: " + taskId);
	}

	// Invalid tenant ID
	@Test(priority = 6)
	public void getTaskInvalidTenantId() {
		String taskId = "15340"; // Use a valid task ID for the positive case
		Response response = TaskEndPoints.getTask(accessToken, "invalid-tenant-id", taskId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Task should not be retrieved with invalid tenant ID");
	}
}
