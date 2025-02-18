package testcases.tasks;

import com.github.javafaker.Faker;
import endpoints.LoginEndPoints;
import endpoints.TaskEndPoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payload.Login;
import payload.projects.Projects;
import payload.tasks.Tasks;
import utilities.ConfigReader;

public class CreateTaskTestCases {
	public String accessToken;
	public Faker faker;
	public Projects projectRequest;
	public ConfigReader configReader;
	public String xTenantId;
	private String projectId = "758";

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

		// Initialize Faker
		faker = new Faker();
	}

	// Positive case
	@Test(priority = 1)
	public void createTaskWithSimplePayload() {
		System.out.println("Creating task with simple payload");

		Tasks taskPayload = new Tasks();
		taskPayload.setProject_id(projectId);
		taskPayload.setTask_title(faker.lorem().sentence(3));
		taskPayload.setTask_type("design");
		taskPayload.setTask_description(faker.lorem().paragraph());

		System.out.println("Request Payload: " + taskPayload.toString());

		Response response = TaskEndPoints.createTask(taskPayload, accessToken, xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to create task");
	}

	// Empty task title
	@Test(priority = 2)
	public void createTaskWithEmptyTaskTitle() {
		System.out.println("Test Case: createTaskWithEmptyTaskTitle - Start");

		Tasks taskPayload = new Tasks();
		taskPayload.setProject_id(projectId);
		taskPayload.setTask_title("");
		taskPayload.setTask_type("design");
		taskPayload.setTask_description(faker.lorem().paragraph());

		System.out.println("Request Payload: " + taskPayload.toString());

		Response response = TaskEndPoints.createTask(taskPayload, accessToken, xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 400, "Task should not be created with empty task title");

		System.out.println("Test Case: createTaskWithEmptyTaskTitle - End");
	}

	// Invalid task priority
	@Test(priority = 3)
	public void createTaskWithInvalidTaskPriority() {
		System.out.println("Test Case: createTaskWithInvalidTaskPriority - Start");

		Tasks taskPayload = new Tasks();
		taskPayload.setProject_id(projectId);
		taskPayload.setTask_title(faker.lorem().sentence(3));
		taskPayload.setTaskPriority("invalid-task-priority");
		taskPayload.setTask_description(faker.lorem().paragraph());

		System.out.println("Request Payload: " + taskPayload.toString());

		Response response = TaskEndPoints.createTask(taskPayload, accessToken, xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 422, "Task should not be created with invalid task type");

		System.out.println("Test Case: createTaskWithInvalidTaskPriority - End");
	}

	// Without authorization
	@Test(priority = 4)
	public void createTaskWithoutAuthorization() {
		System.out.println("Test Case: createTaskWithoutAuthorization - Start");

		Tasks taskPayload = new Tasks();
		taskPayload.setProject_id(projectId);
		taskPayload.setTask_title(faker.lorem().sentence(3));
		taskPayload.setTask_type("design");
		taskPayload.setTask_description(faker.lorem().paragraph());

		System.out.println("Request Payload: " + taskPayload.toString());

		Response response = TaskEndPoints.createTask(taskPayload, null, xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 401, "Task should not be created without authorization");

		System.out.println("Test Case: createTaskWithoutAuthorization - End");
	}

	// Invalid project ID: "12345"
	@Test(priority = 5)
	public void createTaskWithInvalidProjectId1() {
		System.out.println("Creating task with invalid project ID: 12345");

		Tasks taskPayload = new Tasks();
		taskPayload.setProject_id("12345");
		taskPayload.setTask_title(faker.lorem().sentence(3));
		taskPayload.setTask_type("design");
		taskPayload.setTask_description(faker.lorem().paragraph());

		System.out.println("Request Payload: " + taskPayload.toString());

		Response response = TaskEndPoints.createTask(taskPayload, accessToken, xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 422, "Task should not be created with invalid project ID: 12345");
	}

	// Invalid project ID: "-20"
	@Test(priority = 6)
	public void createTaskWithInvalidProjectId2() {
		System.out.println("Creating task with invalid project ID: -20");

		Tasks taskPayload = new Tasks();
		taskPayload.setProject_id("-20");
		taskPayload.setTask_title(faker.lorem().sentence(3));
		taskPayload.setTask_type("design");
		taskPayload.setTask_description(faker.lorem().paragraph());

		System.out.println("Request Payload: " + taskPayload.toString());

		Response response = TaskEndPoints.createTask(taskPayload, accessToken, xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 422, "Task should not be created with invalid project ID: -20");
	}

	// Invalid project ID: "abc"
	@Test(priority = 7)
	public void createTaskWithInvalidProjectId3() {
		System.out.println("Creating task with invalid project ID: abc");

		Tasks taskPayload = new Tasks();
		taskPayload.setProject_id("abc");
		taskPayload.setTask_title(faker.lorem().sentence(3));
		taskPayload.setTask_type("design");
		taskPayload.setTask_description(faker.lorem().paragraph());

		System.out.println("Request Payload: " + taskPayload.toString());

		Response response = TaskEndPoints.createTask(taskPayload, accessToken, xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 422, "Task should not be created with invalid project ID: abc");
	}

	// Invalid tenant ID
	@Test(priority = 8)
	public void createTaskWithInvalidTenantId() {
		System.out.println("Test Case: createTaskWithInvalidTenantId - Start");

		Tasks taskPayload = new Tasks();
		taskPayload.setProject_id(projectId);
		taskPayload.setTask_title(faker.lorem().sentence(3));
		taskPayload.setTask_type("design");
		taskPayload.setTask_description(faker.lorem().paragraph());

		System.out.println("Request Payload: " + taskPayload.toString());

		Response response = TaskEndPoints.createTask(taskPayload, accessToken, "invalid-tenant-id");
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 401, "Task should not be created with invalid tenant ID");

		System.out.println("Test Case: createTaskWithInvalidTenantId - End");
	}

}
