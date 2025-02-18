package testcases.tasks;

import endpoints.LoginEndPoints;
import endpoints.TaskEndPoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import payload.Login;
import payload.tasks.Tasks;
import utilities.ConfigReader;

import java.util.Arrays;
import java.util.List;

public class GroupTaskTestCases {

	private String accessToken;
	private String xTenantId;

	@BeforeClass
	public void setupTestData() {
		// Initialize ConfigReader
		ConfigReader configReader = new ConfigReader();

		// Perform login and retrieve access token
		String email = configReader.getProperty("email");
		String password = configReader.getProperty("password");
		xTenantId = configReader.getProperty("xTenantId");
		Login loginPayload = new Login(email, password);
		accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);
	}

	// Positive case: Update task status
	@Test(priority = 1)
	public void verifyGroupUpdateStatus() {
		System.out.println("Test Case: verifyGroupUpdateStatus - Start");

		List<Integer> taskIds = Arrays.asList(15340, 15341);
		String taskState = "doing";

		Tasks payload = new Tasks();
		payload.setTask_ids(taskIds);
		payload.setTask_state(taskState);

		Response response = TaskEndPoints.groupUpdateStatus(taskIds, taskState, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to update task status");

		System.out.println("Test Case: verifyGroupUpdateStatus - End");
	}

	// Negative case: Update task status with invalid task IDs
	@Test(priority = 2)
	public void verifyGroupUpdateStatusWithInvalidTaskIds() {
		System.out.println("Test Case: verifyGroupUpdateStatusWithInvalidTaskIds - Start");

		List<String> invalidTaskIds = Arrays.asList("12345678", "-20", "abc");
		String taskState = "doing";

		for (String invalidTaskId : invalidTaskIds) {
			Tasks payload = new Tasks();
			payload.setTask_ids(Arrays.asList(Integer.parseInt(invalidTaskId)));
			payload.setTask_state(taskState);

			Response response = TaskEndPoints.groupUpdateStatus(payload.getTask_ids(), taskState, accessToken,
					xTenantId);

			// Log request and response details
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();

			// Assert that the response status code is not 200
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid task ID: " + invalidTaskId);

			System.out.println("Test Case: verifyGroupUpdateStatusWithInvalidTaskIds - End");
		}
	}

	// Positive case: Update assigned_to
	@Test(priority = 3)
	public void verifyGroupUpdateAssignedTo() {
		System.out.println("Test Case: verifyGroupUpdateAssignedTo - Start");

		List<Integer> taskIds = Arrays.asList(15340, 15341);
		Integer assignedTo = 1;

		Tasks payload = new Tasks();
		payload.setTask_ids(taskIds);
		payload.setAssigned_to(assignedTo);

		Response response = TaskEndPoints.groupUpdateAssignedTo(taskIds, assignedTo, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to update assigned_to");

		System.out.println("Test Case: verifyGroupUpdateAssignedTo - End");
	}

	// Negative case: Update assigned_to with invalid task IDs
	@Test(priority = 4)
	public void verifyGroupUpdateAssignedToWithInvalidTaskIds() {
		System.out.println("Test Case: verifyGroupUpdateAssignedToWithInvalidTaskIds - Start");

		List<String> invalidTaskIds = Arrays.asList("12345678", "-20", "abc");
		Integer assignedTo = 1;

		for (String invalidTaskId : invalidTaskIds) {
			Tasks payload = new Tasks();
			payload.setTask_ids(Arrays.asList(Integer.parseInt(invalidTaskId)));
			payload.setAssigned_to(assignedTo);

			Response response = TaskEndPoints.groupUpdateAssignedTo(payload.getTask_ids(), assignedTo, accessToken,
					xTenantId);

			// Log request and response details
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();

			// Assert that the response status code is not 200
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid task ID: " + invalidTaskId);

			System.out.println("Test Case: verifyGroupUpdateAssignedToWithInvalidTaskIds - End");
		}
	}

	// Positive case: Delete tasks
	@Test(priority = 5)
	public void verifyGroupUpdateDelete() {
		System.out.println("Test Case: verifyGroupUpdateDelete - Start");

		List<Integer> taskIds = Arrays.asList(15340, 15341);

		Tasks payload = new Tasks();
		payload.setTask_ids(taskIds);

		Response response = TaskEndPoints.groupUpdateDelete(taskIds, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to delete tasks");

		System.out.println("Test Case: verifyGroupUpdateDelete - End");
	}

	// Negative case: Delete tasks with invalid task IDs
	@Test(priority = 6)
	public void verifyGroupUpdateDeleteWithInvalidTaskIds() {
		System.out.println("Test Case: verifyGroupUpdateDeleteWithInvalidTaskIds - Start");

		List<String> invalidTaskIds = Arrays.asList("12345678", "-20", "abc");

		for (String invalidTaskId : invalidTaskIds) {
			Tasks payload = new Tasks();
			payload.setTask_ids(Arrays.asList(Integer.parseInt(invalidTaskId)));

			Response response = TaskEndPoints.groupUpdateDelete(payload.getTask_ids(), accessToken, xTenantId);

			// Log request and response details
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();

			// Assert that the response status code is 404
			Assert.assertEquals(response.getStatusCode(), 404,
					"Expected failure with invalid task ID: " + invalidTaskId);

			System.out.println("Test Case: verifyGroupUpdateDeleteWithInvalidTaskIds - End");
		}
	}

	// Negative case: Invalid tenant ID
	@Test(priority = 7)
	public void verifyGroupUpdateWithInvalidTenantId() {
		System.out.println("Test Case: verifyGroupUpdateWithInvalidTenantId - Start");

		List<Integer> taskIds = Arrays.asList(15340, 15341);
		String invalidTenantId = "invalidTenantId";
		String taskState = "doing";

		Tasks payload = new Tasks();
		payload.setTask_ids(taskIds);
		payload.setTask_state(taskState);

		Response response = TaskEndPoints.groupUpdateStatus(taskIds, taskState, accessToken, invalidTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant ID");

		System.out.println("Test Case: verifyGroupUpdateWithInvalidTenantId - End");
	}
}
