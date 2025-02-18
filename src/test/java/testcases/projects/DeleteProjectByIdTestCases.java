package testcases.projects;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import payload.Login;
import utilities.ConfigReader;

public class DeleteProjectByIdTestCases {
	private String accessToken;
	private String xTenantId;
	private String projectId = "757"; // Assuming the project ID is known and set here

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
	public void verifyDeleteProjectById() {
		System.out.println("Test Case: verifyDeleteProjectById - Start");

		// Delete project by ID using ProjectEndPoints
		Response response = ProjectEndPoints.deleteProjectById(projectId, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to delete project");

		System.out.println("Test Case: verifyDeleteProjectById - End");
	}

	// Invalid project IDs
	@Test(priority = 2)
	public void verifyDeleteProjectByIdWithInvalidProjectIds() {
		String[] invalidProjectIds = { "12345", "-20", "abc" };
		for (String invalidProjectId : invalidProjectIds) {
			System.out.println("Test Case: verifyDeleteProjectByIdWithInvalidProjectId - Start");

			// Delete project by ID using invalid project ID
			Response response = ProjectEndPoints.deleteProjectById(invalidProjectId, accessToken, xTenantId);

			// Log request and response details
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();

			// Assert that the response status code is not 200
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid project ID: " + invalidProjectId);

			System.out.println("Test Case: verifyDeleteProjectByIdWithInvalidProjectId - End");
		}
	}

	// Invalid access token
	@Test(priority = 3)
	public void verifyDeleteProjectByIdWithInvalidAccessToken() {
		System.out.println("Test Case: verifyDeleteProjectByIdWithInvalidAccessToken - Start");

		// Delete project by ID using invalid access token
		Response response = ProjectEndPoints.deleteProjectById(projectId, "invalid_token", xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyDeleteProjectByIdWithInvalidAccessToken - End");
	}

	// Invalid tenant id
	@Test(priority = 4)
	public void verifyDeleteProjectByIdWithInvalidTenantId() {
		System.out.println("Test Case: verifyDeleteProjectByIdWithInvalidTenantId - Start");

		// Delete project by ID using invalid tenant id
		Response response = ProjectEndPoints.deleteProjectById(projectId, accessToken, "123");

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant id");

		System.out.println("Test Case: verifyDeleteProjectByIdWithInvalidTenantId - End");
	}

	// Access token null
	@Test(priority = 5)
	public void verifyDeleteProjectByIdWithoutAccessToken() {
		System.out.println("Test Case: verifyDeleteProjectByIdWithoutAccessToken - Start");

		// Delete project by ID without access token
		Response response = ProjectEndPoints.deleteProjectById(projectId, null, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyDeleteProjectByIdWithoutAccessToken - End");
	}
}
