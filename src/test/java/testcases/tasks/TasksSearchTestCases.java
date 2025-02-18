package testcases.tasks;

import com.github.javafaker.Faker;
import endpoints.LoginEndPoints;
import endpoints.TaskEndPoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payload.Login;
import utilities.ConfigReader;

public class TasksSearchTestCases {
	public String accessToken;
	public Faker faker;
	public ConfigReader configReader;
	public String xTenantId;
	private String projectId = "758";

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

	// Positive case (Query: te)
	@Test(priority = 1)
	public void searchTasksPositiveCaseQueryTe() {
		Response response = TaskEndPoints.searchTasks(accessToken, xTenantId, projectId, "te", 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve tasks with query 'te'");
	}

	// Positive case (Query: zzz)
	@Test(priority = 2)
	public void searchTasksPositiveCaseQueryZzz() {
		Response response = TaskEndPoints.searchTasks(accessToken, xTenantId, projectId, "zzz", 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve tasks with query 'zzz'");
	}

	// Positive case (Query: blank)
	@Test(priority = 3)
	public void searchTasksPositiveCaseQueryEmpty() {
		Response response = TaskEndPoints.searchTasks(accessToken, xTenantId, projectId, "", 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve tasks with empty query");
	}

	// Without authorization
	@Test(priority = 4)
	public void searchTasksWithoutAuthorization() {
		Response response = TaskEndPoints.searchTasks(null, xTenantId, projectId, "te", 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Tasks should not be retrieved without authorization");
	}

	// Invalid project ID: "12345"
	@Test(priority = 5)
	public void searchTasksInvalidProjectId12345() {
		Response response = TaskEndPoints.searchTasks(accessToken, xTenantId, "12345", "te", 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 422,
				"Tasks should not be retrieved with invalid project ID '12345'");
	}

	// Invalid project ID: "-20"
	@Test(priority = 6)
	public void searchTasksInvalidProjectIdNegative() {
		Response response = TaskEndPoints.searchTasks(accessToken, xTenantId, "-20", "te", 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 422,
				"Tasks should not be retrieved with invalid project ID '-20'");
	}

	// Invalid project ID: "abc"
	@Test(priority = 7)
	public void searchTasksInvalidProjectIdABC() {
		Response response = TaskEndPoints.searchTasks(accessToken, xTenantId, "abc", "te", 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 422,
				"Tasks should not be retrieved with invalid project ID 'abc'");
	}

	// Invalid tenant ID
	@Test(priority = 8)
	public void searchTasksInvalidTenantId() {
		Response response = TaskEndPoints.searchTasks(accessToken, "invalid-tenant-id", projectId, "te", 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Tasks should not be retrieved with invalid tenant ID");
	}
}
