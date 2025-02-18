package testcases.milestones_tasks;

import io.restassured.response.Response;
import payload.Login;
import payload.milestones_tasks.MilestoneTasks;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import endpoints.LoginEndPoints;
import endpoints.MilestoneTasksEndPoints;

public class CreateMilestoneTaskTestCases {

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

	// Test case: Positive scenario for creating a milestone task with valid data.
	@Test(priority = 1)
	public void testCreateMilestoneTaskPositiveCase() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("758");
		task.setMilestoneId("404");
		task.setTaskId("59");

		Response response = MilestoneTasksEndPoints.createMilestoneTask(accessToken, xTenantId, task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for valid data");
	}

	// Test case: Create a milestone task with an invalid project ID.
	@Test(priority = 2)
	public void testCreateMilestoneTaskInvalidProjectId() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("123456");
		task.setMilestoneId("404");
		task.setTaskId("59");

		Response response = MilestoneTasksEndPoints.createMilestoneTask(accessToken, xTenantId, task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 400, "Expected status code 400 for invalid project ID");
	}

	// Test case: Create a milestone task with a negative project ID.
	@Test(priority = 3)
	public void testCreateMilestoneTaskNegativeProjectId() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("-20");
		task.setMilestoneId("404");
		task.setTaskId("59");

		Response response = MilestoneTasksEndPoints.createMilestoneTask(accessToken, xTenantId, task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 400, "Expected status code 400 for negative project ID");
	}

	// Test case: Create a milestone task with a project ID in an incorrect format
	// (non-numeric).
	@Test(priority = 4)
	public void testCreateMilestoneTaskInvalidProjectIdFormat() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("abc");
		task.setMilestoneId("404");
		task.setTaskId("59");

		Response response = MilestoneTasksEndPoints.createMilestoneTask(accessToken, xTenantId, task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for invalid project ID format");
	}

	// Test case: Create a milestone task with an invalid milestone ID.
	@Test(priority = 5)
	public void testCreateMilestoneTaskInvalidMilestoneId() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("758");
		task.setMilestoneId("123456");
		task.setTaskId("59");

		Response response = MilestoneTasksEndPoints.createMilestoneTask(accessToken, xTenantId, task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404 for invalid milestone ID");
	}

	// Test case: Create a milestone task with a negative milestone ID.
	@Test(priority = 6)
	public void testCreateMilestoneTaskNegativeMilestoneId() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("758");
		task.setMilestoneId("-20");
		task.setTaskId("59");

		Response response = MilestoneTasksEndPoints.createMilestoneTask(accessToken, xTenantId, task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404 for negative milestone ID");
	}

	// Test case: Create a milestone task with a milestone ID in an incorrect format
	// (non-numeric).
	@Test(priority = 7)
	public void testCreateMilestoneTaskInvalidMilestoneIdFormat() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("758");
		task.setMilestoneId("abc");
		task.setTaskId("59");

		Response response = MilestoneTasksEndPoints.createMilestoneTask(accessToken, xTenantId, task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for invalid milestone ID format");
	}

	// Test case: Create a milestone task with a valid task ID.
	@Test(priority = 8)
	public void testCreateMilestoneTaskValidTaskId() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("758");
		task.setMilestoneId("404");
		task.setTaskId("59");

		Response response = MilestoneTasksEndPoints.createMilestoneTask(accessToken, xTenantId, task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for valid sub-task ID");
	}

	// Test case: Create a milestone task with a valid task ID.
	@Test(priority = 9)
	public void testCreateMilestoneTaskValidSubTaskId() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("758");
		task.setMilestoneId("404");
		task.setTaskId("15343");

		Response response = MilestoneTasksEndPoints.createMilestoneTask(accessToken, xTenantId, task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for valid sub-task ID");
	}

	// Test case: Create a milestone task without authorization.
	@Test(priority = 10)
	public void testCreateMilestoneTaskWithoutAuthorization() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("758");
		task.setMilestoneId("404");
		task.setTaskId("59");

		Response response = MilestoneTasksEndPoints.createMilestoneTask(null, xTenantId, task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for missing authorization");
	}

	// Test case: Create a milestone task without a tenant ID.
	@Test(priority = 11)
	public void testCreateMilestoneTaskWithoutTenantId() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("758");
		task.setMilestoneId("404");
		task.setTaskId("59");

		Response response = MilestoneTasksEndPoints.createMilestoneTask(accessToken, "", task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for missing tenant ID");
	}

	// Missing milestone ID.
	@Test(priority = 12)
	public void testCreateMilestoneTaskMissingMilestoneId() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("758");
		task.setMilestoneId(null); // Missing milestone ID
		task.setTaskId("59");

		Response response = MilestoneTasksEndPoints.createMilestoneTask(accessToken, xTenantId, task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for missing milestone ID");
	}

	// Test case: Missing task ID.
	@Test(priority = 13)
	public void testCreateMilestoneTaskMissingTaskId() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("758");
		task.setMilestoneId("404");
		task.setTaskId(null); // Missing task ID

		Response response = MilestoneTasksEndPoints.createMilestoneTask(accessToken, xTenantId, task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for missing task ID");
	}

	// Test case: Empty project ID
	@Test(priority = 14)
	public void testCreateMilestoneTaskEmptyProjectId() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId(""); // Empty project ID
		task.setMilestoneId("404");
		task.setTaskId("59");

		Response response = MilestoneTasksEndPoints.createMilestoneTask(accessToken, xTenantId, task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for empty project ID");
	}

	// Test case: Empty milestone ID
	@Test(priority = 15)
	public void testCreateMilestoneTaskEmptyMilestoneId() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("758");
		task.setMilestoneId(""); // Empty milestone ID
		task.setTaskId("59");

		Response response = MilestoneTasksEndPoints.createMilestoneTask(accessToken, xTenantId, task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for empty milestone ID");
	}

	// Test case: Empty task ID
	@Test(priority = 16)
	public void testCreateMilestoneTaskEmptyTaskId() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("758");
		task.setMilestoneId("404");
		task.setTaskId(""); // Empty task ID

		Response response = MilestoneTasksEndPoints.createMilestoneTask(accessToken, xTenantId, task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for empty task ID");
	}

}
