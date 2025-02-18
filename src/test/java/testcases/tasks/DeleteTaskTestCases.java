package testcases.tasks;

import io.restassured.response.Response;
import payload.Login;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import endpoints.LoginEndPoints;
import endpoints.TaskEndPoints;
import utilities.ConfigReader;
import com.github.javafaker.Faker;

public class DeleteTaskTestCases {
	public String accessToken;
	public ConfigReader configReader;
	public String xTenantId;
	public Faker faker;

	@BeforeClass
	public void setupTestData() {
		configReader = new ConfigReader();

		String email = configReader.getProperty("email");
		String password = configReader.getProperty("password");
		xTenantId = configReader.getProperty("xTenantId");
		Login loginPayload = new Login(email, password);
		accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);

		faker = new Faker();
	}

	// Positive case
	@Test(priority = 1)
	public void deleteTaskPositiveCase() {
		String taskId = "15344"; // Use a valid task ID for the positive case
		Response response = TaskEndPoints.deleteTask(accessToken, xTenantId, taskId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to delete task");
	}

	// Without authorization
	@Test(priority = 2)
	public void deleteTaskWithoutAuthorization() {
		String taskId = "15343"; // Use a valid task ID for the positive case
		Response response = TaskEndPoints.deleteTask(null, xTenantId, taskId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Task should not be deleted without authorization");
	}

	// Invalid task ID: "12345678"
	@Test(priority = 3)
	public void deleteTaskInvalidTaskId12345678() {
		String taskId = "12345678";
		Response response = TaskEndPoints.deleteTask(accessToken, xTenantId, taskId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404,
				"Task should not be deleted with invalid task ID: " + taskId);
	}

	// Invalid task ID: "-20"
	@Test(priority = 4)
	public void deleteTaskInvalidTaskIdMinus20() {
		String taskId = "-20";
		Response response = TaskEndPoints.deleteTask(accessToken, xTenantId, taskId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404,
				"Task should not be deleted with invalid task ID: " + taskId);
	}

	// Invalid task ID: "abc"
	@Test(priority = 5)
	public void deleteTaskInvalidTaskIdABC() {
		String taskId = "abc";
		Response response = TaskEndPoints.deleteTask(accessToken, xTenantId, taskId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404,
				"Task should not be deleted with invalid task ID: " + taskId);
	}

	// Invalid tenant ID
	@Test(priority = 6)
	public void deleteTaskInvalidTenantId() {
		String taskId = "15343"; // Use a valid task ID for the positive case
		Response response = TaskEndPoints.deleteTask(accessToken, "invalid-tenant-id", taskId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Task should not be deleted with invalid tenant ID");
	}
}
