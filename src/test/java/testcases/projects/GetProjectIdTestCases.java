package testcases.projects;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import payload.Login;
import utilities.ConfigReader;

public class GetProjectIdTestCases {
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
	public void verifyGetProjectDetails() {
		System.out.println("Test Case: verifyGetProjectDetails - Start");

		// Get project details using ProjectEndPoints
		Response response = ProjectEndPoints.getProjectById(projectId, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve project details");

		System.out.println("Test Case: verifyGetProjectDetails - End");
	}

	// Invalid access token
	@Test(priority = 2)
	public void verifyGetProjectDetailsWithInvalidAccessToken() {
		System.out.println("Test Case: verifyGetProjectDetailsWithInvalidAccessToken - Start");

		// Get project details using invalid access token
		Response response = ProjectEndPoints.getProjectById(projectId, "invalid_token", xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyGetProjectDetailsWithInvalidAccessToken - End");
	}

	// Invalid tenant id
	@Test(priority = 3)
	public void verifyGetProjectDetailsWithInvalidTenantId() {
		System.out.println("Test Case: verifyGetProjectDetailsWithInvalidTenantId - Start");

		// Get project details using invalid tenant id
		Response response = ProjectEndPoints.getProjectById(projectId, accessToken, "123");

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant id");

		System.out.println("Test Case: verifyGetProjectDetailsWithInvalidTenantId - End");
	}

	// Access token null
	@Test(priority = 4)
	public void verifyGetProjectDetailsWithoutAccessToken() {
		System.out.println("Test Case: verifyGetProjectDetailsWithoutAccessToken - Start");

		// Get project details without access token
		Response response = ProjectEndPoints.getProjectById(projectId, null, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyGetProjectDetailsWithoutAccessToken - End");
	}

	// Invalid project IDs
	@Test(priority = 5)
	public void verifyGetProjectDetailsWithInvalidProjectIds() {
		String[] invalidProjectIds = { "12345", "-20", "abc" };
		for (String projectId : invalidProjectIds) {
			System.out.println("Test Case: verifyGetProjectDetailsWithInvalidProjectId - Start");

			// Get project details using invalid project ID
			Response response = ProjectEndPoints.getProjectById(projectId, accessToken, xTenantId);

			// Log request and response details
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();

			// Assert that the response status code is not 200
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid project ID: " + projectId);

			System.out.println("Test Case: verifyGetProjectDetailsWithInvalidProjectId - End");
		}
	}

}
