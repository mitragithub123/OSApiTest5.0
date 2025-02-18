package testcases.milestones_tasks;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.LoginEndPoints;
import endpoints.MilestoneTasksEndPoints;
import io.restassured.response.Response;
import payload.Login;
import payload.milestones_tasks.MilestoneTasks;
import utilities.ConfigReader;

public class UpdateMilestoneTaskTestCases {
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

	// Positive scenario
	@Test(priority = 1)
	public void testUpdateMilestoneTaskPositiveCase() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("799");
		task.setMilestoneId("407");
		task.setTaskId("134188");
		Response response = MilestoneTasksEndPoints.updateMilestoneTask(accessToken, xTenantId, "294", task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
	}

	// Invalid data
	@Test(priority = 2)
	public void testUpdateMilestoneTaskNegativeCase() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("123456");
		task.setMilestoneId("4077");
		task.setTaskId("1341888");
		Response response = MilestoneTasksEndPoints.updateMilestoneTask(accessToken, xTenantId, "294", task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404");
	}

	// Without authorization
	@Test(priority = 3)
	public void testUpdateMilestoneTaskNoAutorization() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("123456");
		task.setMilestoneId("4077");
		task.setTaskId("1341888");
		Response response = MilestoneTasksEndPoints.updateMilestoneTask(null, xTenantId, "294", task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 404");
	}

	// Without tenant id
	@Test(priority = 4)
	public void testUpdateMilestoneTaskInvalidTenantId() {
		MilestoneTasks task = new MilestoneTasks();
		task.setProjectId("123456");
		task.setMilestoneId("4077");
		task.setTaskId("1341888");
		Response response = MilestoneTasksEndPoints.updateMilestoneTask(accessToken, "invalid", "294", task);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 404");
	}
}
