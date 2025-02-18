package testcases.tasks;

import io.restassured.response.Response;
import payload.Login;
import payload.tasks.Tasks;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import endpoints.LoginEndPoints;
import endpoints.TaskEndPoints;
import utilities.ConfigReader;

public class UpdateTaskTestCases {
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

	private Tasks createTaskPayload() {
		Tasks task = new Tasks();
		task.setProject_id("247");
		task.setTask_type(faker.lorem().characters(3, 7, true)); // Random single word with 3 to 7 chars
		task.setTask_title(faker.lorem().sentence()); // Random sentence for task title
		return task;
	}

	// Positive case
	@Test(priority = 1)
	public void updateTaskPositiveCase() {
		String taskId = "15340"; // Use a valid task ID for the positive case
		Tasks taskPayload = createTaskPayload();
		Response response = TaskEndPoints.updateTask(accessToken, xTenantId, taskId, taskPayload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to update task");
	}

	// Without authorization
	@Test(priority = 2)
	public void updateTaskWithoutAuthorization() {
		String taskId = "15340"; // Use a valid task ID for the positive case
		Tasks taskPayload = createTaskPayload();
		Response response = TaskEndPoints.updateTask(null, xTenantId, taskId, taskPayload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Task should not be updated without authorization");
	}

	// Invalid task ID: "12345678"
	@Test(priority = 3)
	public void updateTaskInvalidTaskId12345678() {
		String taskId = "12345678";
		Tasks taskPayload = createTaskPayload();
		Response response = TaskEndPoints.updateTask(accessToken, xTenantId, taskId, taskPayload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404,
				"Task should not be updated with invalid task ID: " + taskId);
	}

	// Invalid task ID: "-20"
	@Test(priority = 4)
	public void updateTaskInvalidTaskIdMinus20() {
		String taskId = "-20";
		Tasks taskPayload = createTaskPayload();
		Response response = TaskEndPoints.updateTask(accessToken, xTenantId, taskId, taskPayload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404,
				"Task should not be updated with invalid task ID: " + taskId);
	}

	// Invalid task ID: "abc"
	@Test(priority = 5)
	public void updateTaskInvalidTaskIdABC() {
		String taskId = "abc";
		Tasks taskPayload = createTaskPayload();
		Response response = TaskEndPoints.updateTask(accessToken, xTenantId, taskId, taskPayload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404,
				"Task should not be updated with invalid task ID: " + taskId);
	}

	// Invalid tenant ID
	@Test(priority = 6)
	public void updateTaskInvalidTenantId() {
		String taskId = "15340"; // Use a valid task ID for the positive case
		Tasks taskPayload = createTaskPayload();
		Response response = TaskEndPoints.updateTask(accessToken, "invalid-tenant-id", taskId, taskPayload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Task should not be updated with invalid tenant ID");
	}
}
