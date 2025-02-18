package testcases.projects;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import payload.Login;
import utilities.ConfigReader;

public class GetProjectManagerTestCases {
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
	public void verifyGetProjectManager() {
		System.out.println("Test Case: verifyGetProjectManager - Start");

		// Get project manager details using ProjectEndPoints
		Response response = ProjectEndPoints.getProjectManager(projectId, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve project manager details");

		System.out.println("Test Case: verifyGetProjectManager - End");
	}

	// Invalid project IDs
	@Test(priority = 2)
	public void verifyGetProjectManagerWithInvalidProjectIds() {
		String[] invalidProjectIds = { "12345", "-20", "abc" };
		for (String invalidProjectId : invalidProjectIds) {
			System.out.println("Test Case: verifyGetProjectManagerWithInvalidProjectId - Start");

			// Get project manager details using invalid project ID in URL
			Response response = ProjectEndPoints.getProjectManager(invalidProjectId, accessToken, xTenantId);

			// Log request and response details
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();

			// Assert that the response status code is not 200
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid project ID: " + invalidProjectId);

			System.out.println("Test Case: verifyGetProjectManagerWithInvalidProjectId - End");
		}
	}

	// Invalid access token
	@Test(priority = 3)
	public void verifyGetProjectManagerWithInvalidAccessToken() {
		System.out.println("Test Case: verifyGetProjectManagerWithInvalidAccessToken - Start");

		// Get project manager details using invalid access token
		Response response = ProjectEndPoints.getProjectManager(projectId, "invalid_token", xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyGetProjectManagerWithInvalidAccessToken - End");
	}

	// Invalid tenant id
	@Test(priority = 4)
	public void verifyGetProjectManagerWithInvalidTenantId() {
		System.out.println("Test Case: verifyGetProjectManagerWithInvalidTenantId - Start");

		// Get project manager details using invalid tenant id
		Response response = ProjectEndPoints.getProjectManager(projectId, accessToken, "123");

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant id");

		System.out.println("Test Case: verifyGetProjectManagerWithInvalidTenantId - End");
	}

	// Access token null
	@Test(priority = 5)
	public void verifyGetProjectManagerWithoutAccessToken() {
		System.out.println("Test Case: verifyGetProjectManagerWithoutAccessToken - Start");

		// Get project manager details without access token
		Response response = ProjectEndPoints.getProjectManager(projectId, null, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyGetProjectManagerWithoutAccessToken - End");
	}

}
