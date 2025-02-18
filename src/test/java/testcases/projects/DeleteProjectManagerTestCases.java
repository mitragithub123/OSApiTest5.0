package testcases.projects;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import payload.Login;
import utilities.ConfigReader;

public class DeleteProjectManagerTestCases {
	private String accessToken;
	private String xTenantId;
	private String projectId = "758"; // Assuming the project ID is known and set here

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

	// Positive case
	@Test(priority = 1)
	public void verifyDeleteProjectManager() {
		System.out.println("Test Case: verifyDeleteProjectManager - Start");

		// Perform delete operation on project manager
		Response response = ProjectEndPoints.deleteProjectManager(projectId, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200 indicating successful deletion
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to delete project manager");

		System.out.println("Test Case: verifyDeleteProjectManager - End");
	}

	// Invalid access token
	@Test(priority = 2)
	public void verifyDeleteProjectManagerWithInvalidAccessToken() {
		System.out.println("Test Case: verifyDeleteProjectManagerWithInvalidAccessToken - Start");

		// Perform delete operation on project manager with invalid access token
		Response response = ProjectEndPoints.deleteProjectManager(projectId, "invalid_token", xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 204 (No Content)
		Assert.assertNotEquals(response.getStatusCode(), 204, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyDeleteProjectManagerWithInvalidAccessToken - End");
	}

	// Invalid tenant id
	@Test(priority = 3)
	public void verifyDeleteProjectManagerWithInvalidTenantId() {
		System.out.println("Test Case: verifyDeleteProjectManagerWithInvalidTenantId - Start");

		// Perform delete operation on project manager with invalid tenant id
		Response response = ProjectEndPoints.deleteProjectManager(projectId, accessToken, "123");

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 204 (No Content)
		Assert.assertNotEquals(response.getStatusCode(), 204, "Expected failure with invalid tenant id");

		System.out.println("Test Case: verifyDeleteProjectManagerWithInvalidTenantId - End");
	}

	// Access token null
	@Test(priority = 4)
	public void verifyDeleteProjectManagerWithoutAccessToken() {
		System.out.println("Test Case: verifyDeleteProjectManagerWithoutAccessToken - Start");

		// Perform delete operation on project manager without access token
		Response response = ProjectEndPoints.deleteProjectManager(projectId, null, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 204 (No Content)
		Assert.assertNotEquals(response.getStatusCode(), 204, "Expected failure without access token");

		System.out.println("Test Case: verifyDeleteProjectManagerWithoutAccessToken - End");
	}

	// Invalid project id
	@Test(priority = 5)
	public void verifyDeleteProjectManagerWithInvalidProjectIds() {
		String[] invalidProjectIds = { "12345", "-20", "abc" };

		for (String invalidProjectId : invalidProjectIds) {
			System.out.println("Test Case: verifyDeleteProjectManagerWithInvalidProjectId - Start");

			// Perform delete operation on project manager with invalid project ID
			Response response = ProjectEndPoints.deleteProjectManager(invalidProjectId, accessToken, xTenantId);

			// Log request and response details
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();

			// Assert that the response status code is not 204 (No Content)
			Assert.assertNotEquals(response.getStatusCode(), 204,
					"Expected failure with invalid project ID: " + invalidProjectId);

			System.out.println("Test Case: verifyDeleteProjectManagerWithInvalidProjectId - End");
		}
	}

}
