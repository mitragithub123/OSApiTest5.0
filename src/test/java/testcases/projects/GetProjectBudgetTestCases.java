package testcases.projects;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import payload.Login;
import utilities.ConfigReader;

public class GetProjectBudgetTestCases {
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
	public void verifyGetProjectBudget() {
		System.out.println("Test Case: verifyGetProjectBudget - Start");

		// Get project budget details using ProjectEndPoints
		Response response = ProjectEndPoints.getProjectBudget(projectId, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve project budget details");

		System.out.println("Test Case: verifyGetProjectBudget - End");
	}

	// Invalid project IDs
	@Test(priority = 2)
	public void verifyGetProjectBudgetWithInvalidProjectIds() {
		String[] invalidProjectIds = { "12345", "-20", "abc" };

		for (String invalidProjectId : invalidProjectIds) {
			System.out.println("Test Case: verifyGetProjectBudgetWithInvalidProjectId - Start");

			// Get project budget details using invalid project ID in URL
			Response response = ProjectEndPoints.getProjectBudget(invalidProjectId, accessToken, xTenantId);

			// Log request and response details
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();

			// Assert that the response status code is not 200
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid project ID: " + invalidProjectId);

			System.out.println("Test Case: verifyGetProjectBudgetWithInvalidProjectId - End");
		}
	}

	// Invalid access token
	@Test(priority = 3)
	public void verifyGetProjectBudgetWithInvalidAccessToken() {
		System.out.println("Test Case: verifyGetProjectBudgetWithInvalidAccessToken - Start");

		// Get project budget details using invalid access token
		Response response = ProjectEndPoints.getProjectBudget(projectId, "invalid_token", xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyGetProjectBudgetWithInvalidAccessToken - End");
	}

	// Invalid tenant id
	@Test(priority = 4)
	public void verifyGetProjectBudgetWithInvalidTenantId() {
		System.out.println("Test Case: verifyGetProjectBudgetWithInvalidTenantId - Start");

		// Get project budget details using invalid tenant id
		Response response = ProjectEndPoints.getProjectBudget(projectId, accessToken, "123");

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant id");

		System.out.println("Test Case: verifyGetProjectBudgetWithInvalidTenantId - End");
	}

	// Access token null
	@Test(priority = 5)
	public void verifyGetProjectBudgetWithoutAccessToken() {
		System.out.println("Test Case: verifyGetProjectBudgetWithoutAccessToken - Start");

		// Get project budget details without access token
		Response response = ProjectEndPoints.getProjectBudget(projectId, null, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyGetProjectBudgetWithoutAccessToken - End");
	}
}
