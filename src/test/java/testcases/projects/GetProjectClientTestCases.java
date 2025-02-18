package testcases.projects;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payload.Login;
import utilities.ConfigReader;

public class GetProjectClientTestCases {
	private String accessToken;
	private String xTenantId;
	private String projectId = "756"; // Assuming the project ID is known and set here

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

	// Positive case: Verify get project client details
	@Test(priority = 1)
	public void verifyGetProjectClientDetails() {
		System.out.println("Test Case: verifyGetProjectClientDetails - Start");

		// Get project client details using ProjectEndPoints
		Response response = ProjectEndPoints.getProjectClient(projectId, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve project client details");

		System.out.println("Test Case: verifyGetProjectClientDetails - End");
	}

	// Negative case: Verify get project client details with invalid project ID
	@Test(priority = 2)
	public void verifyGetProjectClientWithInvalidProjectId() {
		String invalidProjectId = "12345";

		System.out.println("Test Case: verifyGetProjectClientWithInvalidProjectId - Start");

		// Get project client details using invalid project ID
		Response response = ProjectEndPoints.getProjectClient(invalidProjectId, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Expected failure with invalid project ID: " + invalidProjectId);

		System.out.println("Test Case: verifyGetProjectClientWithInvalidProjectId - End");
	}

	// Negative case: Verify get project client details with invalid access token
	@Test(priority = 3)
	public void verifyGetProjectClientWithInvalidAccessToken() {
		System.out.println("Test Case: verifyGetProjectClientWithInvalidAccessToken - Start");

		// Get project client details using invalid access token
		Response response = ProjectEndPoints.getProjectClient(projectId, "invalid_token", xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyGetProjectClientWithInvalidAccessToken - End");
	}

	// Negative case: Verify get project client details with invalid tenant id
	@Test(priority = 4)
	public void verifyGetProjectClientWithInvalidTenantId() {
		System.out.println("Test Case: verifyGetProjectClientWithInvalidTenantId - Start");

		// Get project client details using invalid tenant id
		Response response = ProjectEndPoints.getProjectClient(projectId, accessToken, "123");

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant id");

		System.out.println("Test Case: verifyGetProjectClientWithInvalidTenantId - End");
	}

	// Negative case: Verify get project client details without access token
	@Test(priority = 5)
	public void verifyGetProjectClientWithoutAccessToken() {
		System.out.println("Test Case: verifyGetProjectClientWithoutAccessToken - Start");

		// Get project client details without access token
		Response response = ProjectEndPoints.getProjectClient(projectId, null, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyGetProjectClientWithoutAccessToken - End");
	}
}
